package org.cyk.album.service.message

import com.fasterxml.jackson.databind.ObjectMapper
import org.cyk.album.facade.AlbumPubDto
import org.cyk.album.facade.AlbumUpdateDto
import org.cyk.album.facade.CollectDto
import org.cyk.album.facade.LikeDto
import org.cyk.album.infra.constants.MqConst
import org.cyk.base.utils.DateUtils
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component
import jakarta.annotation.Resource

@Component
class AlbumInfoPublisher {

    @Resource private lateinit var mapper: ObjectMapper
    @Resource private lateinit var rabbitTemplate: RabbitTemplate

    fun queryAlbumVoEvent(
        albumId: Long,
        title: String,
        userId: Long,
    ) {
        val msg = QueryAlbumVoMsg(
            albumId = albumId,
            title = title,
            userId = userId,
        )
        rabbitTemplate.convertAndSend(
            MqConst.ALBUM_INFO_DIRECT,
            MqConst.QUERY_ALBUM_INFO_VO_EVENT,
            mapper.writeValueAsString(msg)
        )
    }

    fun addAlbumLikeEvent(dto: LikeDto, incr: Long) = with(dto) {
        val msg = AddAlbumLikeMsg(
            postId = postId,
            targetId = targetId,
            incr = incr,
        )
        rabbitTemplate.convertAndSend(
            MqConst.ALBUM_INFO_DIRECT,
            MqConst.ADD_ALBUM_LIKE_EVENT,
            mapper.writeValueAsString(msg)
        )
    }

    fun addAlbumCollectEvent(dto: CollectDto, incr: Long) = with(dto) {
        val msg = AddAlbumLikeMsg(
            postId = postId,
            targetId = targetId,
            incr = incr,
        )
        rabbitTemplate.convertAndSend(
            MqConst.ALBUM_INFO_DIRECT,
            MqConst.ADD_ALBUM_COLLECT_EVENT,
            mapper.writeValueAsString(msg)
        )
    }

    fun addAlbumInfoEvent(d: AlbumPubDto) = with(d) {
        val msg = AddAlbumInfoMsg(
            albumId = albumId!!,
            userId = userId,
            title = title,
            content = content,
            ctTime = DateUtils.now(),
            utTime = DateUtils.now(),
        )
        rabbitTemplate.convertAndSend(
            MqConst.ALBUM_INFO_DIRECT,
            MqConst.ADD_ALBUM_INFO_EVENT,
            mapper.writeValueAsString(msg)
        )
    }

    fun updateAlbumInfoEvent(d: AlbumUpdateDto) = with(d) {
        val msg = UpdateAlbumInfoMsg (
            albumId = albumId,
            userId = userId,
            title = title,
            content = content,
            utTime = DateUtils.now(),
        )
        rabbitTemplate.convertAndSend(
            MqConst.ALBUM_INFO_DIRECT,
            MqConst.UPDATE_ALBUM_INFO_EVENT,
            mapper.writeValueAsString(msg)
        )
    }

    fun delAlbumInfoEvent(albumId: Long) {
        val msg = DelAlbumInfoMsg (
            albumId = albumId
        )
        rabbitTemplate.convertAndSend(
            MqConst.ALBUM_INFO_DIRECT,
            MqConst.DEL_ALBUM_INFO_EVENT,
            mapper.writeValueAsString(msg)
        )
    }

}

data class DelAlbumInfoMsg(
    val albumId: Long = -1,
)

data class UpdateAlbumInfoMsg (
    val albumId: Long = -1,
    val userId: Long = -1,
    val title: String = "",
    val content: String = "",
    val utTime: Long = -1,
)

data class AddAlbumInfoMsg(
    val albumId: Long = -1,
    val userId: Long = -1,
    val title: String = "",
    val content: String = "",
    val ctTime: Long = -1,
    val utTime: Long = -1,
)

data class AddAlbumCollectMsg(
    var postId: Long = -1,
    var targetId: Long = -1,
    var incr: Long = 0,
)

data class AddAlbumLikeMsg(
    var postId: Long = -1,
    var targetId: Long = -1,
    var incr: Long = 0,
)

data class QueryAlbumVoMsg(
    val albumId: Long = -1,
    val title: String = "",
    val userId: Long = -1,
)