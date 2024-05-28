package org.cyk.user.service

import org.cyk.base.handler.ApiResp
import org.cyk.base.handler.PageResp
import org.cyk.user.facade.PageUserMsgDto
import org.cyk.user.facade.UserMsgStateVo
import org.cyk.user.facade.UserMsgVo

interface UserMsgService {

    fun queryUserMsgStateVo(userId: Long): UserMsgStateVo
    fun pageUserMsgVo(dto: PageUserMsgDto): PageResp<UserMsgVo>

}