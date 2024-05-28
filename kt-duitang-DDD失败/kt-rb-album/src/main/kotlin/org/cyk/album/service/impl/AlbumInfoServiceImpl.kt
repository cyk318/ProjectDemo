package org.cyk.album.service.impl

import org.cyk.album.facade.*
import org.cyk.album.infra.constants.RedisKeyConst
import org.cyk.album.infra.type.AlbumListType
import org.cyk.base.exception.AppException2
import org.cyk.base.handler.ApiResp
import org.cyk.base.handler.ApiStatus
import org.cyk.album.infra.type.AlbumPubType
import org.cyk.album.infra.type.AlbumState
import org.cyk.album.repo.mongo.AlbumCollectRepo
import org.cyk.album.repo.mongo.AlbumLikeRepo
import org.cyk.album.repo.mysql.AlbumInfoRepo
import org.cyk.album.service.AlbumInfoService
import org.cyk.album.service.cmd.AlbumCmd
import org.cyk.album.service.impl.strategy.*
import org.cyk.album.service.message.AlbumInfoPublisher
import org.cyk.base.exception.AppException
import org.cyk.base.handler.PageResp
import org.cyk.base.model.type.UserMsgStateType
import org.cyk.msg.publisher.UserMsgPublisher
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service

@Service
class AlbumInfoServiceImpl(
    val redisTemplate: StringRedisTemplate,
    val infoRepo: AlbumInfoRepo,
    val cmd: AlbumCmd,
    val collectRepo: AlbumCollectRepo,
    val likeRepo: AlbumLikeRepo,
    val infoPublisher: AlbumInfoPublisher,
    val userMsgPublisher: UserMsgPublisher,
): AlbumInfoService {

    override fun pub(d: AlbumPubDto): Int {
        val pubStrategy: AlbumPubStrategy = when(AlbumPubType.getType(d.type)) {
            AlbumPubType.NORMAL -> NormalAlbumPubStrategy(cmd)
            AlbumPubType.PRIVATE -> PrivateAlbumPubStrategy(cmd)
            AlbumPubType.DRAFT -> DraftAlbumPubStrategy(cmd)
            AlbumPubType.TIME -> return TimeAlbumPubStrategy(cmd, infoPublisher).publish(d)
            else -> throw AppException(ApiStatus.INVALID_PARAM, "专辑发布时，发现非法 type: ${d.type}")
        }

        val result = pubStrategy.publish(d)
        infoPublisher.addAlbumInfoEvent(d)

        return result
    }

    override fun update(d: AlbumUpdateDto): Int {
        isParamOk(d)
        val result = cmd.updateAlbum(d)
        infoPublisher.updateAlbumInfoEvent(d)
        return result
    }

    override fun del(d: AlbumDelDto): Int {
        isParamOk(d)
        val result = cmd.deleteAlbum(d)
        infoPublisher.delAlbumInfoEvent(d.albumId)
        return result
    }

    override fun pageAlbumVo(d: AlbumListDto, curUserId: Long): PageResp<AlbumVo> {
        val q = QueryAlbumVoDto(
            start = d.start,
            limit = d.limit,
            userId = d.userId
        ).apply {
            when(AlbumListType.getType(d.type)) {
                AlbumListType.ALL_LIST -> {
                    state = AlbumState.NORMAL.code
                }
                AlbumListType.OWN_LIST -> {
                    state = AlbumState.NORMAL.code
                }
                AlbumListType.COLLECT_LIST -> {
                    state = AlbumState.NORMAL.code
                    val ids = collectRepo.queryByUserId(d.userId!!).map { it?.targetId }
                    if(ids.isEmpty()) {
                        return PageResp.empty(AlbumVo::class.java)
                    }
                    albumIds = ids.map { it!! }
                }
                AlbumListType.LIKE_LIST -> {
                    state = AlbumState.NORMAL.code
                    val ids = likeRepo.queryByUserId(d.userId!!).map { it?.targetId }
                    if(ids.isEmpty()) {
                        return PageResp.empty(AlbumVo::class.java)
                    }
                    albumIds = ids.map { it!! }
                }
                AlbumListType.PRIVATE_LIST -> {
                    state = AlbumState.PRIVATE.code
                }
                else -> {
                    log.info("专辑查询时，发现非法 type: ${d.type}")
                    throw AppException2(ApiResp.no(ApiStatus.INVALID_REQUEST))
                }
            }
        }
        return cmd.pageAlbumVo(q, curUserId)
    }

    override fun queryAlbumVo(albumId: Long, curUserId: Long): AlbumVo {
        val info = infoRepo.queryById(albumId)
        if(info == null) {
            log.error("查询文章详细信息时，发现非法 id: $albumId")
            throw AppException2(ApiResp.no(ApiStatus.INVALID_REQUEST))
        }
        val result = cmd.queryAlbumVo(info, curUserId)
        infoPublisher.queryAlbumVoEvent(albumId, info.title, curUserId)
        return result
    }

    override fun like(dto: LikeDto): Long {
        isParamOk(dto)
        val (key, value) = RedisKeyConst.makeAlbumLikeKey(dto.targetId) to dto.postId.toString()
        val exists = redisTemplate.opsForSet().isMember(key, value) ?: false
        val result = if (exists) { //1.redis 上存在: 删 redis，异步更新库
            -1L * (redisTemplate.opsForSet().remove(key, value) ?: 0)
        } else { //2.redis 上没有:
            val exist = likeRepo.queryOne(dto.targetId, dto.postId) != null
            if(exist) { //a) 数据库存在: 删库
                -likeRepo.delete(dto.postId, dto.targetId)
            } else { //b) 数据库不存在: redis 上添加，异步更新库
                +redisTemplate.opsForSet().add(key, value)!!
            }
        }

        infoPublisher.addAlbumLikeEvent(dto, result)
        if(result > 0) {
            val aInfo = infoRepo.queryById(dto.targetId)!!
            userMsgPublisher.sendUserMsg(aInfo.userId, dto.postId, aInfo.id.toString(), aInfo.title, UserMsgStateType.LIKE_STATE.code)
        }
        return result
    }


    override fun collect(dto: CollectDto): Long {
        isParamOk(dto)
        val exists = collectRepo.exists(dto.postId, dto.targetId)
        val result = if(exists) {
            -collectRepo.delete(dto.postId, dto.targetId)
        } else {
            +collectRepo.save(dto.postId, dto.targetId)
        }

        infoPublisher.addAlbumCollectEvent(dto, result)
        if(result > 0) {
            val aInfo = infoRepo.queryById(dto.targetId)!!
            userMsgPublisher.sendUserMsg(aInfo.userId, dto.postId, aInfo.id.toString(), aInfo.title, UserMsgStateType.COLLECT_STATE.code)
        }
        return result
    }

    private val log = LoggerFactory.getLogger(AlbumInfoServiceImpl::class.java)

    private fun isParamOk(dto: CollectDto) = dto.run {
        val exists = infoRepo.queryById(targetId) != null
        if(!exists) {
            log.error("收藏的专辑不存在！albumId: $")
            throw AppException2(ApiResp.no(ApiStatus.INVALID_REQUEST))
        }
    }

    private fun isParamOk(dto: LikeDto) = dto.run {
        val exists = infoRepo.queryById(targetId) != null
        if(!exists) {
            log.error("关注的专辑不存在！albumId: $targetId")
            throw AppException2(ApiResp.no(ApiStatus.INVALID_REQUEST))
        }
    }

    private fun isParamOk(d: AlbumDelDto) = d.run {
        val info = infoRepo.queryById(albumId)
        if(info == null) {
            log.error("要删除的文章不存在！albumId: $albumId")
            throw AppException2(ApiResp.no(ApiStatus.INVALID_REQUEST))
        }
        if(info.userId != userId) {
            log.error("无权删除他人的文章！userId: $userId, albumId: ${info.id}")
            throw AppException2(ApiResp.no(ApiStatus.INVALID_REQUEST))
        }
    }

    private fun isParamOk(d: AlbumUpdateDto) = with(d) {
        val o = infoRepo.queryById(d.albumId)
        if(o == null) {
            log.error("修改文章时，发现专辑 id 不存在！albumId: $albumId")
            throw AppException2(ApiResp.no(ApiStatus.INVALID_REQUEST))
        }
        if(o.userId != userId) {
            log.error("只能修改自己的专辑！userId: $userId")
            throw AppException2(ApiResp.no(ApiStatus.INVALID_REQUEST))
        }
    }

}

data class QueryAlbumVoDto(
    var start: Int,
    var limit: Int,
    var userId: Long? = null,
    var albumIds: List<Long>? = null,
    var state: Int = -1,
)