package org.cyk.album.service

import org.cyk.album.facade.*
import org.cyk.base.handler.PageResp

interface AlbumInfoService {

    fun pub(d: AlbumPubDto): Int
    fun update(d: AlbumUpdateDto): Int
    fun del(d: AlbumDelDto): Int
    fun pageAlbumVo(d: AlbumListDto, curUserId: Long): PageResp<AlbumVo>
    fun queryAlbumVo(albumId: Long, curUserId: Long): AlbumVo
    fun like(dto: LikeDto): Long
    fun collect(dto: CollectDto): Long

}