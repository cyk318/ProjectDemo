package org.cyk.album.repo.mongo.impl

import org.cyk.album.repo.mongo.AlbumCollectRepo
import org.cyk.base.utils.DateUtils
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import jakarta.annotation.Resource

@Document("album_collect")
data class AlbumCollectDo(
    var postId: Long,
    var targetId: Long,
    var ctTime: Long,
    var utTime: Long
)

@Service
class AlbumCollectRepoImpl: AlbumCollectRepo {

    @Resource lateinit var mongoTemplate: MongoTemplate

    override fun save(postId: Long, targetId: Long): Long {
        val obj = map(postId, targetId)
        mongoTemplate.save(obj)
        return 1
    }

    private fun map(postId: Long, targetId: Long): AlbumCollectDo = run {
        AlbumCollectDo(
            postId = postId,
            targetId = targetId,
            ctTime = DateUtils.now(),
            utTime = DateUtils.now(),
        )
    }

    override fun delete(postId: Long, targetId: Long): Long {
        val c = Criteria()
        c.and("post_id").`is`(postId)
        c.and("target_id").`is`(targetId)
        val result = mongoTemplate.remove(
            Query.query(c),
            AlbumCollectDo::class.java
        )
        return result.deletedCount
    }

    override fun queryByUserId(userId: Long): List<AlbumCollect?> {
        val result = mongoTemplate.find(
            Query.query(Criteria.where("post_id").`is`(userId)),
            AlbumCollectDo::class.java
        ).map(::map)
        return result
    }

    override fun exists(postId: Long, targetId: Long): Boolean {
        val c = Criteria()
        c.and("post_id").`is`(postId)
        c.and("target_id").`is`(targetId)
        val result = mongoTemplate.exists(
            Query.query(c),
            AlbumCollectDo::class.java
        )
        return result
    }


    fun map(o: AlbumCollectDo?): AlbumCollect? = o?.run {
        AlbumCollect(
            postId = postId,
            targetId = targetId,
            ctTime = ctTime,
            utTime = utTime
        )
    }


}

data class AlbumCollect(
    var postId: Long,
    var targetId: Long,
    var ctTime: Long,
    var utTime: Long
)
