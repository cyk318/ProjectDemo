package org.cyk.album.service.message

import com.fasterxml.jackson.databind.ObjectMapper
import org.cyk.album.infra.constants.MqConst
import org.cyk.album.infra.constants.SuggestScoreConst
import org.cyk.album.infra.type.AlbumStatType
import org.cyk.album.repo.mongo.AlbumCommentChildRepo
import org.cyk.album.repo.mongo.AlbumCommentRepo
import org.cyk.album.repo.mongo.AlbumStatRepo
import org.cyk.album.repo.mysql.AlbumInfoRepo
import org.cyk.album.service.manager.AlbumRedisManager
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import jakarta.annotation.Resource

@Component
class AlbumCommentConsumer {

    @Resource private lateinit var mapper: ObjectMapper
    @Resource private lateinit var statRepo: AlbumStatRepo
    @Resource private lateinit var commentRepo: AlbumCommentRepo
    @Resource private lateinit var commentChildRepo: AlbumCommentChildRepo
    @Resource private lateinit var infoRepo: AlbumInfoRepo
    @Resource private lateinit var redisManager: AlbumRedisManager

    @RabbitListener(queues = [MqConst.ADD_ALBUM_COMMENT_EVENT])
    fun addAlbumCommentHandler(json: String) {
        val msg = mapper.readValue(json, AddAlbumCommentMsg::class.java)
        with(msg) {
            statRepo.incr(targetId, incr, AlbumStatType.COMMENT_CNT)
            //搜推
            val title = infoRepo.queryById(targetId)!!.title
            redisManager.addSearchSuggestForZSet(postId, title, SuggestScoreConst.COMMENT)
        }
    }

    @RabbitListener(queues = [MqConst.DEL_ALBUM_COMMENT_EVENT])
    fun delAlbumCommentHandler(json: String) {
        val msg = mapper.readValue(json, DelAlbumCommentMsg::class.java)
        with(msg) {
            statRepo.incr(albumId, incr, AlbumStatType.COMMENT_CNT)
        }
    }

    @Transactional
    @RabbitListener(queues = [MqConst.ADD_ALBUM_COMMENT_CHILD_EVENT])
    fun addAlbumCommentChildHandler(json: String) {
        val msg = mapper.readValue(json, AddAlbumCommentChildMsg::class.java)
        with(msg) {
            statRepo.incr(albumId, incr, AlbumStatType.COMMENT_CNT)
            commentRepo.incrCommentCnt(parentId, incr)
        }
    }

    @Transactional
    @RabbitListener(queues = [MqConst.DEL_ALBUM_COMMENT_CHILD_EVENT])
    fun delAlbumCommentChildHandler(json: String) {
        val msg = mapper.readValue(json, DelAlbumCommentChildMsg::class.java)
        with(msg) {
            statRepo.incr(albumId, incr, AlbumStatType.COMMENT_CNT)
            commentRepo.incrCommentCnt(parentId, incr)
        }
    }

    @RabbitListener(queues = [MqConst.ADD_ALBUM_COMMENT_LIKE_EVENT])
    fun addAlbumCommentLikeHandler(json: String) {
        val msg = mapper.readValue(json, AddAlbumCommentLikeMsg::class.java)
        with(msg) {
            commentRepo.incrLikeCnt(commentId, incr)
        }
    }

    @RabbitListener(queues = [MqConst.ADD_ALBUM_COMMENT_CHILD_LIKE_EVENT])
    fun addAlbumCommentChildLikeHandler(json: String) {
        val msg = mapper.readValue(json, AddAlbumCommentChildLikeMsg::class.java)
        with(msg) {
            commentChildRepo.incr(commentId, incr)
        }
    }

}
