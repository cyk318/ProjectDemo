package org.cyk.album.repo.mongo.impl

import org.cyk.album.facade.AlbumPubDto
import org.cyk.album.infra.type.AlbumStatType
import org.cyk.album.repo.mongo.AlbumStatRepo
import org.cyk.base.utils.DateUtils
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Service
import jakarta.annotation.Resource

@Document("album_stat")
data class AlbumStatDo(
    @Id
    var id: Long, //专辑 id
    var pageView: Long,
    var likeCnt: Long,
    var commentCnt: Long,
    var collectCnt: Long,
    var ctTime: Long,
    var utTime: Long,
)

data class AlbumStat(
    var id: Long,
    var pageView: Long,
    var likeCnt: Long,
    var commentCnt: Long,
    var collectCnt: Long,
    var ctTime: Long,
    var utTime: Long,
)

@Service
class AlbumStatRepoImpl: AlbumStatRepo {

    @Resource private lateinit var mongoTemplate: MongoTemplate

    override fun save(dto: AlbumPubDto): Int {
        val o = map(dto)
        val result = mongoTemplate.save(o)
        return 1
    }

    override fun delByAlbumId(albumId: Long): Long {
        val result = mongoTemplate.remove(
            Query.query(Criteria.where("_id").`is`(albumId)),
            AlbumStatDo::class.java
        )
        return result.deletedCount
    }

    override fun incr(albumId: Long, incr: Long, type: AlbumStatType): Long {
        val c = Criteria.where("_id").`is`(albumId)
        val u = Update().inc(type.msg, incr)
        val result = mongoTemplate.updateFirst(Query.query(c), u, AlbumStatDo::class.java)
        return result.modifiedCount
    }

    override fun queryByAlbumIds(aIds: List<Long>): List<AlbumStat> {
        val result = mongoTemplate.find(
            Query.query(Criteria.where("_id").`in`(aIds)),
            AlbumStatDo::class.java
        ).map(::map)
        return result
    }

    override fun queryByAlbumId(albumId: Long): AlbumStat? {
        val result = mongoTemplate.findOne(
            Query.query(Criteria.where("_id").`is`(albumId)),
            AlbumStatDo::class.java
        )?.let { map(it) }
        return result
    }

    private fun map(dto: AlbumStatDo): AlbumStat = with(dto){
        AlbumStat(
            id = id,
            pageView = pageView,
            likeCnt = likeCnt,
            commentCnt = commentCnt,
            collectCnt = collectCnt,
            ctTime = ctTime,
            utTime = utTime
        )
    }

    private fun map(dto: AlbumPubDto): AlbumStatDo = with(dto) {
        AlbumStatDo(
            id = albumId!!,
            pageView = 0,
            likeCnt = 0,
            commentCnt = 0,
            collectCnt = 0,
            ctTime = DateUtils.now(),
            utTime = DateUtils.now()
        )
    }

}