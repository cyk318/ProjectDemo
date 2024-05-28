package org.cyk.album.service.cmd

import org.cyk.album.facade.*
import org.cyk.base.handler.PageResp

interface AlbumCommentCmd {

    fun del(dto: CommentDel): Long
    fun delChild(dto: CommentChildDel): Long
    fun pageCommentChildVo(dto: PageCommentChildDto, curUserId: Long): PageResp<CommentChildVo>
    fun pageCommentVo(dto: PageCommentVoDto, curUserId: Long): PageResp<CommentVo>

}