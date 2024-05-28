package org.cyk.msg.publisher

import com.fasterxml.jackson.databind.ObjectMapper
import org.cyk.msg.model.UserMsgMqConst
import org.cyk.msg.utils.DateUtils
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component
import jakarta.annotation.Resource

@Component
class UserMsgPublisher(
    val mapper: ObjectMapper,
    val rabbitTemplate: RabbitTemplate,
) {

    fun sendUserMsg(
        userId: Long, //谁的消息
        postId: Long,
        targetId: String,
        targetContent: String,
        type: Int,
    ) {
        val msg = UserMsgMsg(
            userId = userId,
            postId = postId,
            targetId = targetId,
            targetContent = targetContent,
            type = type,
            ctTime = DateUtils.now(),
            utTime = DateUtils.now(),
        )
        rabbitTemplate.convertAndSend(
            UserMsgMqConst.USER_MSG_DIRECT,
            UserMsgMqConst.USER_MSG_QUEUE,
            mapper.writeValueAsString(msg)
        )
    }

    fun userMsgClear(
        userId: Long,
        type: Int
    ) {
        val msg = UserMsgClearMsg(
            userId = userId,
            type = type,
        )
        rabbitTemplate.convertAndSend(
            UserMsgMqConst.USER_MSG_DIRECT,
            UserMsgMqConst.USER_MSG_CLEAR_QUEUE,
            mapper.writeValueAsString(msg)
        )
    }

}

data class UserMsgClearMsg(
    val userId: Long = -1,
    val type: Int = 0
)

data class UserMsgMsg(
    var userId: Long = -1, //谁的消息
    var postId: Long = -1,
    val targetId: String = "",
    val targetContent: String = "",
    val type: Int = 0,
    val ctTime: Long = 0,
    val utTime: Long = 0,
)
