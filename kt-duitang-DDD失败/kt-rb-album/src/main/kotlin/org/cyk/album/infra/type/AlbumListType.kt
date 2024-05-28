package org.cyk.album.infra.type

enum class AlbumListType(val code: Int) {

    UNKNOWN(0),
    ALL_LIST(1),
    OWN_LIST(2),
    COLLECT_LIST(3),
    LIKE_LIST(4),
    PRIVATE_LIST(5),
    ;

    companion object {

        fun getType(code: Int): AlbumListType = entries.firstOrNull { it.code == code } ?: UNKNOWN

    }

}