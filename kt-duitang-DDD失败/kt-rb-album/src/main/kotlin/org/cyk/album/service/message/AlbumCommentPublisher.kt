package org.cyk.album.service.message

import com.fasterxml.jackson.databind.ObjectMapper
import org.cyk.album.facade.CommentChildDto
import org.cyk.album.facade.CommentDel
import org.cyk.album.facade.CommentDto
import org.cyk.album.infra.constants.MqConst
import org.cyk.album.repo.mongo.AlbumCommentRepo
import org.cyk.album.repo.mysql.AlbumInfoRepo
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component
import jakarta.annotation.Resource


@Component
class AlbumCommentPublisher {

    @Resource lateinit var mapper: ObjectMapper
    @Resource lateinit var rabbitTemplate: RabbitTemplate
    @Resource lateinit var commentRepo: AlbumCommentRepo
    @Resource lateinit var infoRepo: AlbumInfoRepo

    fun addAlbumComment(dto: CommentDto, incr: Int) = with(dto) {
        val msg = AddAlbumCommentMsg(
            postId = postId,
            targetId = targetId,
            incr = incr.toLong(),
        )
        rabbitTemplate.convertAndSend(
            MqConst.ALBUM_COMMENT_DIRECT,
            MqConst.ADD_ALBUM_COMMENT_EVENT,
            mapper.writeValueAsString(msg)
        )
    }

    fun delAlbumComment(dto: CommentDel, albumId: Long, incr: Long) = with(dto) {
        val msg = DelAlbumCommentMsg(
            userId = postId,
            albumId = albumId,
            commentId = targetId,
            incr = incr
        )
        rabbitTemplate.convertAndSend(
            MqConst.ALBUM_COMMENT_DIRECT,
            MqConst.DEL_ALBUM_COMMENT_EVENT,
            mapper.writeValueAsString(msg)
        )
    }

    fun addAlbumCommentChild(dto: CommentChildDto, incr: Int) = with(dto) {
        val albumId = commentRepo.queryById(parentId)!!.targetId
        val msg = AddAlbumCommentChildMsg(
            userId = postId,
            parentId = parentId,
            albumId = albumId,
            incr = incr.toLong(),
        )
        rabbitTemplate.convertAndSend(
            MqConst.ALBUM_COMMENT_DIRECT,
            MqConst.ADD_ALBUM_COMMENT_CHILD_EVENT,
            mapper.writeValueAsString(msg)
        )
    }

    fun delAlbumCommentChild(
        parentId: String,
        incr: Long
    ) {
        val albumId = commentRepo.queryById(parentId)!!.targetId
        val msg = DelAlbumCommentChildMsg(
            albumId = albumId,
            parentId = parentId,
            incr = incr,
        )
        rabbitTemplate.convertAndSend(
            MqConst.ALBUM_COMMENT_DIRECT,
            MqConst.DEL_ALBUM_COMMENT_CHILD_EVENT,
            mapper.writeValueAsString(msg),
        )
    }

    fun addAlbumCommentLike(targetId: String, incr: Long) {
        val msg = AddAlbumCommentLikeMsg(
            commentId = targetId,
            incr = incr
        )
        rabbitTemplate.convertAndSend(
            MqConst.ALBUM_COMMENT_DIRECT,
            MqConst.ADD_ALBUM_COMMENT_LIKE_EVENT,
            mapper.writeValueAsString(msg)
        )
    }

    fun addAlbumCommentChildLike(targetId: String, incr: Long) {
        val msg = AddAlbumCommentChildLikeMsg(
            commentId = targetId,
            incr = incr
        )
        rabbitTemplate.convertAndSend(
            MqConst.ALBUM_COMMENT_DIRECT,
            MqConst.ADD_ALBUM_COMMENT_CHILD_LIKE_EVENT,
            mapper.writeValueAsString(msg)
        )
    }

}

data class AddAlbumCommentChildLikeMsg(
    var commentId: String = "",
    var incr: Long = 0,
)

data class AddAlbumCommentLikeMsg(
    var commentId: String = "",
    var incr: Long = 0,
)

data class DelAlbumCommentChildMsg(
    var albumId: Long = -1,
    var parentId: String = "",
    var incr: Long = 0,
)

data class AddAlbumCommentChildMsg(
    var userId: Long = -1,
    var albumId: Long = -1,
    var parentId: String = "",
    var incr: Long = 0,
)

data class DelAlbumCommentMsg(
    var userId: Long = -1,
    var albumId: Long = -1,
    var commentId: String = "",
    var incr: Long = 0,
)

data class AddAlbumCommentMsg(
    var postId: Long = -1,
    var targetId: Long = -1,
    var incr: Long = 0,
)

