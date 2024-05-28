package org.cyk.user.infra.type

enum class FollowListType(val code: Int) {

    UNKNOWN(0),
    FANS(1),
    FOLLOW(2),
    ;
    companion object {
        fun getType(code: Int): FollowListType {
            return entries.firstOrNull { it.code == code} ?: UNKNOWN
        }
    }

}