package org.cyk.user.service.cmd

import org.cyk.user.facade.RegDto
import org.cyk.user.facade.UpdateUserInfoDto
import org.cyk.user.service.cmd.impl.UserInfoVo

interface UserInfoCmd {

    fun saveUserInfo(dto: RegDto): Int
    fun updateUserInfo(dto: UpdateUserInfoDto): Int
    fun queryUserInfo(id: Long, curUserId: Long): UserInfoVo?

}