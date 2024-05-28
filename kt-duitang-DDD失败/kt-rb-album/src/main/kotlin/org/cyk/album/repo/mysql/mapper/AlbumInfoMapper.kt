package org.cyk.album.repo.mysql.mapper

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper
import java.util.*

@TableName("album_info")
data class AlbumInfoDo(
    @TableId
    var id: Long? = null,
    var userId: Long,
    var title: String = "",
    var content: String = "",
    var state: Int,
    var ctTime: Date? = null,
    var utTime: Date? = null,
)

data class AlbumInfo(
    var id: Long,
    var userId: Long,
    var title: String,
    var content: String,
    var state: Int,
    var ctTime: Date,
    var utTime: Date,
)

@Mapper
interface AlbumInfoMapper: BaseMapper<AlbumInfoDo>