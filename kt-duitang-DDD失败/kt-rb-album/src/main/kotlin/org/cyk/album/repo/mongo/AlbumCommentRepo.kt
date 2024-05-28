package org.cyk.album.repo.mongo

import org.cyk.album.facade.CommentDto
import org.cyk.album.facade.PageCommentVoDto
import org.cyk.album.repo.mongo.impl.AlbumComment
import org.cyk.base.handler.PageResp

interface AlbumCommentRepo {

    fun save(dto: CommentDto): Int
    fun incrCommentCnt(id: String, incr: Long): Long
    fun incrLikeCnt(id: String, incr: Long): Long
    fun queryById(id: String): AlbumComment?
    fun del(id: String): Long
    fun pageComment(dto: PageCommentVoDto): PageResp<AlbumComment>

}