package org.cyk.album.infra.config.mybatis

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor
import com.baomidou.mybatisplus.annotation.DbType

@Configuration
class MybatisConfig {

    @Bean
    fun mybatisPlusInterceptor(): MybatisPlusInterceptor {
        // 初始化核心插件
        val interceptor = MybatisPlusInterceptor()
        // 创建分页插件
        val page = PaginationInnerInterceptor(DbType.MYSQL)
        // 设置分页上限
        page.maxLimit = 25L
        // 添加分页插件到 MybatisPlus
        interceptor.addInnerInterceptor(page)
        return interceptor
    }
}
