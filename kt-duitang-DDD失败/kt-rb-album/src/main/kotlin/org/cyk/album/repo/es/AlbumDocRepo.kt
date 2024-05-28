package org.cyk.album.repo.es

import org.cyk.album.facade.AlbumSearchDto
import org.cyk.album.facade.AlbumSuggestDto
import org.cyk.album.repo.es.impl.AlbumDoc
import org.cyk.album.service.message.AddAlbumInfoMsg
import org.cyk.album.service.message.UpdateAlbumInfoMsg
import org.cyk.base.handler.PageResp

interface AlbumDocRepo {

    fun save(msg: AddAlbumInfoMsg): Int

    fun delete(albumId: Long): Long
    fun update(msg: UpdateAlbumInfoMsg): Int
    fun pageAlbumDoc(o: AlbumSearchDto): PageResp<AlbumDoc>
    fun querySuggests(o: AlbumSuggestDto): List<String>

}