package org.cyk.user.repo.mysql.repo

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import org.cyk.base.utils.PasswordUtils
import org.cyk.user.facade.RegDto
import org.cyk.user.facade.UpdateUserInfoDto
import org.cyk.user.repo.mysql.UserInfoRepo
import org.cyk.user.repo.mysql.mapper.UserInfo
import org.cyk.user.repo.mysql.mapper.UserInfoDo
import org.cyk.user.repo.mysql.mapper.UserInfoMapper
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserInfoRepoImpl: ServiceImpl<UserInfoMapper, UserInfoDo>(), UserInfoRepo {

    override fun save(dto: RegDto): Boolean {
        val obj = map(dto)
        super.save(obj)
        dto.userId = obj.id!!
        return true
    }

    override fun updateUserInfo(dto: UpdateUserInfoDto) = with(dto) {
        ktUpdate()
            .set(!username.isNullOrBlank(), UserInfoDo::username, username)
            .set(!password.isNullOrBlank(), UserInfoDo::password, PasswordUtils.encrypt(password))
            .set(!brief.isNullOrBlank(), UserInfoDo::brief, brief)
            .set(!phone.isNullOrBlank(), UserInfoDo::phone, phone)
            .set(!birthday.isNullOrBlank(), UserInfoDo::birthday, birthday)
            .set(!avatarPath.isNullOrBlank(), UserInfoDo::avatar, avatarPath)
            .set(UserInfoDo::utTime, Date())
            .eq(UserInfoDo::id, id)
            .update()
    }

    override fun queryById(id: Long): UserInfo? = ktQuery()
            .eq(UserInfoDo::id, id)
            .one()
            ?.let(::map)

    override fun queryByName(username: String): UserInfo? = ktQuery()
            .eq(UserInfoDo::username, username)
            .one()
            ?.let(::map)

    override fun queryByIds(ids: List<Long>): List<UserInfo> {
        if(ids.isEmpty()) {
            return emptyList()
        }
        return ktQuery()
            .`in`(UserInfoDo::id, ids)
            .list()
            .map(::map)
    }

    private fun map(dto: RegDto) = with(dto) {
        UserInfoDo(
            username = username,
            password = password,
            ctTime = Date(),
            utTime = Date()
        )
    }

    fun map(o: UserInfoDo) = o.run {
        UserInfo(
            id = id!!,
            username = username,
            password = password,
            brief = brief!!,
            phone = phone,
            gender = gender!!,
            birthday = birthday!!,
            avatar = avatar!!,
            state = state!!,
            ctTime = ctTime!!,
            utTime = utTime!!
        )
    }

}
