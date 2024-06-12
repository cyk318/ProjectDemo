package org.cyk.warehouse

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["org.cyk"])
class WarehouseApplication

fun main(args: Array<String>) {
    runApplication<WarehouseApplication>(*args)
}
