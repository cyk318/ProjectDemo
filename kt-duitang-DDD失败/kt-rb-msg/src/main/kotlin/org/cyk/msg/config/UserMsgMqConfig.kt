package org.cyk.msg.config

import org.cyk.msg.model.UserMsgMqConst
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Queue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserMsgMqConfig {

    @Bean
    fun userMsgDirect() = DirectExchange(UserMsgMqConst.USER_MSG_DIRECT)

    @Bean
    fun userMsgQueue() = Queue(UserMsgMqConst.USER_MSG_QUEUE, true)

    @Bean
    fun userMsgClearQueue() = Queue(UserMsgMqConst.USER_MSG_CLEAR_QUEUE, true)

    @Bean
    fun userMsgBinding() = BindingBuilder
        .bind(userMsgQueue())
        .to(userMsgDirect())
        .with(UserMsgMqConst.USER_MSG_QUEUE)

    @Bean
    fun userMsgClearBinding() = BindingBuilder
        .bind(userMsgClearQueue())
        .to(userMsgDirect())
        .with(UserMsgMqConst.USER_MSG_CLEAR_QUEUE)


}