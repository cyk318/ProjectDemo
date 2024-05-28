package org.cyk.album.service

import org.cyk.album.facade.AlbumTagVo

interface AlbumTagService {

    fun queryList(): List<AlbumTagVo>

}