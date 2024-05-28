package org.cyk.user.infra.constants


object RedisKeyConst {

    private const val CHAT_FRIEND_SET = "CHAT_FRIEND_SET_"

    fun makeChatFriendSetKey(userId: Long) = CHAT_FRIEND_SET + userId

}