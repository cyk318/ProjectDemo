package org.cyk.user.service.impl

import org.cyk.base.handler.PageResp
import org.cyk.msg.publisher.UserMsgPublisher
import org.cyk.msg.utils.DateUtils
import org.cyk.user.facade.PageUserMsgDto
import org.cyk.user.facade.UserMsgStateVo
import org.cyk.user.facade.UserMsgVo
import org.cyk.user.repo.mongo.UserMsgRepo
import org.cyk.user.repo.mongo.impl.UserMsgInfo
import org.cyk.user.repo.mongo.impl.UserMsgState
import org.cyk.user.repo.mysql.UserInfoRepo
import org.cyk.user.repo.mysql.mapper.UserInfo
import org.cyk.user.service.UserMsgService
import org.cyk.user.service.cmd.UserMsgCmd
import org.springframework.stereotype.Service
import jakarta.annotation.Resource

@Service
class UserMsgServiceImpl(
    val msgRepo: UserMsgRepo,
    val cmd: UserMsgCmd,
    val msgPublisher: UserMsgPublisher,
): UserMsgService {


    override fun queryUserMsgStateVo(userId: Long): UserMsgStateVo {
        val result = msgRepo.queryUserMsgState(userId)!!
        return map(result)
    }

    override fun pageUserMsgVo(dto: PageUserMsgDto): PageResp<UserMsgVo> {
        val result = cmd.pageUserMsgVod(dto)
        msgPublisher.userMsgClear(dto.userId, dto.type)
        return result
    }

    private fun map(obj: UserMsgState) = with(obj) {
        UserMsgStateVo (
            followState = followState,
            likeState = likeState,
            commentState = commentState,
            collectState = collectState,
        )
    }

}