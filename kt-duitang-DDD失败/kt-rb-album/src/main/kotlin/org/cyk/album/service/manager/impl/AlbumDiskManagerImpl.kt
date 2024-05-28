package org.cyk.album.service.manager.impl

import org.cyk.base.exception.AppException2
import org.cyk.album.facade.AlbumPhotoDto
import org.cyk.base.handler.ApiResp
import org.cyk.base.handler.ApiStatus
import org.cyk.album.repo.mysql.AlbumPhotoRepo
import org.cyk.album.service.manager.AlbumDiskManager
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import jakarta.annotation.Resource

@Service
class AlbumDiskManagerImpl: AlbumDiskManager {

    @Value("\${save-path.photo}")
    private lateinit var sysPhotoPath: String

    @Resource private lateinit var photoRepo: AlbumPhotoRepo

    override fun savePhotos(dtos: List<AlbumPhotoDto>) {
        dtos.forEach {
            val path = Paths.get(sysPhotoPath)
            if(!Files.exists(path)) {
                Files.createDirectories(path)
            }
            Files.copy(it.photo, Paths.get(it.photoPath), StandardCopyOption.REPLACE_EXISTING)
            it.photo.close()
        }
    }

    override fun delPhotos(filePaths: List<String?>) {
        filePaths.forEach {
            it?.let {
                val path = Path.of(it)
                try {
                    if(Files.exists(path)) {
                        Files.delete(path)
                    } else {
                        log.warn("文件删除时，发现文件 $it 不存在！")
                    }
                } catch (e: IOException) {
                    log.error("删除文件 $it 时发生错误！")
                    throw AppException2(ApiResp.no(ApiStatus.SERVER_ERROR))
                }
            }
        }
    }

    private val log = LoggerFactory.getLogger(AlbumDiskManager::class.java)

}
