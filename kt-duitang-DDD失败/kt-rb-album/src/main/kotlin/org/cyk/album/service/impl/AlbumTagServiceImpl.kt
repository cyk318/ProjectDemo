package org.cyk.album.service.impl

import org.cyk.album.facade.AlbumTagVo
import org.cyk.album.repo.mysql.AlbumTagRepo
import org.cyk.album.repo.mysql.mapper.AlbumTag
import org.cyk.album.service.AlbumTagService
import org.springframework.stereotype.Service
import jakarta.annotation.Resource

@Service
class AlbumTagServiceImpl: AlbumTagService {

    @Resource private lateinit var tagRepo: AlbumTagRepo

    override fun queryList(): List<AlbumTagVo> {
        return tagRepo.queryList().map(::map)
    }

    private fun map(obj: AlbumTag) = with(obj) {
        AlbumTagVo (
            id = id,
            name = name
        )
    }

}