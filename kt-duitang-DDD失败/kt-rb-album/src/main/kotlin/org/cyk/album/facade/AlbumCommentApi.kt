package org.cyk.album.facade

import com.fasterxml.jackson.annotation.JsonProperty
import com.mongodb.internal.VisibleForTesting
import org.cyk.album.service.AlbumCommentService
import org.cyk.base.handler.ApiResp
import org.cyk.base.handler.PageResp
import org.cyk.base.utils.UserIdUtils
import org.hibernate.validator.constraints.Length
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.lang.invoke.StringConcatException
import jakarta.annotation.Resource
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank

@Validated
@RestController
@RequestMapping("/album/comment")
class AlbumCommentApi {

    @Resource private lateinit var commentService: AlbumCommentService

    @PostMapping
    fun comment(
        request: HttpServletRequest,
        @RequestBody @Valid dto: CommentDto
    ): ApiResp<Int> {
        dto.postId = UserIdUtils.getId(request)
        val result = commentService.comment(dto)
        return ApiResp.ok(result)
    }

    @PostMapping("/del")
    fun del(
        request: HttpServletRequest,
        @RequestBody @Valid dto: CommentDel,
    ): ApiResp<Long> {
        dto.postId = UserIdUtils.getId(request)
        val result = commentService.del(dto)
        return ApiResp.ok(result)
    }

    @PostMapping("/child")
    fun child(
        request: HttpServletRequest,
        @RequestBody @Valid dto: CommentChildDto,
    ): ApiResp<Int> {
        dto.postId = UserIdUtils.getId(request)
        val result = commentService.addChild(dto)
        return ApiResp.ok(result)
    }

    @PostMapping("/del/child")
    fun delChild(
        request: HttpServletRequest,
        @RequestBody @Valid dto: CommentChildDel
    ): ApiResp<Long> {
        dto.postId = UserIdUtils.getId(request)
        val result = commentService.delChild(dto)
        return ApiResp.ok(result)
    }

    @GetMapping("/child_list")
    fun childList(
        request: HttpServletRequest,
        @RequestParam(required = false, defaultValue = "1") start: Int,
        @RequestParam(required = false, defaultValue = "25") limit: Int,
        @RequestParam("target_id") @NotBlank targetId: String,
    ): ApiResp<PageResp<CommentChildVo>> {
        val curUserId = UserIdUtils.getId(request)
        val dto = PageCommentChildDto(
            start = start,
            limit = limit,
            targetId = targetId
        )
        val result = commentService.pageCommentChildVo(dto, curUserId)
        return ApiResp.ok(result)
    }

    @PostMapping("/like")
    fun like(
        request: HttpServletRequest,
        @RequestBody dto: CommentLikeDto,
    ): ApiResp<Long> {
        dto.postId = UserIdUtils.getId(request)
        val result = commentService.like(dto)
        return ApiResp.ok(result)
    }

    @PostMapping("/child/like")
    fun childLike(
        request: HttpServletRequest,
        @RequestBody dto: CommentChildLikeDto
    ): ApiResp<Long> {
        dto.postId = UserIdUtils.getId(request)
        val result = commentService.likeChild(dto)
        return ApiResp.ok(result)
    }

    @GetMapping("/list")
    fun list(
        request: HttpServletRequest,
       @RequestParam(required = false, defaultValue = "1") start: Int,
       @RequestParam(required = false, defaultValue = "25") limit: Int,
       @RequestParam("target_id") targetId: Long,
    ): ApiResp<PageResp<CommentVo>> {
        val curUserId = UserIdUtils.getId(request)
        val dto = PageCommentVoDto(
            start = start,
            limit = limit,
            targetId = targetId,
        )
        val result = commentService.pageCommentVo(dto, curUserId)
        return ApiResp.ok(result)
    }

}

data class PageCommentVoDto(
    var start: Int,
    var limit: Int,
    var targetId: Long, //专辑 id
)

data class CommentVo(
    val id: String,
    val content: String,
    val userinfo: UserInfoSimp,
    val children: List<CommentChildVo?>,
    val likeCnt: Long,
    val commentCnt: Long,
    val like: Boolean,
    val ctTime: String,
)

data class CommentChildLikeDto(
    var postId: Long = -1,
    @field:JsonProperty("target_id")
    var targetId: String = "",
)

data class CommentLikeDto(
    var postId: Long = -1,
    @field:JsonProperty("target_id")
    var targetId: String = ""
)

data class CommentChildVo(
    val id: String ,
    val parentId: String,
    val postUserInfo: UserInfoSimp,
    val targetUserInfo: UserInfoSimp,
    val content: String,
    val likeCnt: Long,
    val like: Boolean,
    val ctTime: String,
)

data class PageCommentChildDto (
    var start: Int = 1,
    var limit: Int = 24,
    var targetId: String = "", //父评论 id
)

data class CommentChildDel (
    var postId: Long = -1,
    @field:JsonProperty("target_id")
    @field:NotBlank
    var targetId: String = "",
)

data class CommentChildDto (
    var postId: Long = -1,

    @field:JsonProperty("target_id")
    var targetId: Long = -1, //给那个用户的回复

    @field:NotBlank
    @field:JsonProperty("parent_id")
    var parentId: String = "", //父评论

    @field:NotBlank
    @field:Length(max = 1000)
    var content: String = ""

)

data class CommentDel (
    var postId: Long = -1,
    @field:JsonProperty("target_id")
    @field:NotBlank
    var targetId: String = "",
)

data class CommentDto (
    var postId: Long = -1,
    @field:JsonProperty("target_id")
    var targetId: Long = -1,
    @field:NotBlank
    var content: String = "",
)