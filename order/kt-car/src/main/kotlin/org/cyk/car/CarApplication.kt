package org.cyk.car

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class CarApplication
fun main(args: Array<String>) {
    runApplication<CarApplication>(*args)
}