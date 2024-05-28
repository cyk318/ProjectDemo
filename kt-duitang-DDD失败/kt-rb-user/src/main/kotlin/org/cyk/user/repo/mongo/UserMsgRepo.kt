package org.cyk.user.repo.mongo

import org.cyk.base.handler.PageResp
import org.cyk.base.model.type.UserMsgStateType
import org.cyk.msg.publisher.UserMsgMsg
import org.cyk.user.facade.PageUserMsgDto
import org.cyk.user.repo.mongo.impl.UserMsgInfo
import org.cyk.user.repo.mongo.impl.UserMsgState

interface UserMsgRepo {

    fun init(userId: Long)
    fun save(msg: UserMsgMsg): Int
    fun tryIncr(userId: Long, type: UserMsgStateType): Boolean
    fun clear(userId: Long, type: UserMsgStateType): Long
    fun queryUserMsgState(userId: Long): UserMsgState?
    fun pageUserMsgInfo(dto: PageUserMsgDto): PageResp<UserMsgInfo>

}