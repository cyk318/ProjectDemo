package org.cyk.album.service.cmd.impl

import org.cyk.album.facade.*
import org.cyk.album.repo.mongo.AlbumCollectRepo
import org.cyk.album.repo.mongo.AlbumLikeRepo
import org.cyk.album.repo.mongo.AlbumStatRepo
import org.cyk.album.repo.mongo.impl.AlbumStat
import org.cyk.album.repo.mysql.AlbumInfoRepo
import org.cyk.album.repo.mysql.AlbumPhotoRepo
import org.cyk.album.repo.mysql.mapper.AlbumInfo
import org.cyk.album.repo.mysql.mapper.AlbumPhoto
import org.cyk.album.service.cmd.AlbumCmd
import org.cyk.album.service.impl.QueryAlbumVoDto
import org.cyk.album.service.manager.AlbumDiskManager
import org.cyk.album.service.rpc.UserInfoServiceRpcImpl
import org.cyk.base.handler.PageResp
import org.cyk.base.utils.DateUtils
import org.cyk.feign.user.UserInfoDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.IOException
import java.lang.RuntimeException
import jakarta.annotation.Resource

@Service
class AlbumCmdImpl: AlbumCmd {

    @Resource private lateinit var infoRepo: AlbumInfoRepo
    @Resource private lateinit var statRepo: AlbumStatRepo
    @Resource private lateinit var photoRepo: AlbumPhotoRepo
    @Resource private lateinit var diskManager: AlbumDiskManager
    @Resource private lateinit var userInfoService: UserInfoServiceRpcImpl
    @Resource private lateinit var likeRepo: AlbumLikeRepo
    @Resource private lateinit var collectRepo: AlbumCollectRepo

    @Transactional(rollbackFor = [IOException::class, RuntimeException::class])
    override fun saveAlbum(dto: AlbumPubDto): Int {
        //存数据库
        val result = infoRepo.save(dto)

        val photoDto = dto.photosDto.map { it.apply { this.albumId = dto.albumId } }
        photoRepo.saveBatch(photoDto)

        statRepo.save(dto)
        //存硬盘(专辑照片)
        diskManager.savePhotos(photoDto)
        return result
    }

    @Transactional(rollbackFor = [IOException::class, RuntimeException::class])
    override fun updateAlbum(d: AlbumUpdateDto): Int {
        //查询该专辑的所有照片路径 paths
        val paths = photoRepo.queryByAlbumId(d.albumId).map(AlbumPhoto::photo)

        //数据库: 修改信息
        d.photosDto?.let {
            photoRepo.delByAlbumId(d.albumId)
            photoRepo.saveBatch(it)
        }

        val result = infoRepo.updateAlbumInfo(d)

        //硬盘: 先删除后添加
        d.photosDto?.let { dtos ->
             diskManager.delPhotos(paths)
            diskManager.savePhotos(dtos)
        }
        return result
    }

    @Transactional
    override fun deleteAlbum(d: AlbumDelDto): Int {
        val photoPaths = photoRepo.queryByAlbumId(d.albumId).map(AlbumPhoto::photo)
        //删库
        val result = infoRepo.delById(d.albumId)
        photoRepo.delByAlbumId(d.albumId)
        statRepo.delByAlbumId(d.albumId)
        //删硬盘
        diskManager.delPhotos(photoPaths)
        return result
    }

    override fun pageAlbumVo(q: QueryAlbumVoDto, curUserId: Long): PageResp<AlbumVo> {
        val aPage = infoRepo.pageAlbumVo(q)
        val aIds = aPage.result.map(AlbumInfo::id)

        val aInfos = aPage.result
        val uInfoMap = userInfoService.queryByUserIds(aInfos.map(AlbumInfo::userId)).associateBy { it.id }
        val aPhotoMap = photoRepo.queryByAlbumIds(aIds).groupBy { it.albumId }
        val aStatMap = statRepo.queryByAlbumIds(aIds).associateBy { it.id }

        return PageResp.ok(
            aPage.hasMore,
            aPage.nextStart,
            map(aInfos, uInfoMap, aPhotoMap, aStatMap, curUserId),
            aPage.total
        )
    }

    override fun queryAlbumVo(o: AlbumInfo, curUserId: Long): AlbumVo {
        val aInfo = o
        val uInfo = userInfoService.queryByUserId(aInfo.userId)
        val aPhoto = photoRepo.queryByAlbumId(aInfo.id)
        val aStat = statRepo.queryByAlbumId(aInfo.id)
        return AlbumVo(
            userinfo = uInfo!!.run {
                UserInfoSimp(
                    userId = id,
                    username = username,
                    avatar = avatar,
                )
            },
            id = aInfo.id,
            title = aInfo.title,
            content = aInfo.content,
            photos = aPhoto.map(::map),
            stat = aStat!!.run {
                AlbumStatVo(
                    pageView = pageView,
                    likeCnt = likeCnt,
                    collectCnt = collectCnt,
                    commentCnt = commentCnt,
                    isLike = likeRepo.exists(curUserId, aInfo.id),
                    isCollect = collectRepo.exists(curUserId, aInfo.id)
                )
            },
            ctTime = DateUtils.formatToString(aInfo.ctTime),
            utTime = DateUtils.formatToString(aInfo.utTime),
        )
    }


    private fun map(
        aInfo: List<AlbumInfo>,
        uInfo: Map<Long, UserInfoDto>,
        aPhoto: Map<Long, List<AlbumPhoto>>,
        aStat: Map<Long, AlbumStat>,
        curUserId: Long
    ): List<AlbumVo> = aInfo.map {
        with(it) {
            AlbumVo(
                userinfo = with(uInfo[userId]!!) {
                   UserInfoSimp(
                       userId = id,
                       username = username,
                       avatar = avatar
                   )
                },
                id = id,
                title = title,
                content = content,
                photos = aPhoto[id]!!.map(::map),
                stat = with(aStat[id]!!) {
                    AlbumStatVo(
                        pageView = pageView,
                        likeCnt = likeCnt,
                        commentCnt = commentCnt,
                        collectCnt = collectCnt,
                        isLike = likeRepo.exists(curUserId, id),
                        isCollect = collectRepo.exists(curUserId, id)
                    )
                },
                ctTime = DateUtils.formatToString(ctTime),
                utTime = DateUtils.formatToString(utTime),
            )
        }
    }

    private fun map(o: AlbumPhoto): AlbumPhotoSimp = with(o) {
        AlbumPhotoSimp(
            photo = photo,
            sort = sort
        )
    }

}