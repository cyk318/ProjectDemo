package org.cyk.user.domain

import org.cyk.UserinfoProto
import org.cyk.user.repo.UserinfoRepo
import org.springframework.stereotype.Component
import java.lang.RuntimeException

@Component
class LoginHandler(
    private val userinfoRepo: UserinfoRepo,
) {

    fun handler(request: UserinfoProto.LoginReq): UserinfoProto.LoginResp {
        val userinfo = userinfoRepo.queryByUsername(request.username)
            ?: throw RuntimeException("用户不存在！username: ${request.username}")


    }

}

data class Userinfo (
    val id: Long,
    val username: String,
    val password: String,
    val avatarPath: String,
)
