package org.cyk.warehouse

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@SpringBootApplication(scanBasePackages = ["org.cyk"])
class WarehouseApplication {

    @Bean
    fun corsFilter(): CorsFilter {
        val  config = CorsConfiguration();
        config.allowedMethods = listOf("GET", "POST", "PUT", "DELETE");// 支持请求方式
        config.addAllowedOriginPattern("*");// 支持跨域
        config.allowCredentials = true;// cookie
        config.addAllowedHeader("*");// 允许请求头信息
        config.addExposedHeader("*");// 暴露的头部信息

        val source = UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);// 添加地址映射
        return CorsFilter(source);
    }

}

fun main(args: Array<String>) {
    runApplication<WarehouseApplication>(*args)
}
