package org.cyk.user.service.impl

import org.cyk.base.exception.AppException
import org.cyk.user.facade.LoginDto
import org.cyk.user.facade.RegDto
import org.cyk.user.facade.UpdateUserInfoDto
import org.cyk.base.handler.ApiStatus
import org.cyk.user.repo.mysql.UserInfoRepo
import org.cyk.user.service.UserinfoService
import org.cyk.user.service.cmd.UserInfoCmd
import org.cyk.user.service.cmd.impl.UserInfoVo
import org.cyk.user.service.manager.UserRedisManager
import org.cyk.base.utils.PasswordUtils
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service

@Service
class UserInfoServiceImpl(
    val redisManager: UserRedisManager,
    val infoRepo: UserInfoRepo,
    val infoCmd: UserInfoCmd,
    val redisTemplate: StringRedisTemplate,
): UserinfoService {

    override fun login(dto: LoginDto): String {
        val info = infoRepo.queryByName(dto.username)
            ?: throw AppException(ApiStatus.USERNAME_OR_PASSWORD_ERROR, "用户名不存在！username: ${dto.username}")

        require(PasswordUtils.isOk(dto.password, info.password)) { "登陆时，发现用户密码错误！password: ${dto.password}" }
        return redisManager.saveToken(info)
    }

    override fun reg(dto: RegDto): Int {
        require(infoRepo.queryByName(dto.username) == null) { "注册时，发现用户名存在！username: ${dto.username}" }
        val o = dto.apply { this.password = PasswordUtils.encrypt(dto.password) }
        return infoCmd.saveUserInfo(dto)
    }

    override fun update(dto: UpdateUserInfoDto): Int {
        requireNotNull(infoRepo.queryById(dto.id!!)) { "通过 id 修改用户信息时，发现 id 不存在！userId: ${dto.id}" }
        return infoCmd.updateUserInfo(dto)
    }

    override fun logout(tokenKey: String): Int {
        val result = redisTemplate.delete(tokenKey)
        return if(result) 1 else 0
    }

    override fun queryUserInfo(id: Long, curUserId: Long): UserInfoVo? {
        return infoCmd.queryUserInfo(id, curUserId)
    }

}