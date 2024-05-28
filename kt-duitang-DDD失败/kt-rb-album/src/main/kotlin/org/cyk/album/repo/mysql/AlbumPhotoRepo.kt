package org.cyk.album.repo.mysql

import org.cyk.album.facade.AlbumPhotoDto
import org.cyk.album.repo.mysql.mapper.AlbumPhoto
import org.cyk.album.repo.mysql.mapper.AlbumPhotoDo

interface AlbumPhotoRepo {

    fun saveBatch(list: List<AlbumPhotoDto>): Int
    fun delByAlbumId(albumId: Long): Int
    fun queryOne(albumId: Long, sort: Int): AlbumPhotoDo
    fun queryByAlbumId(albumId: Long): List<AlbumPhoto>
    fun queryByAlbumIds(albumIds: List<Long>): List<AlbumPhoto>

}