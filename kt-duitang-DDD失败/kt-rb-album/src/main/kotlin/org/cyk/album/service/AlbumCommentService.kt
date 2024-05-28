package org.cyk.album.service

import org.cyk.album.facade.*
import org.cyk.base.handler.PageResp


interface AlbumCommentService {
    fun comment(dto: CommentDto): Int
    fun del(dto: CommentDel): Long
    fun delChild(dto: CommentChildDel): Long
    fun addChild(dto: CommentChildDto): Int
    fun pageCommentChildVo(dto: PageCommentChildDto, curUserId: Long): PageResp<CommentChildVo>
    fun like(dto: CommentLikeDto): Long
    fun likeChild(dto: CommentChildLikeDto): Long
    fun pageCommentVo(dto: PageCommentVoDto, curUserId: Long): PageResp<CommentVo>

}