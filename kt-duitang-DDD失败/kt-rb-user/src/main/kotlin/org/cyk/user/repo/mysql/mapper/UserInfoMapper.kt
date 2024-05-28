package org.cyk.user.repo.mysql.mapper

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper
import java.util.Date

@TableName("user_info")
data class UserInfoDo(
    @TableId
    var id: Long? = null,
    var username: String,
    var password: String,
    var brief: String? = null,
    var phone: String? = null,
    var gender: Int? = null, //性别: 0女 1男 2保密(默认)
    var birthday: String? = null,
    var avatar: String? = null,
    var state: Int? = 0, //状态: 0正常(默认) 1封号 2管理员
    var ctTime: Date? = null,
    var utTime: Date? = null,
)

data class UserInfo(
    var id: Long,
    var username: String,
    var password: String? = null,
    var brief: String,
    var phone: String?,
    var gender: Int, //性别: 0女 1男 2保密(默认)
    var birthday: String,
    var avatar: String,
    var state: Int, //状态: 0正常(默认) 1封号 2管理员
    var ctTime: Date,
    var utTime: Date,
)

@Mapper
interface UserInfoMapper: BaseMapper<UserInfoDo>
