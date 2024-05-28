package org.cyk.user.facade
import com.fasterxml.jackson.annotation.JsonProperty
import org.cyk.base.handler.ApiResp
import org.cyk.base.handler.PageResp
import org.cyk.user.service.UserFollowService
import org.cyk.base.utils.UserIdUtils
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.io.Serializable
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min


@Validated
@RestController
@RequestMapping("/user/follow")
class UserFollowApi(
    val followService: UserFollowService
) {

    @PostMapping
    fun follow(
        httpServletRequest: HttpServletRequest,
        @RequestBody dto: FollowDto
    ): ApiResp<Long> {
        dto.postId = UserIdUtils.getId(httpServletRequest)
        val result = followService.follow(dto)
        return ApiResp.ok(result)
    }

    @GetMapping("/list")
    fun list(
        @RequestParam(required = false, defaultValue = "1") @Min(1) start: Int,
        @RequestParam(required = false, defaultValue = "25") @Min(1) @Max(25) limit: Int,
        @RequestParam("user_id") userId: String,
        @RequestParam type: Int
    ): ApiResp<PageResp<FollowVo?>> {
        val dto = FollowListDto(
            start = start,
            limit = limit,
            userId = userId.toLong(),
            type = type
        )
        val result = followService.pageFollowVo(dto)
        return ApiResp.ok(result)
    }

}

data class FollowDto(
    var postId: Long = -1,
    @field:JsonProperty("target_id")
    var targetId: Long = -1,
): Serializable

data class FollowListDto(
    val start: Int = 1,
    val limit: Int = 25,
    val userId: Long = -1,
    val type: Int = -1
): Serializable

data class FollowVo(
    val userId: Long? = null,
    val username: String? = null,
    val avatar: String? = null,
    val youFollowOther: Boolean = false,
    val otherFollowYou: Boolean = false,
)