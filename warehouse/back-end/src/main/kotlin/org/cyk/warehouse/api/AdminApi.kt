package org.cyk.warehouse.api

import org.cyk.warehouse.config.ApiResp
import org.cyk.warehouse.repo.AdminRepo
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
        @RequestBody dto: LoginDto
    ): ApiResp<String> {
        val result = adminRepo.login(dto)
        return ApiResp.ok(result)
    }

}

data class LoginDto (
    val username: String,
    val password: String,
)