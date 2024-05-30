package org.cyk.user.domain.service

import org.cyk.UserinfoProto
import org.cyk.UserinfoProto.LoginResp
import org.cyk.common.ApiStatus
import org.cyk.common.AppException
import org.cyk.user.infra.utils.JwtUtils
import org.cyk.user.infra.utils.PasswordUtils
import org.cyk.user.repo.UserinfoRepo
import org.springframework.stereotype.Component

@Component
class LoginHandler(
    private val userinfoRepo: UserinfoRepo,
) {

    fun handler(request: UserinfoProto.LoginReq): LoginResp {
        //1.参数校验
        val userinfo = userinfoRepo.queryByUsername(request.username)
            ?: throw AppException(ApiStatus.INVALID_PARAM, "用户名不存在！username: ${request.username}")
        if (!PasswordUtils.isOk(request.password, userinfo.password)) {
            throw AppException(ApiStatus.INVALID_PARAM, "密码错误！username: ${request.username}, password: ${request.password}")
        }
        //2.生成并保存 Token
        val token = JwtUtils.createToken(mapOf("id" to userinfo.id))
        userinfoRepo.saveToken(token, userinfo.id)

        return LoginResp.newBuilder()
            .setToken(token)
            .build()
    }

}



