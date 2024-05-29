package org.cyk.operator.api.user

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.cyk.UserinfoProto.LoginReq
import org.cyk.UserinfoProto.RegReq
import org.cyk.UserinfoServiceGrpc.UserinfoServiceBlockingStub
import org.cyk.operator.infra.config.ApiResp
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserinfoApi(
    private val userinfoGrpcStub: UserinfoServiceBlockingStub
) {

    @PostMapping("/login")
    fun login(
        @RequestBody @Valid dto: LoginDto,
    ): ApiResp<String> {
        val b = LoginReq.newBuilder()
            .setUsername(dto.username)
            .setPassword(dto.password)
        val resp = userinfoGrpcStub.login(b.build())
        return ApiResp.ok(resp.token)
    }

    @PostMapping("/reg")
    fun reg(
        @RequestBody @Valid dto: RegDto,
    ): ApiResp<Int> {
        val b = RegReq.newBuilder()
            .setUsername(dto.username)
            .setPassword(dto.password)
        val resp = userinfoGrpcStub.reg(b.build())
        return ApiResp.ok(resp.result)
    }

}

data class LoginDto (
    @field:NotBlank
    val username: String,
    @field:NotBlank
    val password: String,
)

data class RegDto (
    @field:NotBlank
    val username: String,
    @field:NotBlank
    val password: String,
)