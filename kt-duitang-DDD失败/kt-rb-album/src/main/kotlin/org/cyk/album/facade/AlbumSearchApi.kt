package org.cyk.album.facade

import org.cyk.album.service.AlbumSearchService
import org.cyk.album.service.manager.AlbumRedisManager
import org.cyk.base.handler.ApiResp
import org.cyk.base.handler.PageResp
import org.cyk.base.utils.UserIdUtils
import org.hibernate.validator.constraints.Length
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import jakarta.annotation.Resource
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.constraints.NotBlank
import kotlin.math.max

@Validated
@RestController
@RequestMapping("/album/search")
class AlbumSearchApi {

    @Resource private lateinit var searchService: AlbumSearchService
    @Resource private lateinit var redisManager: AlbumRedisManager

    @GetMapping
    fun search(
        request: HttpServletRequest,
        @RequestParam(required = false, defaultValue = "0") start: Int, //es 的分页从 0 开始
        @RequestParam(required = false, defaultValue = "25") limit: Int,
        @RequestParam @Length(max = 100) text: String?
    ): ApiResp<PageResp<AlbumVo>> {
        val userId = UserIdUtils.getId(request)
        val o = AlbumSearchDto (
            start = start,
            limit = limit,
            text = if("推荐" != text) text else redisManager.getSuggestForZet(userId),
            curUserId = userId
        )
        val result = searchService.pageAlbumVo(o)
        return ApiResp.ok(result)
    }

    @GetMapping("/suggest")
    fun suggest(
        @RequestParam(required = false, defaultValue = "10") limit: Int,
        @RequestParam @Length(max = 100) text: String
    ): ApiResp<List<AlbumSuggestVo>> { val o = AlbumSuggestDto(
            limit = limit,
            text = text,
        )
        val result = searchService.suggest(o)
        return ApiResp.ok(result)
    }

}

data class AlbumSuggestDto (
    val limit: Int,
    val text: String,
)

data class AlbumSuggestVo (
    val suggests: String
)

data class AlbumSearchDto (
    val start: Int,
    val limit: Int,
    val text: String?,
    val curUserId: Long,
)