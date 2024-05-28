package org.cyk.user.facade

import org.cyk.base.handler.ApiResp
import org.cyk.base.utils.UserIdUtils
import org.cyk.user.service.UserChatService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import jakarta.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/user/chat")
class UserChatApi(
    val userChatService: UserChatService
) {

    @PostMapping("/add_friend")
    fun addFriend(
        request: HttpServletRequest,
        @RequestParam("target_id") targetId: Long
    ): ApiResp<Long> {
        val dto = AddFriendDto(
            postId = UserIdUtils.getId(request),
            targetId = targetId
        )
        val result = userChatService.addFriend(dto)
        return ApiResp.ok(result)
    }

    @GetMapping("/list")
    fun list(
        request: HttpServletRequest,
    ): ApiResp<List<ChatUserVo>> {
        val userId = UserIdUtils.getId(request)
        val result = userChatService.chatList(userId)
        return ApiResp.ok(result)
    }

}

data class ChatUserVo (
    val userId: Long,
    val username: String,
    val avatar: String,
    val type: Int, //用户类型
)

data class AddFriendDto (
    val postId: Long,
    val targetId: Long,
)