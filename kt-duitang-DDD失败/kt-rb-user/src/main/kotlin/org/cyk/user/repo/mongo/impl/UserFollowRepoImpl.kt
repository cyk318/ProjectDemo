package org.cyk.user.repo.mongo.impl

import org.cyk.user.facade.FollowListDto
import org.cyk.base.handler.PageResp
import org.cyk.base.utils.DateUtils
import org.cyk.user.facade.FollowDto
import org.cyk.user.repo.mongo.UserFollowRepo
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import jakarta.annotation.Resource


@Document("user_follow")
data class UserFollowDo(
    var postId: Long,
    var targetId: Long,
    var ctTime: Long,
    var utTime: Long,
)

data class UserFollow(
    var postId: Long,
    var targetId: Long,
    var ctTime: Long,
    var utTime: Long,
)


@Service
class UserFollowRepoImpl(
    val mongoTemplate: MongoTemplate
): UserFollowRepo {



    override fun fansCnt(userId: Long): Long = mongoTemplate.count(
        Query.query(Criteria.where("target_id").`is`(userId)),
        UserFollowDo::class.java
    )

    override fun followCnt(userId: Long): Long = mongoTemplate.count(
        Query.query(Criteria.where("post_id").`is`(userId)),
        UserFollowDo::class.java
    )

    override fun exists(dto: FollowDto): Boolean {
        val obj = map(dto)
        val result = with(obj) {
            val c = Criteria
                .where("post_id").`is`(postId)
                .and("target_id").`is`(targetId)
            mongoTemplate.exists(Query.query(c), UserFollowDo::class.java)
        }
        return result
    }

    override fun save(dto: FollowDto): Int {
        val obj = map(dto)
        mongoTemplate.save(obj)
        return 1
    }

    override fun delete(dto: FollowDto): Long {
        val obj = map(dto)
        val result = with(obj) {
            val c = Criteria
                .where("post_id").`is`(postId)
                .and("target_id").`is`(targetId)
            mongoTemplate.remove(Query.query(c), UserFollowDo::class.java)
        }
        return result.deletedCount
    }

    override fun pageFollow(dto: FollowListDto): PageResp<UserFollowDo> {
        val limit = dto.limit
        val offset = (dto.start - 1) * limit

        val c = Criteria.where("post_id").`is`(dto.userId)

        val result = mongoTemplate.find(
            Query.query(c).skip(offset.toLong()).limit(limit + 1),
            UserFollowDo::class.java
        )
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

    override fun pageFans(dto: FollowListDto): PageResp<UserFollowDo> {
        val limit = dto.limit
        val offset = (dto.start - 1) * limit

        val c = Criteria.where("target_id").`is`(dto.userId)

        val result = mongoTemplate.find(
            Query.query(c).skip(offset.toLong()).limit(limit + 1),
            UserFollowDo::class.java
        )
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

    private fun map(dto: FollowDto): UserFollowDo = with(dto) {
        UserFollowDo(
            postId = postId!!.toLong(),
            targetId =  targetId.toLong(),
            ctTime = DateUtils.now(),
            utTime = DateUtils.now(),
        )
    }

}
