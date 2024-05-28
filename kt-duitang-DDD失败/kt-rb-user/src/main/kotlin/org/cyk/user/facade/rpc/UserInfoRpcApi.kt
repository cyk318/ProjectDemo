package org.cyk.user.facade.rpc

import com.fasterxml.jackson.databind.ObjectMapper
import org.cyk.feign.user.*
import org.cyk.user.repo.mysql.UserInfoRepo
import org.cyk.user.repo.mysql.mapper.UserInfo
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import jakarta.annotation.Resource


@RestController
@RequestMapping("/user")
class UserInfoRpcApi(
    val infoRepo :UserInfoRepo,
    val mapper: ObjectMapper,
) {

    @PostMapping("/infos")
    fun queryByUserIds(@RequestBody req: QueryByIdsReq): String {
        val result = infoRepo.queryByIds(req.ids).map(::map)
        val p = QueryByIdsResp(infos = result)
        return mapper.writeValueAsString(p)
    }

    @PostMapping("/info")
    fun queryByUserId(@RequestBody req: QueryByIdReq): String {
        val result = infoRepo.queryById(req.id)?.let { map(it) }
        val p = QueryByIdResp(info = result)
        return mapper.writeValueAsString(p)
    }

    private fun map(o: UserInfo): UserInfoDto = with(o) {
        UserInfoDto(
            id = id,
            username = username,
            brief = brief,
            phone = phone,
            gender = gender,
            birthday = birthday,
            avatar = avatar,
            state = state,
            ctTime = ctTime,
            utTime = utTime,
        )
    }

}