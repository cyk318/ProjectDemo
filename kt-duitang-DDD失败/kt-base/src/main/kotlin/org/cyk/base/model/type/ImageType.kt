package org.cyk.base.model.type

enum class ImageType(val code: Int, val msg: String) {
    UNKNOWN(0, "unknown"),
    JPG(1, "jpg"),
    JPEG(2, "jpeg"),
    PNG(3, "png"),
    GIF(4, "gif"),
    BMP(5, "bmp"),
    TIFF(6, "tiff"),
    WEBP(7, "webp");

    companion object {
        fun getType(msg: String): ImageType {
            return entries.firstOrNull { it.msg == msg } ?: UNKNOWN
        }
    }
}
