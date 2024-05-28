package org.cyk.user.service.cmd

import org.cyk.base.handler.PageResp
import org.cyk.user.facade.PageUserMsgDto
import org.cyk.user.facade.UserMsgVo

interface UserMsgCmd {

    fun pageUserMsgVod(dto: PageUserMsgDto): PageResp<UserMsgVo>

}