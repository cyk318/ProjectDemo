package org.cyk.user.repo.mongo.impl

import org.cyk.base.handler.PageResp
import org.cyk.base.model.type.UserMsgStateType
import org.cyk.base.utils.DateUtils
import org.cyk.msg.publisher.UserMsgMsg
import org.cyk.user.facade.PageUserMsgDto
import org.cyk.user.repo.mongo.UserMsgRepo
import org.springframework.data.annotation.Id
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service
import jakarta.annotation.Resource

@Document("user_msg_state")
data class UserMsgStateDo (
    @Id
    val id: Long, //用户id val followState: Int,
    val followState: Int,
    val likeState: Int,   //上限 100
    val commentState: Int,
    val collectState: Int,
    val ctTime: Long,
    val utTime: Long,
)

@Document("user_msg_info")
data class UserMsgInfoDo(
    @Id
    val id: String? = null,
    val userId: Long, //谁的消息
    val postId: Long,
    val targetId: String,
    val targetContent: String,
    val type: Int, //消息类型
    val ctTime: Long,
    val utTime: Long,
)

@Service
class UserMsgRepoImpl: UserMsgRepo {

    @Resource private lateinit var mongoTemplate: MongoTemplate
    private val stateLimit = 100

    override fun init(userId: Long) {
        val obj = map(userId)
        mongoTemplate.save(obj)
    }

    override fun save(msg: UserMsgMsg): Int {
        val obj = map(msg)
        mongoTemplate.save(obj)
        return 1
    }

    private fun map(dto: UserMsgMsg): UserMsgInfoDo = with(dto) {
        UserMsgInfoDo(
            userId = userId,
            postId = postId,
            targetId = targetId,
            targetContent = targetContent,
            type = type,
            ctTime = ctTime,
            utTime = utTime,
        )
    }


    override fun tryIncr(userId: Long, type: UserMsgStateType): Boolean {
        val c = Criteria()
        c.and("_id").`is`(userId)
        c.and(type.msg).gte(stateLimit)
        val exists = mongoTemplate.exists(Query.query(c), UserMsgStateDo::class.java)
        val result = if(exists) {
            false
        } else {
            mongoTemplate.updateFirst(
                Query.query(Criteria.where("_id").`is`(userId)),
                Update().inc(type.msg, 1),
                UserMsgStateDo::class.java)
            true
        }
        return result
    }

    override fun clear(userId: Long, type: UserMsgStateType): Long {
        val result = mongoTemplate.updateFirst(
            Query.query(Criteria.where("_id").`is`(userId)),
            Update().set(type.msg, 0),
            UserMsgStateDo::class.java
        )
        return result.modifiedCount
    }

    override fun queryUserMsgState(userId: Long): UserMsgState? {
        val c = Criteria.where("_id").`is`(userId)
        val result = mongoTemplate.findOne(Query.query(c), UserMsgStateDo::class.java)?.let { map(it) }
        return result
    }

    override fun pageUserMsgInfo(dto: PageUserMsgDto): PageResp<UserMsgInfo> {
        val start = (dto.start - 1L) * dto.limit
        val limit = dto.limit
        val c = Criteria()
        c.and("user_id").`is`(dto.userId)
        c.and("type").`is`(dto.type)
        val result = mongoTemplate.find(
            Query.query(c)
                .skip(start)
                .limit(limit + 1)
                .with(Sort.by(Sort.Order.desc("ct_time"))),
            UserMsgInfoDo::class.java)
            .map(::map)
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

    private fun map(o: UserMsgInfoDo): UserMsgInfo = with(o) {
        UserMsgInfo(
            id = id!!,
            userId = userId,
            postId = postId,
            targetId = targetId,
            targetContent = targetContent,
            type = type,
            ctTime = ctTime,
            utTime = utTime,
        )
    }

    private fun map(userId: Long): UserMsgStateDo = run {
        UserMsgStateDo(
            id = userId,
            followState = 0,
            likeState = 0,
            commentState = 0,
            collectState = 0,
            ctTime = DateUtils.now(),
            utTime = DateUtils.now(),
        )
    }


    private fun map(o: UserMsgStateDo): UserMsgState = with(o) {
        UserMsgState(
            id = id,
            followState = followState,
            likeState = likeState,
            commentState = commentState,
            collectState = collectState,
            ctTime = ctTime,
            utTime = utTime,
        )
    }

}

data class UserMsgState (
    val id: Long, //用户id val followState: Int,
    val followState: Int,
    val likeState: Int,
    val commentState: Int,
    val collectState: Int,
    val ctTime: Long,
    val utTime: Long,
)

data class UserMsgInfo(
    val id: String,
    val userId: Long, //谁的消息
    val postId: Long,
    val targetId: String,
    val targetContent: String,
    val type: Int, //消息类型
    val ctTime: Long,
    val utTime: Long,
)


