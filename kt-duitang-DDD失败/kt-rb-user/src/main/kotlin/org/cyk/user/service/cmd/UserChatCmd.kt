package org.cyk.user.service.cmd

import org.cyk.user.facade.ChatUserVo

interface UserChatCmd {

    fun queryChatList(userId: Long): List<ChatUserVo>

}