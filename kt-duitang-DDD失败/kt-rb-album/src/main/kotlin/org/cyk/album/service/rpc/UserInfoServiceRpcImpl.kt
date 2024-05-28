package org.cyk.album.service.rpc

import com.fasterxml.jackson.databind.ObjectMapper
import org.cyk.feign.user.*
import org.springframework.stereotype.Component
import jakarta.annotation.Resource

@Component
class UserInfoServiceRpcImpl {

    @Resource private lateinit var mapper: ObjectMapper
    @Resource private lateinit var infoServiceRpc: UserInfoServiceRpc

    fun queryByUserIds(ids: List<Long>): List<UserInfoDto> {
        val req = QueryByIdsReq(
            ids = ids
        )
        val json = infoServiceRpc.queryByUserIds(req)
        val resp = mapper.readValue(json, QueryByIdsResp::class.java)
        return resp.infos
    }

    fun queryByUserId(id: Long): UserInfoDto? {
        val req = QueryByIdReq(id = id)
        val json = infoServiceRpc.queryByUserId(req)
        val resp = mapper.readValue(json, QueryByIdResp::class.java)
        return resp.info
    }

}