package org.cyk.user.service.msg

import com.fasterxml.jackson.databind.ObjectMapper
import org.cyk.base.model.type.UserMsgStateType
import org.cyk.msg.model.UserMsgMqConst
import org.cyk.msg.publisher.UserMsgClearMsg
import org.cyk.msg.publisher.UserMsgMsg
import org.cyk.user.repo.mongo.UserMsgRepo
import org.cyk.user.repo.mysql.UserInfoRepo
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import jakarta.annotation.Resource

@Component
class UserMsgConsumer(
    val mapper: ObjectMapper,
    val infoRepo: UserInfoRepo,
    val msgRepo: UserMsgRepo,
) {

    @Transactional
    @RabbitListener(queues = [UserMsgMqConst.USER_MSG_QUEUE])
    fun sendUserMsgHandler(json: String) {
        val msg = mapper.readValue(json, UserMsgMsg::class.java)
        msgRepo.tryIncr(msg.userId, UserMsgStateType.getType(msg.type))
        msgRepo.save(msg)
    }

    @RabbitListener(queues = [UserMsgMqConst.USER_MSG_CLEAR_QUEUE])
    fun userMsgClearHandler(json: String) {
        val msg = mapper.readValue(json, UserMsgClearMsg::class.java)
        msgRepo.clear(msg.userId, UserMsgStateType.getType(msg.type))
    }

}
