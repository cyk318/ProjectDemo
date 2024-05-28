package org.cyk.user.facade

import org.cyk.base.handler.ApiResp
import org.cyk.base.handler.PageResp
import org.cyk.base.utils.UserIdUtils
import org.cyk.user.service.UserMsgService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import jakarta.annotation.Resource
import jakarta.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/user/msg")
class UserMsgApi(
    val userMsgService: UserMsgService
) {

    @GetMapping("/state")
    fun state(
        request: HttpServletRequest
    ): ApiResp<UserMsgStateVo> {
        val userId = UserIdUtils.getId(request)
        val result = userMsgService.queryUserMsgStateVo(userId)
        return ApiResp.ok(result)
    }

    @GetMapping("/info_list")
    fun infoList(
        request: HttpServletRequest,
        @RequestParam(required = false, defaultValue = "1") start: Int,
        @RequestParam(required = false, defaultValue = "25") limit: Int,
        @RequestParam type: Int
    ): ApiResp<PageResp<UserMsgVo>> {
        val dto = PageUserMsgDto (
            start = start,
            limit = limit,
            userId = UserIdUtils.getId(request),
            type = type,
        )
        val result = userMsgService.pageUserMsgVo(dto)
        return ApiResp.ok(result)
    }

}

data class PageUserMsgDto(
    val start: Int = 1,
    val limit: Int = 25,
    val userId: Long = -1,
    val type: Int = 0,
)

data class UserMsgVo (
    val postId: Long,
    val postUsername: String,
    val postAvatar: String,
    val targetId: String,
    val targetContent: String,
    val ctTime: String,
)

data class UserMsgStateVo(
    val followState: Int,
    val likeState: Int,
    val commentState: Int,
    val collectState: Int,
)