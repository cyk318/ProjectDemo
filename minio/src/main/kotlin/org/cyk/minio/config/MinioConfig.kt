package org.cyk.minio.config

import io.minio.MinioClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MinioConfig {

    @Bean
    fun minioClient(): MinioClient = MinioClient.builder()
        .endpoint("http://100.105.180.32:9001") //格式必须是 http://ip:port  注意: 这里使用的 9001 端口，而不是 9000
        .credentials("root", "rootroot") //用户名 和 密码
        .build()

}