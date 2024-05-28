package org.cyk.album.repo.mongo.impl

import org.cyk.album.facade.CommentChildLikeDto
import org.cyk.album.repo.mongo.AlbumCommentChildLikeRepo
import org.cyk.base.utils.DateUtils
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import jakarta.annotation.Resource

@Document("album_comment_child_like")
data class AlbumCommentChildLikeDo(
    var postId: Long,
    var targetId: String,
    var ctTime: Long,
    var utTime: Long,
)

@Service
class AlbumCommentChildLikeRepoImpl: AlbumCommentChildLikeRepo {

    @Resource private lateinit var mongoTemplate: MongoTemplate

    override fun delByCommentId(id: String): Long {
        val c = Criteria.where("_id").`is`(id)
        val result = mongoTemplate.remove(Query.query(c), AlbumCommentChildLikeDo::class.java)
        return result.deletedCount
    }

    override fun exists(postId: Long, targetId: String): Boolean {
        val c = Criteria()
        c.and("post_id").`is`(postId)
        c.and("target_id").`is`(targetId)
        val result = mongoTemplate.exists(Query.query(c), AlbumCommentChildLikeDo::class.java)
        return result
    }

    override fun save(dto: CommentChildLikeDto): Long {
        val obj = map(dto)
        val result = mongoTemplate.save(obj)
        return 1
    }

    override fun del(postId: Long, targetId: String): Long {
        val c = Criteria()
        c.and("post_id").`is`(postId)
        c.and("target_id").`is`(targetId)
        val result = mongoTemplate.remove(Query.query(c), AlbumCommentChildLikeDo::class.java)
        return result.deletedCount
    }

    private fun map(dto: CommentChildLikeDto): AlbumCommentChildLikeDo = with(dto) {
        AlbumCommentChildLikeDo(
            postId = postId,
            targetId = targetId,
            ctTime = DateUtils.now(),
            utTime = DateUtils.now(),
        )
    }

}

data class AlbumCommentChildLike(
    var postId: Long,
    var targetId: String,
    var ctTime: Long,
    var utTime: Long,
)
