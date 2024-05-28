package org.cyk.album.service.message

import com.fasterxml.jackson.databind.ObjectMapper
import org.cyk.album.infra.constants.MqConst
import org.cyk.album.infra.constants.SuggestScoreConst
import org.cyk.album.infra.type.AlbumStatType
import org.cyk.album.repo.es.AlbumDocRepo
import org.cyk.album.repo.mongo.AlbumLikeRepo
import org.cyk.album.repo.mongo.AlbumStatRepo
import org.cyk.album.repo.mysql.AlbumInfoRepo
import org.cyk.album.service.manager.AlbumRedisManager
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import jakarta.annotation.Resource

@Component
class AlbumInfoConsumer {

    @Resource private lateinit var mapper: ObjectMapper
    @Resource private lateinit var likeRepo: AlbumLikeRepo
    @Resource private lateinit var statRepo: AlbumStatRepo
    @Resource private lateinit var albumDocRepo: AlbumDocRepo
    @Resource private lateinit var redisManager: AlbumRedisManager
    @Resource private lateinit var infoRepo: AlbumInfoRepo

    @RabbitListener(queues = [MqConst.QUERY_ALBUM_INFO_VO_EVENT])
    fun queryAlbumVoEventHandler(json: String) {
        val msg = mapper.readValue(json, QueryAlbumVoMsg::class.java)
        with(msg) {
            statRepo.incr(albumId, 1, AlbumStatType.PV)
            //搜推
            redisManager.addSearchSuggestForZSet(userId, title, SuggestScoreConst.PV)
        }
    }

    @Transactional
    @RabbitListener(queues = [MqConst.ADD_ALBUM_LIKE_EVENT])
    fun addAlbumLikeEventHandler(json: String) {
        val msg = mapper.readValue(json, AddAlbumLikeMsg::class.java)
        with(msg) {
            statRepo.incr(targetId, incr, AlbumStatType.LIKE_CNT)
            if(msg.incr > 0) {
                likeRepo.save(postId, targetId)
            } else {
                likeRepo.delete(postId, targetId)
            }
            //搜推
            val title = infoRepo.queryById(targetId)!!.title
            redisManager.addSearchSuggestForZSet(postId, title, SuggestScoreConst.LIKE)
        }
    }

    @Transactional
    @RabbitListener(queues = [MqConst.ADD_ALBUM_COLLECT_EVENT])
    fun addAlbumCollectEventHandler(json: String) {
        val msg = mapper.readValue(json, AddAlbumCollectMsg::class.java)
        with(msg) {
            statRepo.incr(targetId, incr, AlbumStatType.COLLECT_CNT)
            if(msg.incr > 0) {
                likeRepo.save(postId, targetId)
            } else {
                likeRepo.delete(postId, targetId)
            }
            //搜推
            val title = infoRepo.queryById(targetId)!!.title
            redisManager.addSearchSuggestForZSet(postId, title, SuggestScoreConst.COLLECT)
        }
    }

    @RabbitListener(queues = [MqConst.ADD_ALBUM_INFO_EVENT])
    fun addAlbumInfoEventHandler(json: String) {
        val msg = mapper.readValue(json, AddAlbumInfoMsg::class.java)
        albumDocRepo.save(msg)
    }

    @RabbitListener(queues = [MqConst.UPDATE_ALBUM_INFO_EVENT])
    fun updateAlbumInfoEventHandler(json: String) {
        val msg = mapper.readValue(json, UpdateAlbumInfoMsg::class.java)
        albumDocRepo.update(msg)
    }

    @RabbitListener(queues = [MqConst.DEL_ALBUM_INFO_EVENT])
    fun delAlbumInfoEventHandler(json: String) {
        val msg = mapper.readValue(json, DelAlbumInfoMsg::class.java)
        albumDocRepo.delete(msg.albumId)
    }

}