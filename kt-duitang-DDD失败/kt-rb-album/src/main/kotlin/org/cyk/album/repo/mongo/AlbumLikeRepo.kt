package org.cyk.album.repo.mongo

import org.cyk.album.repo.mongo.impl.AlbumLike

interface AlbumLikeRepo {

    fun save(postId: Long, targetId: Long): Long
    fun delete(postId: Long, targetId: Long): Long
    fun exists(postId: Long, targetId: Long): Boolean
    fun queryByUserId(userId: Long): List<AlbumLike?>
    fun queryOne(targetId: Long, postId: Long): AlbumLike?

}