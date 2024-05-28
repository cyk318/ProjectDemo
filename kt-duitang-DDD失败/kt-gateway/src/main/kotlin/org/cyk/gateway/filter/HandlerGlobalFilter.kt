package org.cyk.gateway.filter

import org.cyk.base.exception.AppException2
import org.cyk.base.handler.ApiResp
import org.cyk.base.handler.ApiStatus
import org.cyk.base.model.HeaderConst
import org.cyk.base.model.JwtConst
import org.cyk.gateway.model.PassURIConst
import org.cyk.base.model.RedisKeyConst
import org.cyk.base.utils.JwtUtils
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import jakarta.annotation.Resource

@Configuration
class HandlerGlobalFilter: GlobalFilter, Ordered {

    @Resource(name = "stringRedisTemplate")
    private lateinit var redisTemplate: StringRedisTemplate

    private val passURI = setOf(
        PassURIConst.LOGIN,
        PassURIConst.REG
    )

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        //1.直接放行
        val r = exchange.request
        if(passURI.contains(r.uri.path)) {
            return chain.filter(exchange)
        }
        //2.token 合法性
        val list = r.headers[HeaderConst.TOKEN]
        //1) 空判断
        if(list.isNullOrEmpty()) {
            throw AppException2(ApiResp.no(ApiStatus.TOKEN_EXPIRE))
        }
        //2) JWT 校验
        val token = list.first()
        if(!JwtUtils.checkToken(token)) {
            throw AppException2(ApiResp.no(ApiStatus.TOKEN_EXPIRE))
        }
        //3) redis token 过期
        val userId = JwtUtils.getTokenInfo(token).getClaim(JwtConst.USER_ID).asLong()
        val tokenExists = redisTemplate.hasKey(RedisKeyConst.generateToken(userId))
        if(!tokenExists) {
            throw AppException2(ApiResp.no(ApiStatus.TOKEN_EXPIRE))
        }
        return chain.filter(exchange)
    }

    override fun getOrder(): Int {
        return -1
    }

}