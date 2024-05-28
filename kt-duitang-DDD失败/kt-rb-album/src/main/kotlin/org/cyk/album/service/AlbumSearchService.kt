package org.cyk.album.service

import org.cyk.album.facade.AlbumSearchDto
import org.cyk.album.facade.AlbumSuggestDto
import org.cyk.album.facade.AlbumSuggestVo
import org.cyk.album.facade.AlbumVo
import org.cyk.base.handler.ApiResp
import org.cyk.base.handler.PageResp

interface AlbumSearchService {

    fun pageAlbumVo(o: AlbumSearchDto): PageResp<AlbumVo>

    fun suggest(o: AlbumSuggestDto): List<AlbumSuggestVo>

}