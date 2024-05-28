package org.cyk.user.facade.websocket

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import org.cyk.user.infra.type.UserChatState
import org.cyk.user.repo.mysql.UserInfoRepo
import org.cyk.user.repo.mysql.mapper.UserInfo
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap
import jakarta.annotation.Resource
import jakarta.websocket.OnClose
import jakarta.websocket.OnError
import jakarta.websocket.OnMessage
import jakarta.websocket.OnOpen
import jakarta.websocket.Session
import jakarta.websocket.server.PathParam
import jakarta.websocket.server.ServerEndpoint

data class ChatMsgReq (
    @field:JsonProperty("target_id")
    val targetId: Long = -1,
    val msg: String = "",
)

data class ChatMsgResp (
    val postInfo: ChatUserInfo? = null,
    val msg: String? = null,
    /**
     * 1上线 2下线
     * @see UserChatState
     */
    val state: Int = UserChatState.ONLINE.code,
)

@ServerEndpoint("/websocket/{id}")
@Component
class ChatRoom {

    companion object {
        //在线用户
        private val onlineUserMap = ConcurrentHashMap<Long, Session>()
        //离线消息
        private val offlineMsgMap = ConcurrentHashMap<Long, MutableList<OfflineChatMsg>>()
        private val mapper = ObjectMapper()
        private lateinit var userInfoRepo: UserInfoRepo
    }

    @Resource
    fun setUserInfoRepo(userInfoRepo: UserInfoRepo) {
        Companion.userInfoRepo = userInfoRepo
    }

    private lateinit var postSession: Session
    private lateinit var postInfo: ChatUserInfo

    @OnOpen
    fun onOpen(
        session: Session,
        @PathParam("id") id: Long,
    ) {
        //初始化用户上线信息
        this.postSession = session
        this.postInfo = map(userInfoRepo.queryById(id)!!)
        onlineUserMap[postInfo.id] = session
        log.info("用户: ${postInfo.username} 上线，当前在线人数: ${onlineUserMap.size}")
        //处理离线消息
        offlineMsgMap[postInfo.id]?.forEach {
            val postInfo = map(userInfoRepo.queryById(it.postId)!!)
            postSession.asyncRemote.sendText(
                mapper.writeValueAsString(ChatMsgResp(postInfo, it.msg))
            )
        }
        offlineMsgMap.remove(postInfo.id)
        //通知所有用户
        notifyAllUser(UserChatState.ONLINE.msg, UserChatState.ONLINE.code)
    }

    @OnMessage
    fun onMessage(msgJson: String) {
        log.info("收到客户端消息 ${postInfo.username}: $msgJson")
        val req = mapper.readValue(msgJson, ChatMsgReq::class.java)
        val targetSession = onlineUserMap[req.targetId]
        if(targetSession == null) { // 加入到离线消息中
            val offlineMsgList = offlineMsgMap[req.targetId] ?: mutableListOf()
            offlineMsgList.add(OfflineChatMsg(
                postId = postInfo.id,
                targetId = req.targetId,
                msg = req.msg
            ))
            offlineMsgMap[req.targetId] = offlineMsgList
        } else {
            targetSession.asyncRemote.sendText(
                mapper.writeValueAsString(ChatMsgResp(postInfo, req.msg))
            )
        }
    }

    @OnError
    fun onError(
        session: Session,
        error: Throwable,
    ) {
        log.warn("WebSocket 连接异常！session: $session")
        error.printStackTrace()
    }

    @OnClose
    fun onClose()  {
        onlineUserMap.remove(postInfo.id)
        notifyAllUser(UserChatState.OFFLINE.msg, UserChatState.OFFLINE.code)
    }

    fun notifyAllUser(
        msg: String,
        state: Int,
    ) {
        onlineUserMap.forEach {
            it.value.asyncRemote.sendText(
                mapper.writeValueAsString(ChatMsgResp(
                    postInfo = postInfo,
                    msg = msg,
                    state = state
                ))
            )
        }
    }

    private fun map(o: UserInfo) = with(o) {
        ChatUserInfo (
            id = id,
            username = username,
            avatar = avatar,
        )
    }

    private val log = LoggerFactory.getLogger(ChatRoom::class.java)
}

data class OfflineChatMsg (
    val postId: Long,
    val targetId: Long,
    val msg: String,
)

data class ChatUserInfo (
    val id: Long,
    val username: String,
    val avatar: String,
)


