package org.cyk.album.infra.config.mq

import org.cyk.album.infra.constants.MqConst
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.rabbit.retry.MessageRecoverer
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ErrorMessageConfig {

    @Bean
    fun errorMessageExchange(): DirectExchange {
        return DirectExchange(MqConst.ERROR_DIRECT);
    }

    @Bean
    fun errorQueue(): Queue {
        return Queue(MqConst.ERROR_QUEUE, true);
    }

    @Bean
    fun errorBinding(): Binding {
        return BindingBuilder
            .bind(errorQueue())
            .to(errorMessageExchange())
            .with(MqConst.ERROR_QUEUE);
    }

//    @Bean
//    fun republishMessageRecoverer(rabbitTemplate: RabbitTemplate): MessageRecoverer {
//        return RepublishMessageRecoverer(
//            rabbitTemplate,
//            MqConst.ERROR_DIRECT,
//            MqConst.ERROR_QUEUE,
//        );
//    }

}
