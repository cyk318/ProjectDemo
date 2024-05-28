package org.cyk.user.service.manager.impl

import io.netty.util.Timeout
import org.cyk.base.model.JwtConst
import org.cyk.base.model.RedisKeyConst
import org.cyk.user.service.manager.UserRedisManager
import org.cyk.base.utils.JwtUtils
import org.cyk.user.facade.AddFriendDto
import org.cyk.user.repo.mysql.mapper.UserInfo
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit
import jakarta.annotation.Resource

@Service
class UserRedisManagerImpl(
    val redisTemplate: StringRedisTemplate
): UserRedisManager {


    override fun saveToken(obj: UserInfo): String {
        val tokenKey = RedisKeyConst.generateToken(obj.id)
        if(redisTemplate.hasKey(tokenKey)) {
            return redisTemplate.opsForValue().get(tokenKey) as String
        }

        val tokenPayload = mapOf(
            JwtConst.USER_ID to obj.id,
        )
        val token = JwtUtils.createToken(tokenPayload)
        redisTemplate.opsForValue().set(tokenKey, token, 1, TimeUnit.DAYS)

        return token
    }

    override fun saveFriendIfNotExists(dto: AddFriendDto): Long {
        val key = org.cyk.user.infra.constants.RedisKeyConst.makeChatFriendSetKey(dto.postId)
        val value = dto.targetId.toString()
        val result = redisTemplate.opsForSet().add(key, value)
        redisTemplate.expire(key, 3, TimeUnit.DAYS)
        return result ?: 0
    }

}