package org.cyk.user.domain.service

import org.cyk.UserinfoProto
import org.cyk.user.repo.UserinfoRepo
import org.springframework.stereotype.Component
import java.lang.RuntimeException

@Component
class RegHandler(
    private val userinfoRepo: UserinfoRepo,
) {

    fun handler(request: UserinfoProto.RegReq): UserinfoProto.RegResp {
        //1.参数校验
        val userinfo = userinfoRepo.queryByUsername(request.username)
        if (userinfo != null) throw RuntimeException("该用户已存在！${userinfo.username}")

        //2.新增用户
        userinfoRepo.save(request)

        return UserinfoProto.RegResp.newBuilder()
            .setResult(1)
            .build()
    }

}