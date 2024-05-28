package org.cyk.album.repo.es.impl

import co.elastic.clients.elasticsearch._types.SortOrder
import co.elastic.clients.elasticsearch.core.search.FieldSuggester
import co.elastic.clients.elasticsearch.core.search.FieldSuggesterBuilders
import co.elastic.clients.elasticsearch.core.search.Suggester
import org.cyk.album.facade.AlbumSearchDto
import org.cyk.album.facade.AlbumSuggestDto
import org.cyk.album.repo.es.AlbumDocRepo
import org.cyk.album.service.message.AddAlbumInfoMsg
import org.cyk.album.service.message.UpdateAlbumInfoMsg
import org.cyk.base.handler.PageResp
import org.springframework.data.annotation.Id
import org.springframework.data.domain.PageRequest
import org.springframework.data.elasticsearch.annotations.CompletionField
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import org.springframework.data.elasticsearch.core.SearchHit
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates
import org.springframework.data.elasticsearch.core.query.UpdateQuery
import org.springframework.data.elasticsearch.core.suggest.Completion
import org.springframework.data.elasticsearch.core.suggest.response.Suggest
import org.springframework.stereotype.Service
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate
import org.springframework.data.elasticsearch.client.elc.NativeQuery
import org.springframework.data.elasticsearch.core.query.HighlightQuery
import org.springframework.data.elasticsearch.core.query.highlight.Highlight
import org.springframework.data.elasticsearch.core.query.highlight.HighlightField
import org.springframework.data.elasticsearch.core.query.highlight.HighlightParameters

@Document(indexName = "album_doc")
data class AlbumDocDo (
    @Id
    @Field(type = FieldType.Keyword)
    val id: Long,
    @Field(name = "user_id", type = FieldType.Long)
    val userId: Long,
    @Field(type = FieldType.Text, analyzer = "ik_max_word", copyTo = ["suggestion"])
    val title: String,
    @Field(type = FieldType.Text, analyzer = "ik_smart")
    val content: String,
    @Field(name = "ct_time", type = FieldType.Long)
    val ctTime: Long,
    @Field(name = "ut_time", type = FieldType.Long)
    val utTime: Long,
    @CompletionField(maxInputLength = 100, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    val suggestion: Completion? = null,
)

@Service
class AlbumDocRepoImpl(
    val elasticsearchTemplate: ElasticsearchTemplate
): AlbumDocRepo {

    override fun save(msg: AddAlbumInfoMsg): Int {
        val obj = map(msg)
        elasticsearchTemplate.save(obj)
        return 1
    }

    override fun delete(albumId: Long): Long {
        elasticsearchTemplate.delete(albumId.toString(), AlbumDocDo::class.java)
        return 1L
    }

    override fun update(msg: UpdateAlbumInfoMsg): Int {
        val query = UpdateQuery.builder(msg.albumId.toString()) //指定修改的文档 id
            .withDocument(org.springframework.data.elasticsearch.core.document.Document.create() //指定修改字段
                .append("title", msg.title)
                .append("content", msg.content)
                .append("ut_time", msg.utTime)
            )
            .build()
        val result = elasticsearchTemplate.update(query, IndexCoordinates.of("album_doc")).result
        return result.ordinal
    }

    private fun map(msg: AddAlbumInfoMsg): AlbumDocDo = with(msg) {
        AlbumDocDo(
            id = albumId,
            userId = userId,
            title = title,
            content = content,
            ctTime = ctTime,
            utTime = utTime,
        )
    }

    override fun pageAlbumDoc(o: AlbumSearchDto): PageResp<AlbumDoc> {
        //高亮字段
        val highField = listOf(
            HighlightField("title"),
            HighlightField("content"),
        )
        val query = with(NativeQuery.builder()) {
            if(o.text.isNullOrBlank()) {
                withQuery { q -> q.multiMatch { it } }
            } else {
                withQuery { q -> q
                    .multiMatch { m -> m
                        .fields("title", "content") .query(o.text)
                    }
                }
            }
            withHighlightQuery(
                HighlightQuery(
                    Highlight(
                        HighlightParameters.builder()
                            .withPreTags("<span style='color:#f73131'>")
                            .withPostTags("</span>")
                            .withFragmentSize(10)
                            .withNumberOfFragments(1)
                            .build(),
                        highField
                    ),
                    String::class.java
                )
            )
            withSort { s -> s.field { f->f.field("ct_time").order(SortOrder.Desc)} }
            withPageable(PageRequest.of(o.start, o.limit + 1))
        }.build()

        val result = elasticsearchTemplate.search(query, AlbumDocDo::class.java)
            .map(::map)
            .toMutableList()
        val hasMore = result.size == o.limit + 1
        if(hasMore) {
            result.removeLast()
        }
        return PageResp.ok(
            hasMore,
            o.start + 1L,
            result,
            null
        )
    }

    override fun querySuggests(o: AlbumSuggestDto): List<String> {
        val fieldSuggest = FieldSuggester.Builder()
            .text(o.text)
            .completion(
                FieldSuggesterBuilders.completion()
                    .field("suggestion")
                    .skipDuplicates(true)
                    .size(o.limit)
                    .build()
            ).build()

        val query = NativeQuery.builder()
            .withSuggester(Suggester.of { s -> s.suggesters("title_suggest", fieldSuggest) })
            .build()
        val hits = elasticsearchTemplate.search(query, AlbumDocDo::class.java)
        val suggests = hits.suggest
            ?.getSuggestion("title_suggest") //根据自定义补全名获取对应的补全结果集
            ?.entries?.get(0)      //结果集(记录了根据什么前缀(prefix)进行自动补全，补全的结果对象...)
            ?.options?.map(::map) ?: emptyList()  //补全的结果对象(其中 text 就是自动补全的结果)
        return suggests
    }

    private fun map(hit: Suggest.Suggestion.Entry.Option): String {
        return hit.text
    }

    private fun map(hit: SearchHit<AlbumDocDo>): AlbumDoc {
        val o = hit.content
        return AlbumDoc(
            id = o.id,
            userId = o.userId,
            title = hit.getHighlightField("title").let {
                if(it.size > 0) it[0] else o.title },
            content = hit.getHighlightField("content").let {
                if(it.size > 0) it[0] else o.content
            },
            ctTime = o.ctTime,
            utTime = o.utTime,
        )
    }

}

data class AlbumDoc (
    val id: Long,
    val userId: Long,
    val title: String,
    val content: String,
    val ctTime: Long,
    val utTime: Long
)
