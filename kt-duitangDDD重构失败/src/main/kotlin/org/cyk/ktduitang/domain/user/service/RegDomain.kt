package org.cyk.ktduitang.domain.user.service

import org.cyk.ktduitang.domain.user.design.cmd.SaveUserinfoHandler
import org.cyk.ktduitang.domain.user.repo.UserIdentRepo
import org.cyk.ktduitang.facade.model.RegDto
import org.cyk.ktduitang.infra.config.ApiStatus
import org.cyk.ktduitang.infra.config.AppException
import org.cyk.ktduitang.infra.tools.PasswordUtils
import org.springframework.stereotype.Component

@Component
class RegDomain(
    private val userIdentRepo: UserIdentRepo,
    private val saveUserinfoHandler: SaveUserinfoHandler,
) {

    fun chValid(dto: RegDto) {
        //1.用户名不能重复
        userIdentRepo.queryByUsername(dto.username)?.let {
            throw AppException(ApiStatus.INVALID_PARAM, "用户名不能重复！")
        }
    }

    fun createNewUserAndEncrypt(dto: RegDto) {
        //1.密码加密
        dto.password = PasswordUtils.encrypt(dto.password)
        //2.保存用户信息
        saveUserinfoHandler.handler(dto)
    }

}