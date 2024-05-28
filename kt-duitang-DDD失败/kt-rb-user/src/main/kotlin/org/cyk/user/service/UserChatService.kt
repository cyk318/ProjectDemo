package org.cyk.user.service

import org.cyk.user.facade.AddFriendDto
import org.cyk.user.facade.ChatUserVo

interface UserChatService {

    fun addFriend(dto: AddFriendDto): Long
    fun chatList(userId: Long): List<ChatUserVo>

}