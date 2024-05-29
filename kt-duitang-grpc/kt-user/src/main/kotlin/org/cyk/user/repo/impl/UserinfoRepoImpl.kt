package org.cyk.user.repo.impl

import org.cyk.UserinfoProto
import org.cyk.user.domain.Userinfo
import org.cyk.user.infra.RedisKey
import org.cyk.user.repo.UserinfoRepo
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

data class UserinfoDo (
    val id: Long? = null,
    val username: String,
    val password: String,
    val avatarPath: String = "www.cyk.com",
)

@Repository
class UserinfoRepoImpl(
    private val mongoTemplate: MongoTemplate,
    private val redisTemplate: StringRedisTemplate,
): UserinfoRepo {

    override fun save(req: UserinfoProto.RegReq) {
        val obj = map(req)
        mongoTemplate.save(obj)
    }

    override fun saveToken(token: String, id: Long) {
        redisTemplate.opsForValue().set(RedisKey.getToken(id), token, 1, TimeUnit.DAYS)
    }

    override fun queryByUsername(username: String): Userinfo? {
        val c = Criteria.where("username").`is`(username)
        return mongoTemplate
            .findOne(Query.query(c), UserinfoDo::class.java)
            ?.let(::map)
    }

    private fun map(o: UserinfoDo): Userinfo = with(o) {
        Userinfo(
            id = id!!,
            username = username,
            password = password,
            avatarPath = avatarPath,
        )
    }

    private fun map(req: UserinfoProto.RegReq) = with(req) {
        UserinfoDo (
            username = username,
            password = password,
        )
    }

}

