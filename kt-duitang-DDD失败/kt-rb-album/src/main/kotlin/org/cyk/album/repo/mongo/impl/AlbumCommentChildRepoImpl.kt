package org.cyk.album.repo.mongo.impl

import org.cyk.album.facade.CommentChildDto
import org.cyk.album.facade.PageCommentChildDto
import org.cyk.album.repo.mongo.AlbumCommentChildRepo
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

@Document("album_comment_child")
data class AlbumCommentChildDo (
    @Id
    val id: String? = null,
    val parentId: String,
    val postId: Long, //评论发起者
    val targetId: Long, //给谁回复
    val content: String,
    val likeCnt: Long,
    val ctTime: Long? = null,
    val utTime: Long,
)

@Service
class AlbumCommentChildRepoImpl: AlbumCommentChildRepo {

    @Resource private lateinit var mongoTemplate: MongoTemplate

    override fun incr(id: String, incr: Long): Long {
        val c = Criteria.where("_id").`is`(id)
        val u = Update().inc("like_cnt", incr)
        val result = mongoTemplate.updateFirst(Query.query(c), u, AlbumCommentChildDo::class.java)
        return result.modifiedCount
    }

    override fun save(dto: CommentChildDto): Int {
        val obj = map(dto)
        val result = mongoTemplate.save(obj)
        return 1
    }

    override fun queryById(id: String): AlbumCommentChild? {
        val c = Criteria.where("_id").`is`(id)
        val result = mongoTemplate.findOne(Query.query(c), AlbumCommentChildDo::class.java)?.let { map(it) }
        return result
    }

    override fun del(targetId: String): Long {
        val c = Criteria.where("_id").`is`(targetId)
        val result = mongoTemplate.remove(Query.query(c), AlbumCommentChildDo::class.java)
        return result.deletedCount
    }

    override fun pageCommentChild(d: PageCommentChildDto): PageResp<AlbumCommentChild> {
        val offset = (d.start - 1L) * d.limit
        val limit = d.limit
        val result = mongoTemplate.find(
            Query.query(Criteria.where("parent_id").`is`(d.targetId))
                .with(Sort.by(Sort.Order.desc("ct_time")))
                .skip(offset)
                .limit(limit + 1),
            AlbumCommentChildDo::class.java)
            .map(::map)
            .toMutableList()

        val hasMore = result.size == limit + 1
        if(hasMore) {
             result.removeLast()
        }
        return PageResp.ok(
            hasMore,
            d.start + 1L,
            result,
            null
        )
    }

    private fun map(o: AlbumCommentChildDo) = o.run {
        AlbumCommentChild(
            id = id!!,
            parentId = parentId,
            postId = postId,
            targetId = targetId,
            content = content,
            likeCnt = likeCnt,
            ctTime = ctTime!!,
            utTime = utTime,
        )
    }

    private fun map(o: CommentChildDto): AlbumCommentChildDo = o.run {
        AlbumCommentChildDo(
            parentId = parentId,
            postId = postId,
            targetId = targetId,
            content = content,
            likeCnt = 0,
            ctTime = DateUtils.now(),
            utTime = DateUtils.now(),
        )
    }

}

data class AlbumCommentChild (
    val id: String,
    val parentId: String,
    val postId: Long, //评论发起者
    val targetId: Long, //给谁回复
    val content: String,
    val likeCnt: Long,
    val ctTime: Long,
    val utTime: Long,
)
