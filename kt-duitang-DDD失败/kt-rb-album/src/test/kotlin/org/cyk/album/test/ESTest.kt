package org.cyk.album.test

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate
import jakarta.annotation.Resource

@SpringBootTest
class ESTest {

    @Resource private lateinit var elasticsearchRestTemplate: ElasticsearchRestTemplate

    @Test
    fun test() {
    }


}