package org.cyk.user.service.manager.impl

import org.cyk.base.exception.AppException2
import org.cyk.base.handler.ApiResp
import org.cyk.base.handler.ApiStatus
import org.cyk.user.repo.mysql.UserInfoRepo
import org.cyk.user.service.manager.UserDiskManager
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import jakarta.annotation.Resource

@Service
class UserDiskManagerImpl(
    val infoRepo: UserInfoRepo
): UserDiskManager {

    override fun saveAvatar(
        avatar: MultipartFile,
        savePathAbs: String
    ) {
        //1.检查路径是否存在，不存在就创建
        val path = Paths.get(savePathAbs)
        if(!Files.exists(path)) {
            Files.createDirectories(path)
        }
        //2.制作头像保存路径
        val avatarFile = File(savePathAbs)
        //3.上传到服务器
        avatar.transferTo(avatarFile)
    }

    override fun delAvatar(path: String) {
        if(path.isBlank()) return
        val dbAvatar = File(path)
        val isDel = dbAvatar.delete()
        if(!isDel) {
            log.warn("删除旧照片失败！path: $path")
            throw AppException2(ApiResp.no(ApiStatus.SERVER_ERROR))
        }
    }

    private val log = LoggerFactory.getLogger(UserDiskManagerImpl::class.java)

}