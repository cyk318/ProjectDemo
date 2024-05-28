package org.cyk.base.model

object RedisKeyConst {

    val TOKEN_PREFIX: String = "TOKEN_"

    fun generateToken(userId: Long) = TOKEN_PREFIX + userId

}
