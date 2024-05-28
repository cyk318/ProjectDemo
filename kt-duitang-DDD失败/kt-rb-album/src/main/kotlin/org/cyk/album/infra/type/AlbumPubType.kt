package org.cyk.album.infra.type

enum class AlbumPubType(val code: Int) {

    UNKNOWN(0),
    NORMAL(1),
    PRIVATE(2),
    DRAFT(3),
    TIME(4),
    ;

    companion object {
        fun getType(code: Int): AlbumPubType {
            return entries.firstOrNull { it.code == code } ?: UNKNOWN
        }
    }

}