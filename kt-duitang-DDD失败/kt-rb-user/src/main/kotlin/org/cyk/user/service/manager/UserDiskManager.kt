package org.cyk.user.service.manager

import org.springframework.web.multipart.MultipartFile

interface UserDiskManager {

    /**
     * @return: 头像地址
     */
    fun saveAvatar( avatar: MultipartFile, savePathAbs: String)

    fun delAvatar(path: String)

}