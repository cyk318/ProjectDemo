package org.cyk.user.repo.impl

import com.baomidou.mybatisplus.annotation.TableName
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper
import org.apache.ibatis.annotations.Mapper
import org.cyk.user.repo.UserinfoRepo
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Mapper
interface UserinfoMapper: BaseMapper<Userinfo>

@TableName("user_info")
data class Userinfo (
    val id: Long? = null,
    val username: String,
    val phone: String,
    val address: String,
    val cTime: LocalDateTime = LocalDateTime.now(),
    val uTime: LocalDateTime = LocalDateTime.now(),
)

@Repository
class UserinfoRepoImpl(
    private val userinfoMapper: UserinfoMapper,
): UserinfoRepo {

}