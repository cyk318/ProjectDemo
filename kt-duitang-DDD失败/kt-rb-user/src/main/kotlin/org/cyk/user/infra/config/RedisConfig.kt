package org.cyk.user.infra.config

import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig: BeanPostProcessor {

    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        if(RedisTemplate::class.java.isAssignableFrom(bean.javaClass)) {
            val redisTemplate = bean as RedisTemplate<*, *>
            val stringRedisSerializer = StringRedisSerializer()
            redisTemplate.keySerializer = stringRedisSerializer
            redisTemplate.valueSerializer = stringRedisSerializer
        }
        return bean
    }

}