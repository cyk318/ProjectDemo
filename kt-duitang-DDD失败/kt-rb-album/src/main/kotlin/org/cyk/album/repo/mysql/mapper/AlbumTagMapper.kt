package org.cyk.album.repo.mysql.mapper

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper
import java.util.Date

@TableName("album_tag")
data class AlbumTagDo(
    @TableId
    val id: Long,
    val name: String,
    val ctTime: Date,
    val utTime: Date,
)

@Mapper
interface AlbumTagMapper: BaseMapper<AlbumTagDo>

data class AlbumTag(
    val id: Long,
    val name: String,
    val ctTime: Date,
    val utTime: Date,
)
