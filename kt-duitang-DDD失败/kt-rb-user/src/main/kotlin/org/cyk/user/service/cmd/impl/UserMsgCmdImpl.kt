package org.cyk.user.service.cmd.impl

import org.cyk.base.handler.PageResp
import org.cyk.msg.utils.DateUtils
import org.cyk.user.facade.PageUserMsgDto
import org.cyk.user.facade.UserMsgVo
import org.cyk.user.repo.mongo.UserMsgRepo
import org.cyk.user.repo.mongo.impl.UserMsgInfo
import org.cyk.user.repo.mysql.UserInfoRepo
import org.cyk.user.repo.mysql.mapper.UserInfo
import org.cyk.user.service.cmd.UserMsgCmd
import org.springframework.stereotype.Service
import jakarta.annotation.Resource

@Service
class UserMsgCmdImpl(
    val msgRepo: UserMsgRepo,
    val infoRepo: UserInfoRepo,
): UserMsgCmd {


    override fun pageUserMsgVod(dto: PageUserMsgDto): PageResp<UserMsgVo> {
        val page = msgRepo.pageUserMsgInfo(dto)
        val list = page.result
        val uInfoMap = infoRepo.queryByIds(list.map { it.postId }).associateBy { it.id }

        return PageResp.ok(
            page.hasMore,
            page.nextStart,
            map(list, uInfoMap),
            page.total
        )
    }

    private fun map(
        list: List<UserMsgInfo>,
        uInfoMap: Map<Long, UserInfo>
    ): List<UserMsgVo> = list.map {
        UserMsgVo(
            postId = it.postId,
            postUsername =  uInfoMap[it.postId]!!.username,
            postAvatar = uInfoMap[it.postId]!!.avatar,
            targetId = it.targetId,
            targetContent = it.targetContent,
            ctTime = DateUtils.format(it.ctTime)
        )
    }

}