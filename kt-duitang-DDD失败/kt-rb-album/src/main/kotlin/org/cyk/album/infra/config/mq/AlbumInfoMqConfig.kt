package org.cyk.album.infra.config.mq

import org.cyk.album.infra.constants.MqConst
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Queue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AlbumInfoMqConfig {

    @Bean
    fun albumInfoDirect() = DirectExchange(MqConst.ALBUM_INFO_DIRECT)

    @Bean
    fun queryAlbumInfoVoEventQueue() = Queue(MqConst.QUERY_ALBUM_INFO_VO_EVENT)

    @Bean
    fun addAlbumLikeEventQueue() = Queue(MqConst.ADD_ALBUM_LIKE_EVENT)

    @Bean
    fun addAlbumCollectEventQueue() = Queue(MqConst.ADD_ALBUM_COLLECT_EVENT)

    @Bean
    fun addAlbumInfoEventQueue() = Queue(MqConst.ADD_ALBUM_INFO_EVENT)

    @Bean
    fun updateAlbumInfoEventQueue() = Queue(MqConst.UPDATE_ALBUM_INFO_EVENT)

    @Bean
    fun delAlbumInfoEventQueue() = Queue(MqConst.DEL_ALBUM_INFO_EVENT)

    @Bean
    fun queryAlbumInfoVoEventBinding() = BindingBuilder
            .bind(queryAlbumInfoVoEventQueue())
            .to(albumInfoDirect())
            .with(MqConst.QUERY_ALBUM_INFO_VO_EVENT)

    @Bean
    fun addAlbumLikeEventBinding() = BindingBuilder
            .bind(addAlbumLikeEventQueue())
            .to(albumInfoDirect())
            .with(MqConst.ADD_ALBUM_LIKE_EVENT)

    @Bean
    fun addAlbumCollectEventBinding() = BindingBuilder
            .bind(addAlbumCollectEventQueue())
            .to(albumInfoDirect())
            .with(MqConst.ADD_ALBUM_COLLECT_EVENT)

    @Bean
    fun addAlbumInfoEventBinding() = BindingBuilder
        .bind(addAlbumInfoEventQueue())
        .to(albumInfoDirect())
        .with(MqConst.ADD_ALBUM_INFO_EVENT)

    @Bean
    fun updateAlbumInfoEventBinding() = BindingBuilder
        .bind(updateAlbumInfoEventQueue())
        .to(albumInfoDirect())
        .with(MqConst.UPDATE_ALBUM_INFO_EVENT)

    @Bean
    fun delAlbumInfoEventBinding() = BindingBuilder
        .bind(delAlbumInfoEventQueue())
        .to(albumInfoDirect())
        .with(MqConst.DEL_ALBUM_INFO_EVENT)

}