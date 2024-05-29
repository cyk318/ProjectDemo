package org.cyk.operator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OperatorApplication

fun main(args: Array<String>) {
    runApplication<OperatorApplication>(*args)
}