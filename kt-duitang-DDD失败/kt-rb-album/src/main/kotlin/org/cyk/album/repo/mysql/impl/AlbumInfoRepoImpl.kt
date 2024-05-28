package org.cyk.album.repo.mysql.impl

import com.baomidou.mybatisplus.core.metadata.OrderItem
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.cyk.album.facade.AlbumPubDto
import org.cyk.album.facade.AlbumUpdateDto
import org.cyk.album.repo.mysql.AlbumInfoRepo
import org.cyk.album.repo.mysql.mapper.AlbumInfo
import org.cyk.album.repo.mysql.mapper.AlbumInfoDo
import org.cyk.album.repo.mysql.mapper.AlbumInfoMapper
import org.cyk.album.service.impl.QueryAlbumVoDto
import org.cyk.base.handler.PageResp
import org.cyk.base.utils.DateUtils
import org.cyk.base.utils.PageUtils
import org.springframework.stereotype.Service
import java.util.Date

@Service
class AlbumInfoRepoImpl: AlbumInfoRepo, ServiceImpl<AlbumInfoMapper, AlbumInfoDo>() {

    override fun save(dto: AlbumPubDto): Int {
        val obj = map(dto)
        val result = super.save(obj)
        dto.albumId = obj.id
        return if(result) 1 else 0
    }

    override fun delById(albumId: Long): Int {
        val result = ktUpdate()
            .eq(AlbumInfoDo::id, albumId)
            .remove()
        return if(result) 1 else 0
    }

    override fun updateAlbumInfo(dto: AlbumUpdateDto): Int = dto.run {
        val result = ktUpdate()
            .set(AlbumInfoDo::title, title)
            .set(AlbumInfoDo::content, content)
            .set(AlbumInfoDo::utTime, Date())
            .eq(AlbumInfoDo::id, albumId)
            .update()
        if(result) 1 else 0
    }

    override fun queryById(albumId: Long): AlbumInfo? = ktQuery()
        .eq(AlbumInfoDo::id, albumId)
        .one()
        ?.let { map(it) }

    override fun queryByIds(aIds: List<Long>): List<AlbumInfo> = ktQuery()
        .`in`(AlbumInfoDo::id, aIds)
        .list()
        .map(::map)

    override fun pageAlbumVo(q: QueryAlbumVoDto): PageResp<AlbumInfo> = with(q) {
        val b = ktQuery()
        if(!albumIds.isNullOrEmpty()) {
            b.eq(AlbumInfoDo::state, state)
            b.`in`(!albumIds.isNullOrEmpty(), AlbumInfoDo::id, albumIds)
        } else {
            b.eq(userId != null, AlbumInfoDo::userId, userId)
            b.eq(AlbumInfoDo::state, state)
        }
        val pageResult = b.page(PageUtils.pageDesc(
                start,
                limit + 1,
                "ct_time",
                AlbumInfoDo::class.java
            )).addOrder(OrderItem.desc("ct_time"))
        val result = pageResult.records
            .map(::map)
            .toMutableList()
        val hasMore = result.size == q.limit + 1
        if(hasMore) {
            result.removeLast()
        }
        PageResp.ok(hasMore, start + 1L, result, null)
    }

    private fun map(o: AlbumInfoDo): AlbumInfo = with(o) {
        AlbumInfo(
            id = id!!,
            userId = userId,
            title = title,
            content = content,
            state = state,
            ctTime = ctTime!!,
            utTime = utTime!!
        )
    }



    private fun map(dto: AlbumPubDto): AlbumInfoDo = with(dto)  {
        AlbumInfoDo(
            userId = userId,
            title = title,
            content = content,
            state = state!!,
        )
    }

}