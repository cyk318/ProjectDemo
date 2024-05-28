package org.cyk.user.service

import org.cyk.user.facade.LoginDto
import org.cyk.user.facade.RegDto
import org.cyk.user.facade.UpdateUserInfoDto
import org.cyk.user.service.cmd.impl.UserInfoVo

interface UserinfoService {

    fun login(dto: LoginDto): String

    fun reg(dto: RegDto): Int

    fun update(dto: UpdateUserInfoDto): Int

    fun logout(tokenKey: String): Int

    fun queryUserInfo(id: Long, curUserId: Long): UserInfoVo?

//
//    fun querySimpUserInfoRpc(req: SimpUserInfoReq?): SimpUserInfoResp
//
//    fun querySimpUserInfoListRpc(req: SimpUserInfoListReq?): SimpUserInfoListResp

}