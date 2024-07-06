package org.cyk.minio

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MinioApplication

fun main(args: Array<String>) {
    runApplication<MinioApplication>(*args)
}
