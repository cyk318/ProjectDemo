package org.cyk.base.utils
import org.cyk.base.exception.AppException2
import org.cyk.base.handler.ApiResp
import org.cyk.base.handler.ApiStatus
import org.cyk.base.model.type.ImageSortType
import org.cyk.base.model.type.ImageType
import org.slf4j.LoggerFactory
import org.springframework.web.multipart.MultipartFile
import java.awt.image.BufferedImage
import java.io.IOException
import java.util.UUID
import javax.imageio.ImageIO

object ImageUtils {

    private val log = LoggerFactory.getLogger(ImageUtils::class.java)

    fun makeAvatarSavePath(sysAvatarPath: String, suffix: String): String {
        return sysAvatarPath + UUID.randomUUID() + ".$suffix"
    }

    fun makePhotoSavePath(sysPhotoPath: String, suffix: String): String {
        return sysPhotoPath + UUID.randomUUID() + ".$suffix"
    }

    /**
     * 校验图片文件格式，如果正确就返回文件名和后缀
     * @return key: prefix   value: suffix
     */
    fun getImageFileAndChk(photoFile: MultipartFile): Pair<String, String> {
        val result = run {
            //1.文件类型校验
            typeChk(photoFile)
            //2.文件名校验
            getImageNameChk(photoFile)
        }
        return result
    }

    private fun getImageNameChk(photoFile: MultipartFile): Pair<String, String> {
        //key: prefix   value: suffix
        val photoName = photoFile.originalFilename
        if (photoName.isNullOrBlank()) {
            log.info("图片名称不能为空！")
            throw AppException2(ApiResp.no(ApiStatus.INVALID_PARAM))
        }

        val lastIndex = photoName.lastIndexOf(".")
        if (lastIndex == -1) {
            log.info("图片名非法！ photoName: {}", photoName)
            throw AppException2(ApiResp.no(ApiStatus.INVALID_PARAM))
        }

        val prefix = photoName.substring(0, lastIndex) //前闭后开
        val suffix = photoName.substring(lastIndex + 1)
        if (ImageSortType.getType(prefix.toInt()) == ImageSortType.UNKNOWN) {
            log.info("图片名前缀非法！ photoName: {}", photoName)
            throw AppException2(ApiResp.no(ApiStatus.INVALID_PARAM))
        }
        if (ImageType.getType(suffix) == ImageType.UNKNOWN) {
            log.info("图片名后缀非法！ photoName: {}", photoName)
            throw AppException2(ApiResp.no(ApiStatus.INVALID_PARAM))
        }
        return prefix to suffix
    }

    private fun typeChk(photoFile: MultipartFile) {
        photoFile.inputStream.use { inputStream ->
            //尝试读取图片，读取失败为 null
            val read: BufferedImage = ImageIO.read(inputStream) ?: throw IOException()
        }
    }
}
