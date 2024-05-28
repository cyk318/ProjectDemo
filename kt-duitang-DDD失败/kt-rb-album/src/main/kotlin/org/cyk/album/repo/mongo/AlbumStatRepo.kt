package org.cyk.album.repo.mongo

import org.cyk.album.facade.AlbumPubDto
import org.cyk.album.infra.type.AlbumStatType
import org.cyk.album.repo.mongo.impl.AlbumStat


interface AlbumStatRepo {

    fun save(dto: AlbumPubDto): Int
    fun delByAlbumId(albumId: Long): Long
    fun incr(albumId: Long, incr: Long ,type: AlbumStatType): Long
    fun queryByAlbumIds(aIds: List<Long>): List<AlbumStat>
    fun queryByAlbumId(albumId: Long): AlbumStat?

}