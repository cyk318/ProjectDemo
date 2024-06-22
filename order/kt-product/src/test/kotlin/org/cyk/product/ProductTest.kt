package org.cyk.product

import jakarta.annotation.Resource
import org.cyk.product.repo.ProductRepo
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ProductTest {

    @Resource
    private lateinit var productRepo: ProductRepo

    @Test
    fun test1() {
        val result = productRepo.queryProductInfoById(1641011011005L)
        println(result)
    }

}