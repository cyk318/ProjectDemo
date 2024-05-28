package org.cyk.user.service.cmd.impl

import org.cyk.user.facade.RegDto
import org.cyk.user.facade.UpdateUserInfoDto
import org.cyk.user.repo.mysql.UserInfoRepo
import org.cyk.user.service.cmd.UserInfoCmd
import org.cyk.user.service.manager.UserDiskManager
import org.cyk.base.utils.ImageUtils
import org.cyk.user.facade.FollowDto
import org.cyk.user.repo.mongo.UserFollowRepo
import org.cyk.user.repo.mongo.UserMsgRepo
import org.cyk.user.repo.mysql.mapper.UserInfo
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.IOException
import java.io.Serializable
import java.util.*
import jakarta.annotation.Resource

@Service
class UserInfoCmdImpl(
    val infoRepo: UserInfoRepo,
    val diskManager: UserDiskManager,
    val msgRepo: UserMsgRepo,
    val followRepo: UserFollowRepo,
) : UserInfoCmd {

    @Value("\${save-path.avatar}")
    private lateinit var sysAvatarPath: String

    override fun saveUserInfo(dto: RegDto): Int {
        val result = infoRepo.save(dto)
        msgRepo.init(dto.userId)
        return if(result) 1 else 0
    }

    @Transactional(rollbackFor = [IOException::class, RuntimeException::class])
    override fun updateUserInfo(dto: UpdateUserInfoDto): Int {
        //为了避免事务失效，先操作数据库，最后再操作硬盘
        val savePathAbs = if(dto.avatar == null || dto.avatar!!.isEmpty) {
            null
        } else {
            val suffix = ImageUtils.getImageFileAndChk(dto.avatar!!).second //头像的前缀没有实际意义
            ImageUtils.makeAvatarSavePath(sysAvatarPath, suffix)
        }

        val info = infoRepo.queryById(dto.id!!) //保留一份数据库中的数据，将来用来判断是否要删除旧照片

        //数据库
        val result = dto.run {
            avatarPath = savePathAbs
            infoRepo.updateUserInfo(this)
        }

        //硬盘
        savePathAbs?.let {
            if(info!!.avatar.isNotBlank()) {
                diskManager.delAvatar(info.avatar) //如果旧的存在，就删除旧的
            }
            diskManager.saveAvatar(dto.avatar!!, dto.avatarPath!!) //添加新的
        }
        return if(result) 1 else 0
    }

    override fun queryUserInfo(id: Long, curUserId: Long): UserInfoVo? {
        return infoRepo.queryById(id)?.let { map(it, curUserId) }
    }

    private fun map(o: UserInfo, curUserId: Long) = o.run {
        UserInfoVo(
            id = id,
            username = username,
            brief = brief,
            phone = phone ?: "",
            gender = gender,
            birthday = birthday,
            avatar = avatar,
            state = state,
            fansCnt = followRepo.fansCnt(id),
            followCnt = followRepo.followCnt(id),
            youFollowOther = followRepo.exists(FollowDto(curUserId, id)),
            ctTime = ctTime,
            utTime = utTime,
        )
    }

}

data class UserInfoVo(
    val id: Long,
    val username: String,
    val brief: String,
    val phone: String,
    val gender: Int, //性别: 0女 1男 2保密(默认)
    val birthday: String,
    val avatar: String? = "",
    val state: Int, //状态: 0正常(默认) 1封号 2管理员
    val fansCnt: Long,
    val followCnt: Long,
    val youFollowOther: Boolean, //是否关注该用户
    val ctTime: Date,
    val utTime: Date
): Serializable

