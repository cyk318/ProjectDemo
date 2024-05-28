package org.cyk.album.repo.mongo.impl

import org.cyk.album.facade.CommentLikeDto
import org.cyk.album.repo.mongo.AlbumCommentLikeRepo
import org.cyk.base.utils.DateUtils
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import jakarta.annotation.Resource

@Document("album_comment_like")
data class AlbumCommentLikeDo(
    var postId: Long,
    var targetId: String,
    var ctTime: Long,
    var utTime: Long,
)

@Service
class AlbumCommentLikeRepoImpl: AlbumCommentLikeRepo {

    @Resource private lateinit var mongoTemplate: MongoTemplate

    override fun delByCommentId(id: String): Long {
        val c = Criteria.where("_id").`is`(id)
        val result = mongoTemplate.remove(Query.query(c), AlbumCommentLikeDo::class.java)
        return result.deletedCount
    }

    override fun del(postId: Long, targetId: String): Long {
        val c = Criteria()
        c.and("post_id").`is`(postId)
        c.and("target_id").`is`(targetId)
        val result = mongoTemplate.remove(Query.query(c), AlbumCommentLikeDo::class.java)
        return result.deletedCount
    }

    override fun exists(postId: Long, targetId: String): Boolean {
        val c = Criteria()
        c.and("post_id").`is`(postId)
        c.and("target_id").`is`(targetId)
        val result = mongoTemplate.exists(Query.query(c), AlbumCommentLikeDo::class.java)
        return result
    }

    override fun save(dto: CommentLikeDto): Long {
        val obj = map(dto)
        val result = mongoTemplate.save(obj)
        return 1
    }

    private fun map(dto: CommentLikeDto): AlbumCommentLikeDo = with(dto) {
        AlbumCommentLikeDo(
            postId = postId,
            targetId = targetId,
            ctTime = DateUtils.now(),
            utTime = DateUtils.now(),
        )
    }


}

data class AlbumCommentLike(
    var postId: Long,
    var targetId: String,
    var ctTime: Long,
    var utTime: Long,
)
