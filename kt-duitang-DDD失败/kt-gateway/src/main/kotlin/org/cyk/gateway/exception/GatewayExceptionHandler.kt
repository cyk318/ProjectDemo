package org.cyk.gateway.exception

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.cyk.base.exception.AppException2
import org.cyk.base.handler.ApiResp
import org.cyk.base.handler.ApiStatus
import org.slf4j.LoggerFactory
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.buffer.DataBufferFactory
import org.springframework.http.MediaType
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Configuration
class GatewayExceptionHandler(private val objectMapper: ObjectMapper) : ErrorWebExceptionHandler {

    private val logger = LoggerFactory.getLogger(GatewayExceptionHandler::class.java)

    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        val apiResp = if (ex is AppException2) {
            ApiResp.no(ApiStatus.TOKEN_EXPIRE)
        } else {
            ApiResp.no(ApiStatus.SERVER_ERROR)
        }

        val response = exchange.response

        if (response.isCommitted) {
            return Mono.error(ex)
        }

        response.headers.contentType = MediaType.APPLICATION_JSON
        logger.error("网关异常: {}", ex.message)

        return response.writeWith(Mono.fromSupplier {
            val bufferFactory: DataBufferFactory = response.bufferFactory()
            try {
                bufferFactory.wrap(objectMapper.writeValueAsBytes(apiResp))
            } catch (e: JsonProcessingException) {
                throw RuntimeException(e)
            }
        })
    }
}
