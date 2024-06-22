package org.cyk.product

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class ProductApplication
fun main(args: Array<String>) {
    runApplication<ProductApplication>(*args)
}