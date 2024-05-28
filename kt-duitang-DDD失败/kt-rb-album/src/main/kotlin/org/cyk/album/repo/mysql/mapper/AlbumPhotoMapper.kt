package org.cyk.album.repo.mysql.mapper

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper

@TableName("album_photo")
data class AlbumPhotoDo(
    @TableId
    var id: Long? = null,
    var albumId: Long,
    var photo: String,
    var sort: Int,
)

data class AlbumPhoto(
    var id: Long,
    var albumId: Long,
    var photo: String,
    var sort: Int,
)

@Mapper
interface AlbumPhotoMapper: BaseMapper<AlbumPhotoDo>