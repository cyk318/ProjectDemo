package org.cyk.album.facade

import com.fasterxml.jackson.annotation.JsonProperty
import org.cyk.base.exception.AppException2
import org.cyk.base.handler.ApiResp
import org.cyk.base.handler.ApiStatus
import org.cyk.base.handler.PageResp
import org.cyk.album.infra.type.AlbumListType
import org.cyk.album.infra.type.AlbumPubType
import org.cyk.album.infra.type.AlbumState
import org.cyk.album.service.AlbumInfoService
import org.cyk.base.exception.AppException
import org.cyk.base.utils.ImageUtils
import org.cyk.base.utils.UserIdUtils
import org.hibernate.validator.constraints.Length
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream
import jakarta.annotation.Resource
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min

@Validated
@RestController
@RequestMapping("/album")
class AlbumInfoApi(
    val infoService: AlbumInfoService
) {

    @Value("\${save-path.photo}")
    private lateinit var sysAvatarPath: String

    @PostMapping("/pub")
    fun pub(
        request: HttpServletRequest,
        @RequestParam(required = false) @Length(max = 64) title: String?,
        @RequestParam(required = false) @Length(max = 1024) content: String?,
        @RequestParam type: Int,
        @RequestParam photos: List<MultipartFile>,
        @RequestParam(required = false, name = "pub_time") pubTime: String?,
    ): ApiResp<Int> {

        if (AlbumPubType.TIME.code == type && pubTime == null) throw AppException(ApiStatus.INVALID_PARAM,  "类型为定时发布，但是未设置定时时间" )
        require(photos.isNotEmpty()) { "专辑照片不能为空" }

        val d = AlbumPubDto(
            userId = UserIdUtils.getId(request),
            title = title ?: "",
            content = content ?: "",
            type = type,
            photosDto = AlbumPhotoDto.ofList(photos, sysAvatarPath),
            pubTime = pubTime?.toLong()
        )
        val result = infoService.pub(d)
        return ApiResp.ok(result)
    }

    @PostMapping("/update")
    fun update(
        request: HttpServletRequest,
        @RequestParam("album_id") albumId: String,
        @RequestParam(required = false) title: String,
        @RequestParam(required = false) content: String,
        @RequestParam(required = false) photos: List<MultipartFile>?
    ): ApiResp<Int> {
        val d = AlbumUpdateDto(
            userId = UserIdUtils.getId(request),
            albumId = albumId.toLong(),
            title = title,
            content = content,
            photosDto = photos?.let { AlbumPhotoDto.ofList(it, sysAvatarPath, albumId.toLong()) }
        )
        val result = infoService.update(d)
        return ApiResp.ok(result)
    }

    @GetMapping("/del")
    fun del(
        request: HttpServletRequest,
        @RequestParam id: String
    ): ApiResp<Int> {
        val d = AlbumDelDto(
            userId = UserIdUtils.getId(request),
            albumId = id.toLong()
        )
        val result = infoService.del(d)
        return ApiResp.ok(result)
    }

    @GetMapping("/list")
    fun list(
        request: HttpServletRequest,
        @RequestParam(required = false, defaultValue = "1") @Min(1) start: Int,
        @RequestParam(required = false, defaultValue = "25") @Max(25) limit: Int,
        @RequestParam(required = false, value = "user_id") @Length(min = 1) userId: String?,
        @RequestParam type: Int
    ): ApiResp<PageResp<AlbumVo>> {
        //1.查看私密列表，只能是当前用户
        val finalUserId = paramProcessor(request, type, userId)
        val d = AlbumListDto(
            start = start,
            limit = limit,
            userId = finalUserId,
            type = type
        )
        val curUserId = UserIdUtils.getId(request)
        val result = infoService.pageAlbumVo(d, curUserId)
        return ApiResp.ok(result)
    }

    @GetMapping
    fun info(
        request: HttpServletRequest,
        @RequestParam id: Long
    ): ApiResp<AlbumVo> {
        val curUserId = UserIdUtils.getId(request)
        val result = infoService.queryAlbumVo(id, curUserId)
        return ApiResp.ok(result)
    }

    @PostMapping("/like")
    fun like(
        request: HttpServletRequest,
        @RequestBody dto: LikeDto
    ): ApiResp<Long> {
        dto.postId = UserIdUtils.getId(request)
        val result = infoService.like(dto)
        return ApiResp.ok(result)
    }

    @PostMapping("/collect")
    fun collect(
        request: HttpServletRequest,
        @RequestBody dto: CollectDto
    ): ApiResp<Long> {
        dto.postId = UserIdUtils.getId(request)
        val result = infoService.collect(dto)
        return ApiResp.ok(result)
    }


    private fun paramProcessor(
        request: HttpServletRequest,
        type: Int,
        userId: String?,
    ): Long? {
        var finalUserId = userId?.toLong()
        finalUserId?.let {
            //传了 userId，就不能查询所有列表
            if(AlbumListType.ALL_LIST.code == type) {
                throw AppException2(ApiResp.no(ApiStatus.INVALID_REQUEST))
            }
            return finalUserId
        }
        //如果查询是私人列表，一定是当前用户
        if(AlbumListType.PRIVATE_LIST.code == type) {
            finalUserId = UserIdUtils.getId(request)
        }
        //查询收藏，点赞，个人 列表，如果没传 userId，表示查询当前登录用户的
        if((AlbumListType.OWN_LIST.code == type
                    || AlbumListType.COLLECT_LIST.code == type
                    || AlbumListType.LIKE_LIST.code == type)) {
            finalUserId = UserIdUtils.getId(request)
        }
        return finalUserId
    }

}

data class LikeDto(
    var postId: Long = -1,
    @field:JsonProperty("target_id")
    var targetId: Long
)

data class CollectDto(
    var postId: Long = -1,
    @field:JsonProperty("target_id")
    var targetId: Long
)

data class AlbumVo(
    var id: Long,
    var userinfo: UserInfoSimp,
    var title: String,
    var content: String,
    var photos: List<AlbumPhotoSimp>,
    var stat: AlbumStatVo,
    var ctTime: String,
    var utTime: String
)

data class UserInfoSimp (
    var userId: Long,
    var username: String,
    var avatar: String,
)

data class AlbumPhotoSimp (
    var photo: String,
    var sort: Int
)

data class AlbumStatVo (
    var pageView: Long,
    var likeCnt: Long,
    var collectCnt: Long,
    var commentCnt: Long,
    var isLike: Boolean,
    var isCollect: Boolean,
)

data class AlbumListDto(
    var start: Int,
    var limit: Int,
    var userId: Long?,
    var type: Int,
)

data class AlbumDelDto(
    var userId: Long,
    var albumId: Long
)

data class AlbumUpdateDto(
    var userId: Long,
    var albumId: Long,
    var title: String,
    var content: String,
    var photosDto: List<AlbumPhotoDto>?,
)

data class AlbumPubDto(
    var albumId: Long? = null,
    var userId: Long,
    var title: String,
    var content: String,
    var type: Int,
    var state: Int = AlbumState.NORMAL.code,
    var photosDto: List<AlbumPhotoDto>,
    var pubTime: Long?
)

data class AlbumPhotoDto(
    var albumId: Long? = null,
    var photo: InputStream, //多线程异步保存: 当主线程删除后会自动删除临时文件 multipartFile，因此此处使用流来保存
    var photoPath: String,
    var sort: Int
) {
    companion object {

        fun ofList(
            photos: List<MultipartFile>,
            sysAvatarPath: String
        ): List<AlbumPhotoDto> = photos.map {
            //校验文件名，并获取前后缀
            val (px,sx) = ImageUtils.getImageFileAndChk(it)
            //生成保存绝对路径
            val savePathAbs = ImageUtils.makePhotoSavePath(sysAvatarPath, sx)
            AlbumPhotoDto(
                photo = it.inputStream,
                sort = px.toInt(),
                photoPath = savePathAbs
            )
        }.toList()


        fun ofList(
            photos: List<MultipartFile>,
            sysAvatarPath: String,
            albumId: Long
        ): List<AlbumPhotoDto> = photos.map {
            //校验文件名，并获取前后缀
            val (px,sx) = ImageUtils.getImageFileAndChk(it)
            //生成保存绝对路径
            val savePathAbs = ImageUtils.makePhotoSavePath(sysAvatarPath, sx)
            AlbumPhotoDto(
                albumId = albumId,
                photo = it.inputStream,
                sort = px.toInt(),
                photoPath = savePathAbs
            )
        }.toList()

    }
}


