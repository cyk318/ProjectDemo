package org.cyk.user.repo.mysql

import org.cyk.user.facade.RegDto
import org.cyk.user.facade.UpdateUserInfoDto
import org.cyk.user.repo.mysql.mapper.UserInfo

interface UserInfoRepo {

    fun save(dto: RegDto): Boolean
    fun updateUserInfo(dto: UpdateUserInfoDto): Boolean
    fun queryById(id: Long): UserInfo?
    fun queryByName(username: String): UserInfo?
    fun queryByIds(ids: List<Long>): List<UserInfo>


}