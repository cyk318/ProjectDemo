package org.cyk.user.service.impl

import org.cyk.user.facade.AddFriendDto
import org.cyk.user.facade.ChatUserVo
import org.cyk.user.service.UserChatService
import org.cyk.user.service.cmd.UserChatCmd
import org.cyk.user.service.manager.UserRedisManager
import org.springframework.stereotype.Service

@Service
class UserChatServiceImpl(
    val redisManager: UserRedisManager,
    val cmd: UserChatCmd,
): UserChatService {

    override fun addFriend(dto: AddFriendDto): Long {
        val result = redisManager.saveFriendIfNotExists(dto)
        return result
    }

    override fun chatList(userId: Long): List<ChatUserVo> {
        val result = cmd.queryChatList(userId)
        return result
    }

}