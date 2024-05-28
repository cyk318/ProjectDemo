package org.cyk.album.repo.mongo

import org.cyk.album.facade.CollectDto
import org.cyk.album.repo.mongo.impl.AlbumCollect

interface AlbumCollectRepo {

    fun save(postId: Long, targetId: Long): Long
    fun delete(postId: Long, targetId: Long): Long
    fun queryByUserId(userId: Long): List<AlbumCollect?>
    fun exists(postId: Long, targetId: Long): Boolean


}