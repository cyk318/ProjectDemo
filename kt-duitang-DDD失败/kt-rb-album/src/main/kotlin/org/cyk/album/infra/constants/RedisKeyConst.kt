package org.cyk.album.infra.constants

object RedisKeyConst {

    private const val ALBUM_LIKE = "ALBUM_LIKE_"
    private const val SEARCH_SUGGEST_ZSET = "SEARCH_SUGGEST_ZSET_"

    fun makeAlbumLikeKey(album: Long): String {
        return ALBUM_LIKE + album
    }
    fun makeSearchSuggestZSetKey(userId: Long): String {
        return SEARCH_SUGGEST_ZSET + userId
    }

}