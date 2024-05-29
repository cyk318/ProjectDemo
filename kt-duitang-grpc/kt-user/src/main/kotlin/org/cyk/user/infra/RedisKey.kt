package org.cyk.user.infra

object RedisKey {

    const val TOKEN = "TOKEN_"

    fun getToken(id: String) = "$TOKEN$id"

}