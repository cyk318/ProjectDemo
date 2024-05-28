package org.cyk.album.service.manager

interface AlbumRedisManager {

    /**
     * 增加搜推短语
     * 使用 FIFO 淘汰策略
     */
    fun addSearchSuggestForZSet(userId: Long, suggest: String, score: Double)

    fun getSuggestForZet(userId: Long): String?

}