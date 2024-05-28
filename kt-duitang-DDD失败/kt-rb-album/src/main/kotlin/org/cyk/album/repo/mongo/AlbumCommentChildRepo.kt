package org.cyk.album.repo.mongo

import org.cyk.album.facade.CommentChildDto
import org.cyk.album.facade.PageCommentChildDto
import org.cyk.album.repo.mongo.impl.AlbumCommentChild
import org.cyk.base.handler.PageResp

interface AlbumCommentChildRepo {

    fun incr(id: String, incr: Long): Long
    fun save(dto: CommentChildDto): Int
    fun queryById(id: String): AlbumCommentChild?
    fun del(targetId: String): Long
    fun pageCommentChild(d: PageCommentChildDto): PageResp<AlbumCommentChild>

}