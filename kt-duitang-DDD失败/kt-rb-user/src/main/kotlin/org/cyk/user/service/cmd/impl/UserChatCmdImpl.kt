package org.cyk.user.service.cmd.impl

import org.cyk.user.facade.ChatUserVo
import org.cyk.user.facade.FollowDto
import org.cyk.user.facade.websocket.ChatRoom
import org.cyk.user.infra.constants.RedisKeyConst
import org.cyk.user.infra.type.ChatUserType
import org.cyk.user.repo.mongo.UserFollowRepo
import org.cyk.user.repo.mysql.UserInfoRepo
import org.cyk.user.repo.mysql.mapper.UserInfo
import org.cyk.user.service.cmd.UserChatCmd
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import jakarta.annotation.Resource

@Service
class UserChatCmdImpl: UserChatCmd {

    @Resource(name = "stringRedisTemplate")
    private lateinit var redisTemplate: StringRedisTemplate

    @Resource private lateinit var infoRepo: UserInfoRepo
    @Resource private lateinit var followRepo: UserFollowRepo

    override fun queryChatList(userId: Long): List<ChatUserVo> {
        val friendIds = redisTemplate.opsForSet().members(RedisKeyConst.makeChatFriendSetKey(userId))
            ?.map { it.toLong() }
            ?.toList()
            ?: emptyList()
        val friendInfos = infoRepo.queryByIds(friendIds).map { map(it, userId) }
        return friendInfos
    }

    private fun map(friendInfo: UserInfo, curUserId: Long) = with(friendInfo) {
        ChatUserVo(
            userId = id,
            username = username,
            avatar = avatar,
            type = let {
                val youFollowOther = followRepo.exists(FollowDto(curUserId, id))
                val otherFollowYou = followRepo.exists(FollowDto(id, curUserId))
                when {
                    youFollowOther && otherFollowYou -> ChatUserType.FRIEND.code
                    youFollowOther && !otherFollowYou -> ChatUserType.FOLLOWED.code
                    !youFollowOther && otherFollowYou -> ChatUserType.FANS.code
                    else -> ChatUserType.STRANGER.code
                }
            }
        )
    }

}