package org.cyk.album.service.impl

import org.cyk.album.facade.*
import org.cyk.album.repo.mongo.AlbumCommentChildLikeRepo
import org.cyk.album.repo.mongo.AlbumCommentChildRepo
import org.cyk.album.repo.mongo.AlbumCommentLikeRepo
import org.cyk.album.repo.mongo.AlbumCommentRepo
import org.cyk.album.repo.mysql.AlbumInfoRepo
import org.cyk.album.service.AlbumCommentService
import org.cyk.album.service.cmd.AlbumCommentCmd
import org.cyk.album.service.message.AlbumCommentPublisher
import org.cyk.album.service.rpc.UserInfoServiceRpcImpl
import org.cyk.base.exception.AppException2
import org.cyk.base.handler.ApiResp
import org.cyk.base.handler.ApiStatus
import org.cyk.base.handler.PageResp
import org.cyk.base.model.type.UserMsgStateType
import org.cyk.msg.publisher.UserMsgPublisher
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AlbumCommentServiceImpl(
    val commentRepo: AlbumCommentRepo,
    val infoRepo: AlbumInfoRepo,
    val cmd: AlbumCommentCmd,
    val commentChildRepo: AlbumCommentChildRepo,
    val commentPublisher: AlbumCommentPublisher,
    val commentLikeRepo: AlbumCommentLikeRepo,
    val commentLikeChildRepo: AlbumCommentChildLikeRepo,
    val userMsgPublisher: UserMsgPublisher,
    val userInfoService: UserInfoServiceRpcImpl,
): AlbumCommentService {

    @Transactional
    override fun comment(dto: CommentDto): Int {
        isParamOk(dto)
        val result = commentRepo.save(dto)

        commentPublisher.addAlbumComment(dto, result)
        val userId = infoRepo.queryById(dto.targetId)!!.userId
        userMsgPublisher.sendUserMsg(userId, dto.postId, dto.targetId.toString(), dto.content, UserMsgStateType.COMMENT_STATE.code)
        return result
    }

    override fun del(dto: CommentDel): Long {
        isParamOk(dto)
        val albumId = commentRepo.queryById(dto.targetId)!!.targetId //先删库，再发消息，处理消息的时候就找不到了

        val result = -cmd.del(dto)
        commentPublisher.delAlbumComment(dto, albumId, result)
        return result
    }

    override fun delChild(dto: CommentChildDel): Long {
        isParamOk(dto)
        val parentId = commentChildRepo.queryById(dto.targetId)!!.parentId //先删库，再发消息，处理消息的时候就找不到了

        val result = -cmd.delChild(dto)
        commentPublisher.delAlbumCommentChild(parentId, result)
        return result
    }

    override fun addChild(dto: CommentChildDto): Int {
        isParamOk(dto)
        val result = commentChildRepo.save(dto)
        commentPublisher.addAlbumCommentChild(dto, result)
        return result
    }

    override fun pageCommentChildVo(dto: PageCommentChildDto, curUserId: Long): PageResp<CommentChildVo> {
        return cmd.pageCommentChildVo(dto, curUserId)
    }

    override fun like(dto: CommentLikeDto): Long {
        isParamOk(dto)
        val exists = commentLikeRepo.exists(dto.postId, dto.targetId)
        val result = if(!exists) {
            +commentLikeRepo.save(dto)
        } else {
            -commentLikeRepo.del(dto.postId, dto.targetId)
        }
        commentPublisher.addAlbumCommentLike(dto.targetId, result)
        return result
    }

    override fun likeChild(dto: CommentChildLikeDto): Long {
        isParamOk(dto)
        val exists = commentLikeChildRepo.exists(dto.postId, dto.targetId)
        val result = if(!exists) {
            +commentLikeChildRepo.save(dto)
        } else {
            -commentLikeChildRepo.del(dto.postId, dto.targetId)
        }
        commentPublisher.addAlbumCommentChildLike(dto.targetId, result)
        return result
    }

    override fun pageCommentVo(dto: PageCommentVoDto, curUserId: Long): PageResp<CommentVo> {
        val result = cmd.pageCommentVo(dto, curUserId)
        return result
    }

    private fun isParamOk(dto: CommentLikeDto) = with(dto) {
        val exists = commentRepo.queryById(targetId) != null
        if(!exists) {
            log.warn("点赞的评论不存在！commentId: $targetId")
            throw AppException2(ApiResp.no(ApiStatus.INVALID_REQUEST))
        }
    }

    private fun isParamOk(dto: CommentChildLikeDto) = with(dto) {
        val exists = commentChildRepo.queryById(targetId) != null
        if(!exists) {
            log.warn("点赞的子评论不存在！commentId: $targetId")
            throw AppException2(ApiResp.no(ApiStatus.INVALID_REQUEST))
        }
    }

    private fun isParamOk(dto: CommentChildDto) = dto.run {
        val ok1 = commentRepo.queryById(parentId) != null
        if(!ok1) {
            log.warn("父评论 id 非法(不存在): $parentId")
            throw AppException2(ApiResp.no(ApiStatus.INVALID_PARAM))
        }
        val ok2 = userInfoService.queryByUserId(dto.targetId) != null
        if(!ok2) {
            log.warn("用户 id 非法 targetId: $targetId")
            throw AppException2(ApiResp.no(ApiStatus.INVALID_PARAM))
        }
    }

    private fun isParamOk(dto: CommentDel) = dto.run {
        val obj = commentRepo.queryById(targetId)
        if(obj == null) {
            log.warn("要删除的评论不存在！commentId: $targetId")
            throw AppException2(ApiResp.no(ApiStatus.INVALID_REQUEST))
        }
        if(obj.postId != postId) {
            log.warn("不能删除其他人的评论！postId: $postId, author: ${obj.postId}")
            throw AppException2(ApiResp.no(ApiStatus.INVALID_REQUEST))
        }
    }

    private fun isParamOk(dto: CommentDto) = dto.run {
        val exists = infoRepo.queryById(targetId) != null
        if(!exists) {
            log.warn("专辑评论时，发现专辑 id 非法: $targetId")
            throw AppException2(ApiResp.no(ApiStatus.INVALID_REQUEST))
        }
    }

    private fun isParamOk(dto: CommentChildDel) = dto.run {
        val obj = commentChildRepo.queryById(targetId)
        if(obj == null) {
            log.warn("删除子评论时，子评论 id 非法: ${dto.targetId}")
            throw AppException2(ApiResp.no(ApiStatus.INVALID_REQUEST))
        }
        if(obj.postId != postId) {
            log.warn("不可以删除其他人的子评论！postId: $postId, authorId: ${obj.postId}")
            throw AppException2(ApiResp.no(ApiStatus.INVALID_REQUEST))
        }

    }

    private var log = LoggerFactory.getLogger(AlbumCommentServiceImpl::class.java)

}