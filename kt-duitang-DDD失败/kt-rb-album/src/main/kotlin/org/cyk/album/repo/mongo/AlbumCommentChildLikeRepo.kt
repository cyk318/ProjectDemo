package org.cyk.album.repo.mongo

import org.cyk.album.facade.CommentChildLikeDto


interface AlbumCommentChildLikeRepo {

    fun delByCommentId(id: String): Long
    fun exists(postId: Long, targetId: String): Boolean
    fun save(dto: CommentChildLikeDto): Long
    abstract fun del(postId: Long, targetId: String): Long

}