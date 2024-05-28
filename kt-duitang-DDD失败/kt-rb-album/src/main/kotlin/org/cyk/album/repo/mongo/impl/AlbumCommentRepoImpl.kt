package org.cyk.album.repo.mongo.impl

import org.cyk.album.facade.CommentDto
import org.cyk.album.facade.PageCommentVoDto
import org.cyk.album.repo.mongo.AlbumCommentRepo
import org.cyk.base.handler.PageResp
import org.cyk.base.utils.DateUtils
import org.springframework.data.annotation.Id
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service
import jakarta.annotation.Resource

@Document("album_comment")
data class AlbumCommentDo(
    @Id
    val id: String? = null,
    val postId: Long,
    val targetId: Long,
    val content: String,
    val likeCnt: Long? = null,
    val commentCnt: Long? = null,
    val ctTime: Long? = null,
    val utTime: Long,
)

@Service
class AlbumCommentRepoImpl: AlbumCommentRepo {

    @Resource private lateinit var mongoTemplate: MongoTemplate

    override fun save(dto: CommentDto): Int {
        val obj = map(dto)
        val result = mongoTemplate.save(obj)
        return 1
    }

    override fun incrCommentCnt(id: String, incr: Long): Long {
        val c = Criteria.where("_id").`is`(id)
        val u = Update().inc("comment_cnt", incr)
        val result = mongoTemplate.updateFirst(Query.query(c), u, AlbumCommentDo::class.java)
        return result.modifiedCount
    }

    override fun incrLikeCnt(id: String, incr: Long): Long {
        val c = Criteria.where("_id").`is`(id)
        val u = Update().inc("like_cnt", incr)
        val result = mongoTemplate.updateFirst(Query.query(c), u, AlbumCommentDo::class.java)
        return result.modifiedCount
    }

    override fun queryById(id: String): AlbumComment? {
        val c = Criteria.where("_id").`is`(id)
        val result = mongoTemplate.findOne(Query.query(c), AlbumCommentDo::class.java)?.let { map(it) }
        return result
    }

    override fun del(id: String): Long {
        val c = Criteria.where("_id").`is`(id)
        val result = mongoTemplate.remove(Query.query(c), AlbumCommentDo::class.java)
        return result.deletedCount
    }

    override fun pageComment(dto: PageCommentVoDto): PageResp<AlbumComment> = with(dto) {
        val offset = (start - 1L) * limit
        val c = Criteria.where("target_id").`is`(targetId)
        val result = mongoTemplate.find(
            Query.query(c)
                .skip(offset)
                .limit(limit + 1)
                .with(Sort.by(Sort.Order.desc("ct_time"))),
            AlbumCommentDo::class.java
        ).map(::map)
            .toMutableList()
        val hasMore = result.size == limit + 1
        if(hasMore) {
            result.removeLast()
        }
        return PageResp.ok(
            hasMore,
            dto.start + 1L,
            result,
            null
        )
    }
    fun map(o: AlbumCommentDo): AlbumComment = o.run {
        AlbumComment(
            id = id!!,
            postId = postId,
            targetId = targetId,
            content = content,
            likeCnt = likeCnt!!,
            commentCnt = commentCnt!!,
            ctTime = ctTime!!,
            utTime = utTime,
        )
    }

    fun map(dto: CommentDto): AlbumCommentDo = with(dto) {
        AlbumCommentDo(
            postId = postId,
            targetId = targetId,
            content = content,
            likeCnt = 0,
            commentCnt = 0,
            ctTime = DateUtils.now(),
            utTime = DateUtils.now(),
        )
    }

}

data class AlbumComment(
    val id: String,
    val postId: Long,
    val targetId: Long,
    val content: String,
    val likeCnt: Long,
    val commentCnt: Long,
    val ctTime: Long,
    val utTime: Long,
)
