package org.cyk.user.service.manager

import org.cyk.user.facade.AddFriendDto
import org.cyk.user.repo.mysql.mapper.UserInfo
import org.cyk.user.repo.mysql.mapper.UserInfoDo
import org.springframework.stereotype.Component

interface UserRedisManager {

    fun saveToken(obj: UserInfo): String

    fun saveFriendIfNotExists(dto: AddFriendDto): Long

}