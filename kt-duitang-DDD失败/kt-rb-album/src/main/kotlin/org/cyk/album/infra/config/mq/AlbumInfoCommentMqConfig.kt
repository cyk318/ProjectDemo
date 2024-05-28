package org.cyk.album.infra.config.mq

import org.cyk.album.infra.constants.MqConst
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Queue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AlbumInfoCommentMqConfig {

    @Bean
    fun albumCommentDirect() = DirectExchange(MqConst.ALBUM_COMMENT_DIRECT)

    @Bean
    fun addAlbumCommentEventQueue() = Queue(MqConst.ADD_ALBUM_COMMENT_EVENT)

    @Bean
    fun delAlbumCommentEventQueue() = Queue(MqConst.DEL_ALBUM_COMMENT_EVENT)

    @Bean
    fun addAlbumChildCommentEventQueue() = Queue(MqConst.ADD_ALBUM_COMMENT_CHILD_EVENT)

    @Bean
    fun delAlbumChildCommentEventQueue() = Queue(MqConst.DEL_ALBUM_COMMENT_CHILD_EVENT)

    @Bean
    fun addAlbumCommentLikeEventQueue() = Queue(MqConst.ADD_ALBUM_COMMENT_LIKE_EVENT)

    @Bean
    fun addAlbumChildCommentLikeEventQueue() = Queue(MqConst.ADD_ALBUM_COMMENT_CHILD_LIKE_EVENT)

    @Bean
    fun addAlbumCommentEventBinding() = BindingBuilder
            .bind(addAlbumCommentEventQueue())
            .to(albumCommentDirect())
            .with(MqConst.ADD_ALBUM_COMMENT_EVENT)

    @Bean
    fun delAlbumCommentEventBinding() = BindingBuilder
            .bind(delAlbumCommentEventQueue())
            .to(albumCommentDirect())
            .with(MqConst.DEL_ALBUM_COMMENT_EVENT)

    @Bean
    fun addAlbumChildCommentEventBinding() = BindingBuilder
            .bind(addAlbumChildCommentEventQueue())
            .to(albumCommentDirect())
            .with(MqConst.ADD_ALBUM_COMMENT_CHILD_EVENT)

    @Bean
    fun delAlbumChildCommentEventBinding() = BindingBuilder
            .bind(delAlbumChildCommentEventQueue())
            .to(albumCommentDirect())
            .with(MqConst.DEL_ALBUM_COMMENT_CHILD_EVENT)

    @Bean
    fun addAlbumCommentLikeEventBinding() = BindingBuilder
        .bind(addAlbumCommentLikeEventQueue())
        .to(albumCommentDirect())
        .with(MqConst.ADD_ALBUM_COMMENT_LIKE_EVENT)

    @Bean
    fun addAlbumChildCommentLikeEventBinding() = BindingBuilder
        .bind(addAlbumChildCommentLikeEventQueue())
        .to(albumCommentDirect())
        .with(MqConst.ADD_ALBUM_COMMENT_CHILD_LIKE_EVENT)

}