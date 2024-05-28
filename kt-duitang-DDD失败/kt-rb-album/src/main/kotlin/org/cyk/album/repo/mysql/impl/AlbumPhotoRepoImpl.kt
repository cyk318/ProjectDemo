package org.cyk.album.repo.mysql.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.cyk.album.facade.AlbumPhotoDto
import org.cyk.album.repo.mysql.AlbumPhotoRepo
import org.cyk.album.repo.mysql.mapper.AlbumPhoto
import org.cyk.album.repo.mysql.mapper.AlbumPhotoDo
import org.cyk.album.repo.mysql.mapper.AlbumPhotoMapper
import org.springframework.stereotype.Service

@Service
class AlbumPhotoRepoImpl: AlbumPhotoRepo, ServiceImpl<AlbumPhotoMapper, AlbumPhotoDo>() {

    override fun saveBatch(list: List<AlbumPhotoDto>): Int = run {
        val objs = list.map(::map)
        val result = super.saveBatch(objs)
        if(result) objs.size else 0
    }

    override fun delByAlbumId(albumId: Long): Int {
        val result = ktUpdate()
            .eq(AlbumPhotoDo::albumId, albumId)
            .remove()
        return if(result) 1 else 0
    }

    override fun queryOne(albumId: Long, sort: Int): AlbumPhotoDo = ktQuery()
        .eq(AlbumPhotoDo::albumId, albumId)
        .eq(AlbumPhotoDo::sort, sort)
        .one()

    override fun queryByAlbumId(albumId: Long): List<AlbumPhoto> = ktQuery()
        .eq(AlbumPhotoDo::albumId, albumId)
        .list()
        .map(::map)

    override fun queryByAlbumIds(albumIds: List<Long>): List<AlbumPhoto> {
        if(albumIds.isEmpty()) {
            return emptyList()
        }
        return ktQuery()
            .`in`(AlbumPhotoDo::albumId, albumIds)
            .list()
            .map(::map)
    }

    private fun map(o: AlbumPhotoDo): AlbumPhoto = with(o) {
        AlbumPhoto(
            id = id!!,
            albumId = albumId,
            photo = photo,
            sort = sort
        )
    }


    private fun map(dto: AlbumPhotoDto): AlbumPhotoDo = with(dto) {
        AlbumPhotoDo(
            albumId = albumId!!,
            photo = photoPath,
            sort = sort
        )
    }
}