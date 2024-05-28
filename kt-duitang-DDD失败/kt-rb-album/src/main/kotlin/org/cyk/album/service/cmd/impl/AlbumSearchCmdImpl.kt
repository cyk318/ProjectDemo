package org.cyk.album.service.cmd.impl

import org.cyk.album.facade.*
import org.cyk.album.repo.es.AlbumDocRepo
import org.cyk.album.repo.es.impl.AlbumDoc
import org.cyk.album.repo.mongo.AlbumCollectRepo
import org.cyk.album.repo.mongo.AlbumLikeRepo
import org.cyk.album.repo.mongo.AlbumStatRepo
import org.cyk.album.repo.mongo.impl.AlbumStat
import org.cyk.album.repo.mysql.AlbumPhotoRepo
import org.cyk.album.repo.mysql.mapper.AlbumPhoto
import org.cyk.album.service.cmd.AlbumSearchCmd
import org.cyk.album.service.rpc.UserInfoServiceRpcImpl
import org.cyk.base.handler.PageResp
import org.cyk.base.utils.DateUtils
import org.cyk.feign.user.UserInfoDto
import org.springframework.stereotype.Service
import jakarta.annotation.Resource

@Service
class AlbumSearchCmdImpl: AlbumSearchCmd {


    @Resource private lateinit var albumDocRepo: AlbumDocRepo
    @Resource private lateinit var userInfoRpc: UserInfoServiceRpcImpl
    @Resource private lateinit var albumPhotoRepo: AlbumPhotoRepo
    @Resource private lateinit var albumStatRepo: AlbumStatRepo
    @Resource private lateinit var albumLikeRepo: AlbumLikeRepo
    @Resource private lateinit var albumCollectRepo: AlbumCollectRepo

    override fun pageAlbumVo(o: AlbumSearchDto): PageResp<AlbumVo> {
        val page = albumDocRepo.pageAlbumDoc(o)
        val aIds = page.result.map(AlbumDoc::id)
        if(aIds.isEmpty()) {
            return PageResp.empty(AlbumVo::class.java)
        }
        val aInfos = page.result

        val uInfoMap = let {
            val uIds = aInfos.map(AlbumDoc::userId)
            userInfoRpc.queryByUserIds(uIds).associateBy(UserInfoDto::id)
        }

        val aPhotoMap = albumPhotoRepo.queryByAlbumIds(aIds).groupBy(AlbumPhoto::albumId)

        val aStatMap = albumStatRepo.queryByAlbumIds(aIds).associateBy(AlbumStat::id)
        return PageResp.ok(
            page.hasMore,
            page.nextStart,
            map(aInfos, uInfoMap, aPhotoMap, aStatMap, o.curUserId),
            page.total
        )
    }

    override fun suggest(o: AlbumSuggestDto): List<AlbumSuggestVo> {
        val result = albumDocRepo.querySuggests(o).map(::map)
        return result
    }

    private fun map(str: String) = run {
        AlbumSuggestVo (
            suggests = str
        )
    }

    private fun map(
        aInfos: List<AlbumDoc>,
        uInfoMap: Map<Long, UserInfoDto>,
        aPhotoMap: Map<Long, List<AlbumPhoto>>,
        aStatMap: Map<Long, AlbumStat>,
        curUserId: Long,
    ): List<AlbumVo> = run {
        aInfos.map {
            AlbumVo(
                id = it.id,
                userinfo = uInfoMap[it.userId]!!.run {
                    UserInfoSimp(
                        userId = id,
                        username = username,
                        avatar = avatar,
                    )
                },
                title = it.title,
                content = it.content,
                photos = aPhotoMap[it.id]!!.map(::map),
                stat = aStatMap[it.id]!!.run {
                    AlbumStatVo(
                        pageView = pageView,
                        likeCnt = likeCnt,
                        collectCnt = collectCnt,
                        commentCnt = commentCnt,
                        isLike = albumLikeRepo.exists(curUserId, it.id),
                        isCollect = albumCollectRepo.exists(curUserId, it.id),
                    )
                },
                ctTime = DateUtils.formatToString(it.ctTime),
                utTime = DateUtils.formatToString(it.utTime),
            )
        }
    }

    private fun map(o: AlbumPhoto) = with(o) {
        AlbumPhotoSimp (
            photo = photo,
            sort = sort,
        )
    }

}