package org.cyk.warehouse.api

import jakarta.servlet.http.HttpServletRequest
import org.cyk.warehouse.config.ApiResp
import org.cyk.warehouse.config.AppException
import org.cyk.warehouse.config.HttpSessionKey
import org.cyk.warehouse.repo.AdminRepo
import org.cyk.warehouse.repo.impl.AdminDo
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/admin")
class AdminApi(
    private val adminRepo: AdminRepo,
) {

    @PostMapping("/login")
    fun login(
        request: HttpServletRequest,
        @RequestBody dto: LoginDto
    ): ApiResp<Int> {
        //1.判断用户是否存在
        val dbUser = adminRepo.queryByUsername(dto.username) ?: throw AppException("当前用户不存在！")
        //2.判断密码是否正确
        if (dto.password != dbUser.password) throw AppException("用户密码错误！")
        //3.登录成功，保存登录信息
        request.session.setAttribute(HttpSessionKey.USER_KEY, dbUser)
        return ApiResp.ok(1)
    }

    @PostMapping("/reg")
    fun reg(
        @RequestBody dto: RegDto,
    ): ApiResp<Int> {
        //1.判断用户是否存在
        val dbUser = adminRepo.queryByUsername(dto.username)?.let { throw AppException("当前用户名已存在！") }
        //2.创建用户
        adminRepo.save(dto)
        return ApiResp.ok(1)
    }

    @GetMapping("/list")
    fun list(): ApiResp<List<AdminDo>> {
        //从数据库中查询所有用户信息
        val result = adminRepo.queryAll()
        return ApiResp.ok(result)
    }

    @GetMapping("/del/{id}")
    fun del(
        @PathVariable id: String
    ): ApiResp<Long> {
        val result = adminRepo.del(id)
        return ApiResp.ok(result)
    }

    @PostMapping("/update")
    fun update(
        @RequestBody dto: UpdateAdminDto,
    ): ApiResp<Long> {
        adminRepo.queryById(dto.id) ?: throw AppException("用户id ${dto.id} 不存在")
        val result = adminRepo.update(dto)
        return ApiResp.ok(result)
    }

}

data class UpdateAdminDto(
    val id: String,
    val username: String,
    val password: String,
)

data class RegDto (
    val username: String,
    val password: String,
)

data class LoginDto (
    val username: String,
    val password: String,
)