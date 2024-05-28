package org.cyk.album.repo.mongo.impl

import org.cyk.album.repo.mongo.AlbumLikeRepo
import org.cyk.base.utils.DateUtils
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import jakarta.annotation.Resource

@Document("album_like")
data class AlbumLikeDo(
    var postId: Long,
    var targetId: Long,
    var ctTime: Long,
    var utTime: Long
)

@Service
class AlbumLikeRepoImpl: AlbumLikeRepo {

    @Resource private lateinit var mongoTemplate: MongoTemplate

    override fun save(postId: Long, targetId: Long): Long {
        val obj = map(postId, targetId)
        val result = mongoTemplate.save(obj)
        return 1
    }

    override fun delete(postId: Long, targetId: Long): Long {
        val c = Criteria()
        c.and("target_id").`is`(targetId)
        c.and("post_id").`is`(postId)
        val result = mongoTemplate.remove(
            Query.query(c),
            AlbumLikeDo::class.java
        )
        return result.deletedCount
    }

    override fun exists(postId: Long, targetId: Long): Boolean {
        val c = Criteria()
        c.and("target_id").`is`(targetId)
        c.and("post_id").`is`(postId)
        val result = mongoTemplate.exists(
            Query.query(c),
            AlbumLikeDo::class.java
        )
        return result
    }

    override fun queryByUserId(userId: Long): List<AlbumLike?> {
        val result = mongoTemplate.find(
            Query.query(Criteria.where("post_id").`is`(userId)),
            AlbumLikeDo::class.java
        ).map(::map)
        return result
    }

    override fun queryOne(targetId: Long, postId: Long): AlbumLike? {
        val c = Criteria()
        c.and("target_id").`is`(targetId)
        c.and("post_id").`is`(postId)
        val result = mongoTemplate.findOne(
            Query.query(c),
            AlbumLikeDo::class.java
        )?.let { map(it) }
        return result
    }

    fun map(o: AlbumLikeDo?): AlbumLike? = o?.run {
        AlbumLike(
            postId = postId,
            targetId = targetId,
            ctTime = ctTime,
            utTime = utTime
        )
    }

    private fun map(postId: Long, targetId: Long): AlbumLikeDo = run {
        AlbumLikeDo(
            postId = postId,
            targetId = targetId,
            ctTime = DateUtils.now(),
            utTime = DateUtils.now(),
        )
    }

}

data class AlbumLike(
    var postId: Long,
    var targetId: Long,
    var ctTime: Long,
    var utTime: Long
)
