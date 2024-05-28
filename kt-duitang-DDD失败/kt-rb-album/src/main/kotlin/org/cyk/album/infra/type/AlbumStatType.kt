package org.cyk.album.infra.type

enum class AlbumStatType(
    val code: Int,
    val msg: String
) {

    UNKNOWN(0, "unknown"),
    PV(1, "page_view"),
    LIKE_CNT(2, "like_cnt"),
    COMMENT_CNT(3, "comment_cnt"),
    COLLECT_CNT(4, "collect_cnt"),
    ;

    companion object {
        fun getType(code: Int) = entries.firstOrNull {it.code == code} ?: UNKNOWN
    }

}