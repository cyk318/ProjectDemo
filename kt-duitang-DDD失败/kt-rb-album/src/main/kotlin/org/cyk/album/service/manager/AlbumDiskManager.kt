package org.cyk.album.service.manager

import org.cyk.album.facade.AlbumPhotoDto

interface AlbumDiskManager {

    fun savePhotos(dtos: List<AlbumPhotoDto>)

    fun delPhotos(filePaths: List<String?>)

}