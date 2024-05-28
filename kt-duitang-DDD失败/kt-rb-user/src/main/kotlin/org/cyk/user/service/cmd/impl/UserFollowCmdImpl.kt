package org.cyk.user.service.cmd.impl

import org.cyk.user.facade.FollowDto
import org.cyk.user.facade.FollowListDto
import org.cyk.user.facade.FollowVo
import org.cyk.base.handler.PageResp
import org.cyk.user.repo.mongo.UserFollowRepo
import org.cyk.user.repo.mysql.UserInfoRepo
import org.cyk.user.repo.mysql.mapper.UserInfo
import org.cyk.user.service.cmd.UserFollowCmd
import org.springframework.stereotype.Service
import jakarta.annotation.Resource

@Service
class UserFollowCmdImpl(
    var followRepo: UserFollowRepo,
    var infoRepo: UserInfoRepo,
): UserFollowCmd {

    override fun save(dto: FollowDto): Long {
        val result = followRepo.save(dto)
        return 1L
    }

    override fun delete(dto: FollowDto): Long {
        val result = followRepo.delete(dto)
        return result
    }

    override fun pageFollow(dto: FollowListDto): PageResp<FollowVo?> {
        val pageResult = followRepo.pageFollow(dto)
        val userIds = pageResult.result
            .asSequence()
            .map { it.targetId }
            .toList()

        val vo = infoRepo.queryByIds(userIds).map { map(it, dto.userId) }
        return PageResp.ok(
            pageResult.hasMore,
            pageResult.nextStart,
            vo,
            pageResult.total
        )
    }

    override fun pageFans(dto: FollowListDto): PageResp<FollowVo?> {
        val pageResult = followRepo.pageFans(dto)
        val userIds = pageResult.result
            .asSequence()
            .map { it.postId }
            .toList()

        val vo = infoRepo.queryByIds(userIds).map { map(it, dto.userId) }
        return PageResp.ok(
            pageResult.hasMore,
            pageResult.nextStart,
            vo,
            pageResult.total
        )
    }

    private fun map(it: UserInfo?, curUserId: Long) = it?.run {
        FollowVo(
            userId = id,
            username = username,
            avatar = avatar,
            youFollowOther = followRepo.exists(FollowDto(curUserId, id)),
            otherFollowYou = followRepo.exists(FollowDto(id, curUserId)),
        )
    }


}
