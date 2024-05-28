package org.cyk.album

import org.cyk.feign.user.UserInfoServiceRpc
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients


@SpringBootApplication(scanBasePackages = ["org.cyk"])
@EnableFeignClients(basePackages = ["org.cyk"])
class AlbumApplication

fun main(args: Array<String>) {
    runApplication<AlbumApplication>(*args)
}