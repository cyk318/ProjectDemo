package org.cyk.base.model.type

enum class UserMsgStateType(
    val code: Int,
    val msg: String,
) {

    UNKNOWN(0, "unknown"),
    FOLLOW_STATE(1, "follow_state"),
    LIKE_STATE(2, "like_state"),
    COLLECT_STATE(3, "collect_state"),
    COMMENT_STATE(4, "comment_state"),
    ;

    companion object {
        fun getType(code: Int) = entries.firstOrNull { it.code == code } ?: UNKNOWN
    }

}