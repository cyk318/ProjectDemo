package org.cyk.operator.infra.config
import io.grpc.ManagedChannelBuilder
import org.cyk.UserinfoServiceGrpc
import org.cyk.UserinfoServiceGrpc.UserinfoServiceBlockingStub
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GrpcConfig {

    @Value("\${grpc-client.host}")
    private var host: String = ""

    @Value("\${grpc-client.port}")
    private var port: Int = -1

    @Bean
    fun userinfoServiceBlockingStub(): UserinfoServiceBlockingStub {
        val managedChannel = ManagedChannelBuilder
            .forAddress(host, port)
            .usePlaintext()
            .build()
        return UserinfoServiceGrpc.newBlockingStub(managedChannel)
    }

}
