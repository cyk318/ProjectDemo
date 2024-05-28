package org.cyk.album.service.cmd

import org.cyk.album.facade.AlbumDelDto
import org.cyk.album.facade.AlbumPubDto
import org.cyk.album.facade.AlbumUpdateDto
import org.cyk.album.facade.AlbumVo
import org.cyk.album.repo.mysql.mapper.AlbumInfo
import org.cyk.album.service.impl.QueryAlbumVoDto
import org.cyk.base.handler.PageResp

interface AlbumCmd {
    fun saveAlbum(dto: AlbumPubDto): Int
    fun updateAlbum(d: AlbumUpdateDto): Int
    fun deleteAlbum(d: AlbumDelDto): Int
    fun pageAlbumVo(q: QueryAlbumVoDto, curUserId: Long): PageResp<AlbumVo>
    fun queryAlbumVo(o: AlbumInfo, curUserId: Long): AlbumVo

}