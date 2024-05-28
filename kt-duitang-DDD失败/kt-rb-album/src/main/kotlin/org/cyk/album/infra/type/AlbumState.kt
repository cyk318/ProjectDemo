package org.cyk.album.infra.type

enum class AlbumState(val code: Int) {

    UNKNOWN(0),
    NORMAL(1),
    PRIVATE(2),
    DRAFT(3),
    LOCK(4),
    ;

    companion object {
        fun getType(code: Int) = entries.firstOrNull {it.code == code} ?: UNKNOWN
    }

}