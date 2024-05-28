package org.cyk.user.facade

import org.cyk.base.handler.ApiResp
import org.cyk.base.model.RedisKeyConst
import org.cyk.user.service.UserinfoService
import org.cyk.user.service.cmd.impl.UserInfoVo
import org.cyk.base.utils.UserIdUtils
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.*
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank

@Validated
@RestController
@RequestMapping("/user")
class UserInfoApi(
    val infoService: UserinfoService
) {

    @PostMapping("/login")
    fun login(
        @RequestBody @Valid loginDto: LoginDto
    ): ApiResp<String> {
        val result = infoService.login(loginDto)
        return ApiResp.ok(result)
    }

    @PostMapping("/reg")
    fun reg(
        @RequestBody @Valid regDto: RegDto
    ): ApiResp<Int> {
        val result = infoService.reg(regDto)
        return ApiResp.ok(result)
    }

    @PostMapping("/update")
    fun update(
        httpServletRequest: HttpServletRequest,
        @ModelAttribute updateUserInfoDto: UpdateUserInfoDto
    ): ApiResp<Int> {
        val o = updateUserInfoDto.apply { id = UserIdUtils.getId(httpServletRequest) }
        val result = infoService.update(o)
        return ApiResp.ok(result)
    }

    @GetMapping("/logout")
    fun logout(httpServletRequest: HttpServletRequest): ApiResp<Int> {
        val tokenKey = run {
            val userId = UserIdUtils.getId(httpServletRequest)
            RedisKeyConst.generateToken(userId)
        }
        val result = infoService.logout(tokenKey)
        return ApiResp.ok(result)
    }

    @GetMapping
    fun userinfo(
        httpServletRequest: HttpServletRequest,
        @RequestParam(required = false) id: String?
    ): ApiResp<UserInfoVo?> {
        val userId = if(id.isNullOrBlank()) UserIdUtils.getId(httpServletRequest) else id.toLong()
        val curUserId = UserIdUtils.getId(httpServletRequest)
        val result = infoService.queryUserInfo(userId, curUserId)
        return ApiResp.ok(result)
    }

}

data class LoginDto(
    var id: Long = -1L,
    @field:NotBlank
    var username: String = "",
    @field:NotBlank
    var password: String = "",
)

data class RegDto(
    var userId: Long = -1,
    @field:NotBlank
    var username: String = "",
    @field:NotBlank
    var password: String = "",
)

data class UpdateUserInfoDto(
    var id: Long? = null,
    var username: String? = null,
    var password: String? = null,
    var brief: String? = null,
    var phone: String? = null,
    var gender: Int? = null, //性别: 0女 1男 2保密(默认)
    var birthday: String? = null,
    var avatar: MultipartFile? = null,
    var avatarPath: String? = null,
    var utTime: Date? = Date(),
)


