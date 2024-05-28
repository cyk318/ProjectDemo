package org.cyk.album.facade

import org.cyk.album.service.AlbumTagService
import org.cyk.base.handler.ApiResp
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import jakarta.annotation.Resource
import kotlin.collections.List

@RestController
@RequestMapping("/album/tag")
class AlbumTabApi {

    @Resource private lateinit var tagService: AlbumTagService

    @GetMapping("/list")
    fun list(): ApiResp<List<AlbumTagVo>> {
        val result = tagService.queryList()
        return ApiResp.ok(result)
    }

}

data class AlbumTagVo (
    val id: Long,
    val name: String,
)
