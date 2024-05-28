package org.cyk.album.repo.mysql

import org.cyk.album.repo.mysql.mapper.AlbumTag

interface AlbumTagRepo {

    fun queryList(): List<AlbumTag>


}