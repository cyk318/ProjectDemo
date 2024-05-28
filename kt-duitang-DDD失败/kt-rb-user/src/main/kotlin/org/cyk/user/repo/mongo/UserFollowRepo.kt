package org.cyk.user.repo.mongo

import org.cyk.user.facade.FollowListDto
import org.cyk.base.handler.PageResp
import org.cyk.user.facade.FollowDto
import org.cyk.user.repo.mongo.impl.UserFollowDo

interface UserFollowRepo {

    fun fansCnt(userId: Long): Long
    fun followCnt(userId: Long): Long
    fun exists(dto: FollowDto): Boolean
    fun save(dto: FollowDto): Int
    fun delete(dto: FollowDto): Long
    fun pageFollow(dto: FollowListDto): PageResp<UserFollowDo>
    fun pageFans(dto: FollowListDto): PageResp<UserFollowDo>

}