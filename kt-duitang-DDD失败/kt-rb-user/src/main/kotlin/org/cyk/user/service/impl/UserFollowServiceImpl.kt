package org.cyk.user.service.impl

import org.cyk.base.exception.AppException
import org.cyk.base.exception.AppException2
import org.cyk.user.facade.FollowDto
import org.cyk.user.facade.FollowListDto
import org.cyk.user.facade.FollowVo
import org.cyk.base.handler.ApiResp
import org.cyk.base.handler.ApiStatus
import org.cyk.base.handler.PageResp
import org.cyk.base.model.type.UserMsgStateType
import org.cyk.msg.publisher.UserMsgPublisher
import org.cyk.user.infra.type.FollowListType
import org.cyk.user.repo.mongo.UserFollowRepo
import org.cyk.user.repo.mysql.UserInfoRepo
import org.cyk.user.service.UserFollowService
import org.cyk.user.service.cmd.UserFollowCmd
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UserFollowServiceImpl(
    val followRepo: UserFollowRepo,
    val followCmd: UserFollowCmd,
    val infoRepo: UserInfoRepo,
    val msgPublisher: UserMsgPublisher,
): UserFollowService {

    override fun follow(dto: FollowDto): Long {
        validParam(dto)
        val exists = followRepo.exists(dto)
        val result = if(!exists) {
            +followCmd.save(dto)
        } else {
            -followCmd.delete(dto)
        }
        if(result > 0) {
            msgPublisher.sendUserMsg(dto.targetId, dto.postId, dto.targetId.toString(), "", UserMsgStateType.FOLLOW_STATE.code)
        }
        return result
    }

    override fun pageFollowVo(dto: FollowListDto): PageResp<FollowVo?> {
        val result = when(FollowListType.getType(dto.type)) {
            FollowListType.FOLLOW -> followCmd.pageFollow(dto)
            FollowListType.FANS -> followCmd.pageFans(dto)
            else -> throw AppException(ApiStatus.INVALID_PARAM, "查询 粉丝/关注 分页时，发现非法 type: ${dto.type}")
        }
        return result
    }

    private fun validParam(dto: FollowDto) = with(dto) {
        require(postId != targetId) { "不能关注自己！userId: $targetId" }
        requireNotNull(infoRepo.queryById(targetId)) { "不能关注不存在的人！postId: $postId, targetId: $targetId" }
    }

}