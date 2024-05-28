package org.cyk.user.infra.type

enum class ChatUserType(
    val code: Int
) {

    STRANGER(1),        //陌生人
    FOLLOWED(2),        //已关注
    FANS(3),            //粉丝
    FRIEND(4),          //好友(相互关注)
    ;

}