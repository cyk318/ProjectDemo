package org.cyk.album.service.cmd.impl

import org.cyk.album.facade.*
import org.cyk.album.repo.mongo.AlbumCommentChildLikeRepo
import org.cyk.album.repo.mongo.AlbumCommentChildRepo
import org.cyk.album.repo.mongo.AlbumCommentLikeRepo
import org.cyk.album.repo.mongo.AlbumCommentRepo
import org.cyk.album.repo.mongo.impl.AlbumComment
import org.cyk.album.repo.mongo.impl.AlbumCommentChild
import org.cyk.album.service.cmd.AlbumCommentCmd
import org.cyk.album.service.rpc.UserInfoServiceRpcImpl
import org.cyk.base.handler.PageResp
import org.cyk.base.utils.DateUtils
import org.cyk.feign.user.UserInfoDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import jakarta.annotation.Resource

@Service
class AlbumCommentCmdImpl: AlbumCommentCmd {

    @Resource private lateinit var commentRepo: AlbumCommentRepo
    @Resource private lateinit var commentChildRepo: AlbumCommentChildRepo
    @Resource private lateinit var commentLikeRepo: AlbumCommentLikeRepo
    @Resource private lateinit var commentChildLikeRepo: AlbumCommentChildLikeRepo
    @Resource private lateinit var userInfoService: UserInfoServiceRpcImpl

    @Transactional
    override fun del(dto: CommentDel): Long {
        val result = with(dto) {
            val parentCnt = commentRepo.del(dto.targetId)
            val childCnt = commentChildRepo.del(dto.targetId)
            parentCnt + childCnt
        }
        commentLikeRepo.delByCommentId(dto.targetId)
        return result
    }

    override fun delChild(dto: CommentChildDel): Long {
        val result = with(dto) {
            commentChildRepo.del(targetId)
        }
        commentChildLikeRepo.delByCommentId(dto.targetId)
        return result
    }

    override fun pageCommentChildVo(dto: PageCommentChildDto, curUserId: Long): PageResp<CommentChildVo> {
        val cPage = commentChildRepo.pageCommentChild(dto)
        val cList = cPage.result

        val pUserMap = cList.let {
            val postIds = it.map(AlbumCommentChild::postId)
            userInfoService.queryByUserIds(postIds)
                .map(::map)
                .associateBy(UserInfoSimp::userId)
        }


        val tUserMap = cList.let {
            val targetIds = it.map(AlbumCommentChild::targetId)
            userInfoService.queryByUserIds(targetIds)
                .map(::map)
                .associateBy(UserInfoSimp::userId)
        }

        return PageResp.ok(
            cPage.hasMore,
            cPage.nextStart,
            map(cList, pUserMap, tUserMap, curUserId),
            cPage.total
        )
    }

    override fun pageCommentVo(dto: PageCommentVoDto, curUserId: Long): PageResp<CommentVo> {
        val pPage = commentRepo.pageComment(dto)
        val pList = pPage.result

        val pUserMap = let {
            val postId = pList.map(AlbumComment::postId)
            userInfoService.queryByUserIds(postId)
                .map(::map)
                .associateBy(UserInfoSimp::userId)
        }

        val cList = pList.map {
            val r = pageCommentChildVo(PageCommentChildDto(
                start = 1,
                limit = 1,
                targetId = it.id
            ), curUserId).result
            if(r.isEmpty()) null else r[0]
        }.groupBy { it?.parentId }

        return PageResp.ok(
            pPage.hasMore,
            pPage.nextStart,
            map1(pList, pUserMap, cList, curUserId),
            pPage.total
        )
    }

    private fun map1(
        pList: List<AlbumComment>,
        pUserMap: Map<Long, UserInfoSimp>,
        cList: Map<String?, List<CommentChildVo?>>,
        curUserId: Long,
    ): List<CommentVo> = run {
        pList.map {
            CommentVo(
                id = it.id,
                content = it.content,
                userinfo = pUserMap[it.postId]!!,
                children = cList[it.id] ?: emptyList(),
                likeCnt = it.likeCnt,
                commentCnt = it.commentCnt,
                like = commentLikeRepo.exists(curUserId, it.id),
                ctTime = DateUtils.formatToString(it.ctTime),
            )
        }
    }


    private fun map(
        cList: List<AlbumCommentChild>,
        pUserMap: Map<Long, UserInfoSimp>,
        tUserMap: Map<Long, UserInfoSimp>,
        curUserId: Long,
    ): List<CommentChildVo> {
        return cList.map {
            CommentChildVo(
                id = it.id,
                parentId = it.parentId,
                postUserInfo = pUserMap[it.postId]!!,
                targetUserInfo = tUserMap[it.targetId]!!,
                content = it.content,
                likeCnt = it.likeCnt,
                like = commentChildLikeRepo.exists(curUserId, it.id),
                ctTime = DateUtils.formatToString(it.ctTime)
            )
        }
    }

    private fun map(o: UserInfoDto): UserInfoSimp = with(o) {
        UserInfoSimp(
            userId = id,
            username = username,
            avatar = avatar,
        )
    }

}