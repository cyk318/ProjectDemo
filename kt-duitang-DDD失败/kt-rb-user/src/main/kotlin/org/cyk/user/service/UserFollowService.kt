package org.cyk.user.service

import org.cyk.user.facade.FollowDto
import org.cyk.user.facade.FollowListDto
import org.cyk.user.facade.FollowVo
import org.cyk.base.handler.PageResp

interface UserFollowService {

    fun follow(dto: FollowDto): Long

    fun pageFollowVo(dto: FollowListDto): PageResp<FollowVo?>

}