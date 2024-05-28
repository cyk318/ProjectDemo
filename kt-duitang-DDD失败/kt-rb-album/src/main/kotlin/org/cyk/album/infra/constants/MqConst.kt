package org.cyk.album.infra.constants

object MqConst {

    const val ALBUM_INFO_DIRECT = "album.info.direct"
    const val QUERY_ALBUM_INFO_VO_EVENT = "query.album.info.vo.event"
    const val ADD_ALBUM_LIKE_EVENT = "add.album.like.event"
    const val ADD_ALBUM_COLLECT_EVENT = "add.album.collect.event"
    const val ADD_ALBUM_INFO_EVENT = "add.album.info.event"
    const val UPDATE_ALBUM_INFO_EVENT = "update.album.info.event"
    const val DEL_ALBUM_INFO_EVENT = "del.album.info.event"

    const val ALBUM_COMMENT_DIRECT = "album.comment.direct"
    const val ADD_ALBUM_COMMENT_EVENT = "add.album.comment.event"
    const val DEL_ALBUM_COMMENT_EVENT = "del.album.comment.event"
    const val ADD_ALBUM_COMMENT_CHILD_EVENT = "add.album.comment.child.event"
    const val DEL_ALBUM_COMMENT_CHILD_EVENT = "del.album.comment.child.event"
    const val ADD_ALBUM_COMMENT_LIKE_EVENT = "add.album.comment.like.event"
    const val ADD_ALBUM_COMMENT_CHILD_LIKE_EVENT = "add.album.comment.child.like.event"

    const val ERROR_DIRECT = "error.direct"
    const val ERROR_QUEUE = "error.queue"

}