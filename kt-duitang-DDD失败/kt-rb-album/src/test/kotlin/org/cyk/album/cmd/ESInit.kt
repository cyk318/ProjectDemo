package org.cyk.album.cmd

import org.cyk.album.AlbumApplication
import org.cyk.album.infra.type.AlbumState
import org.cyk.album.repo.es.impl.AlbumDocDo
import org.cyk.album.repo.mysql.impl.AlbumInfoRepoImpl
import org.cyk.album.repo.mysql.mapper.AlbumInfoDo
import org.cyk.base.utils.DateUtils
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate
import jakarta.annotation.Resource

@SpringBootTest(classes = [AlbumApplication::class])
class ESInit {

    @Resource private lateinit var restTemplate: ElasticsearchRestTemplate
    @Resource private lateinit var albumInfoRepo: AlbumInfoRepoImpl

    @Test
    fun run() {
        initAlbumAgg()
    }

    fun initAlbumAgg() {
        val idxExists = restTemplate.indexOps(AlbumDocDo::class.java).exists()
        if(idxExists) {
            restTemplate.indexOps(AlbumDocDo::class.java).delete()
        }
        restTemplate.indexOps(AlbumDocDo::class.java).create()
        restTemplate.indexOps(AlbumDocDo::class.java).putMapping(
            restTemplate.indexOps(AlbumDocDo::class.java).createMapping()
        )

        val list = albumInfoRepo.ktQuery().list()
            .asSequence()
            .filter { it.state == AlbumState.NORMAL.code }
            .map(::map)
            .toList()
        val result = restTemplate.save(list)
    }

    fun map(o: AlbumInfoDo): AlbumDocDo = with(o) {
        AlbumDocDo(
            id = id!!,
            userId = userId,
            title = title,
            content = content,
            ctTime = DateUtils.formatToLong(ctTime!!),
            utTime = DateUtils.formatToLong(utTime!!),
        )
    }

}
