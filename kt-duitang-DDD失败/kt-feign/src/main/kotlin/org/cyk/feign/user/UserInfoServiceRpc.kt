package org.cyk.feign.user

//import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClientConfiguration
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import java.io.Serializable
import java.util.*

//@FeignClient("user", configuration = [LoadBalancerClientConfiguration::class])

@FeignClient("user")
interface UserInfoServiceRpc {

    @PostMapping("/user/infos")
    fun queryByUserIds(req: QueryByIdsReq): String

    @PostMapping("/user/info")
    fun queryByUserId(req: QueryByIdReq): String

}

data class QueryByIdReq(
    var id: Long = -1
)

data class QueryByIdResp(
    var info: UserInfoDto? = null
)

data class QueryByIdsReq(
    var ids: List<Long> = emptyList(),
)

data class QueryByIdsResp(
    var infos: List<UserInfoDto> = emptyList(),
)

data class UserInfoDto (
    var id: Long,
    var username: String,
    var brief: String,
    var phone: String?,
    var gender: Int, //性别: 0女 1男 2保密(默认)
    var birthday: String,
    var avatar: String,
    var state: Int, //状态: 0正常(默认) 1封号 2管理员
    var ctTime: Date,
    var utTime: Date,
)
