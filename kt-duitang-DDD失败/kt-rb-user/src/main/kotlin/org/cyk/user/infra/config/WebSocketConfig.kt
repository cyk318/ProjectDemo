package org.cyk.user.infra.config
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry
import org.springframework.web.socket.server.standard.ServerEndpointExporter

/**
 * 注入对象 ServerEndpointExporter
 * 这个 bean 会自动注册使用了 @ServerEndpoint 注解声明的 WebSocket endpoint
 */

@Configuration
class WebSocketConfig: WebSocketConfigurer {

    @Bean
    fun serverEndpointExporter() = ServerEndpointExporter()
    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
    }

}
