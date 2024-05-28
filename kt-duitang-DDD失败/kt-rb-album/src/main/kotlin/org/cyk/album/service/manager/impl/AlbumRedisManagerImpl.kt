package org.cyk.album.service.manager.impl

import org.cyk.album.infra.constants.AppConst
import org.cyk.album.infra.constants.RedisKeyConst
import org.cyk.album.service.manager.AlbumRedisManager
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import jakarta.annotation.Resource

@Service
class AlbumRedisManagerImpl: AlbumRedisManager {

    @Resource(name = "stringRedisTemplate")
    private lateinit var redisTemplate: StringRedisTemplate

    override fun addSearchSuggestForZSet(userId: Long, suggest: String, score: Double) {
        val key = RedisKeyConst.makeSearchSuggestZSetKey(userId)
        val isLimit = redisTemplate.opsForZSet().size(key) == AppConst.SEARCH_SUGGEST_LIST_SIZE
        if(isLimit) {
            redisTemplate.opsForZSet().popMin(key)
        }
        redisTemplate.opsForZSet().add(key, suggest, score)
    }

    override fun getSuggestForZet(userId: Long): String? {
        val key = RedisKeyConst.makeSearchSuggestZSetKey(userId)
        val result = redisTemplate.opsForZSet().range(key, 0, -1)
            ?.joinToString(separator = " ")
        return result
    }

}