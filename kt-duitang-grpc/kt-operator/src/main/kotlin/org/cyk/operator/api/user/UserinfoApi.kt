package org.cyk.operator.api.user

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.cyk.UserinfoProto.LoginReq
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
        val req = LoginReq.newBuilder()
            .setUsername(dto.username)
            .setPassword(dto.password)
            .build()
        val resp = userinfoGrpcStub.login(req)
        return ApiResp.ok(resp.token)
    }

}

data class LoginDto (
    @field:NotBlank
    val username: String,
    @field:NotBlank
    val password: String,
)