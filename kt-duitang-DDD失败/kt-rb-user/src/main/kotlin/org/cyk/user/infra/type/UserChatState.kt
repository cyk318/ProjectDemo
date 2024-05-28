package org.cyk.user.infra.type

enum class UserChatState(
    val code: Int,
    val msg: String,
) {

    ONLINE(1, "online"),      //上线
    OFFLINE(2, "offline"),     //下线

}