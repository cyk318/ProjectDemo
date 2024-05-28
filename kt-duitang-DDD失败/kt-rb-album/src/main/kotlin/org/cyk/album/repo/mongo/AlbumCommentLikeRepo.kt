package org.cyk.album.repo.mongo

import org.cyk.album.facade.CommentLikeDto

interface AlbumCommentLikeRepo {

    fun delByCommentId(id: String): Long
    fun del(postId: Long, targetId: String): Long
    fun exists(postId: Long, targetId: String): Boolean
    fun save(dto: CommentLikeDto): Long

}