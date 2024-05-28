package org.cyk.album.repo.mysql.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.cyk.album.repo.mysql.AlbumTagRepo
import org.cyk.album.repo.mysql.mapper.AlbumTag
import org.cyk.album.repo.mysql.mapper.AlbumTagDo
import org.cyk.album.repo.mysql.mapper.AlbumTagMapper
import org.springframework.stereotype.Service

@Service
class AlbumTagRepoImpl: ServiceImpl<AlbumTagMapper, AlbumTagDo>(),AlbumTagRepo {

    override fun queryList(): List<AlbumTag> = ktQuery()
        .list()
        .map(::map)

    private fun map(o: AlbumTagDo) = with(o) {
        AlbumTag (
            id = id,
            name = name,
            ctTime = ctTime,
            utTime = utTime,
        )
    }

}
