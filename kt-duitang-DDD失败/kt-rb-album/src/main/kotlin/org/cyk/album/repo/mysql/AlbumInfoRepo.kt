package org.cyk.album.repo.mysql

import org.cyk.album.facade.AlbumPubDto
import org.cyk.album.facade.AlbumUpdateDto
import org.cyk.album.repo.mysql.mapper.AlbumInfo
import org.cyk.album.repo.mysql.mapper.AlbumInfoDo
import org.cyk.album.service.impl.QueryAlbumVoDto
import org.cyk.base.handler.PageResp

interface AlbumInfoRepo {

    fun save(dto: AlbumPubDto): Int
    fun delById(albumId: Long): Int
    fun updateAlbumInfo(dto: AlbumUpdateDto): Int
    fun queryById(albumId: Long): AlbumInfo?
    fun queryByIds(aIds: List<Long>): List<AlbumInfo>
    fun pageAlbumVo(q: QueryAlbumVoDto): PageResp<AlbumInfo>

}