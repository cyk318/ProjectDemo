package org.cyk.user.service.cmd

import org.cyk.user.facade.FollowDto
import org.cyk.user.facade.FollowListDto
import org.cyk.user.facade.FollowVo
import org.cyk.base.handler.PageResp

interface UserFollowCmd {

    fun save(dto: FollowDto): Long
    fun delete(dto: FollowDto): Long
    fun pageFollow(dto: FollowListDto): PageResp<FollowVo?>
    fun pageFans(dto: FollowListDto): PageResp<FollowVo?>

}