package org.cyk.album.service.cmd

import org.cyk.album.facade.AlbumSearchDto
import org.cyk.album.facade.AlbumSuggestDto
import org.cyk.album.facade.AlbumSuggestVo
import org.cyk.album.facade.AlbumVo
import org.cyk.base.handler.PageResp

interface AlbumSearchCmd {

    fun pageAlbumVo(o: AlbumSearchDto): PageResp<AlbumVo>

    fun suggest(o: AlbumSuggestDto): List<AlbumSuggestVo>

}