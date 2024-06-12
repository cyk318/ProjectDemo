package org.cyk.warehouse.api

import org.cyk.warehouse.config.ApiResp
import org.cyk.warehouse.repo.ProductRepo
import org.cyk.warehouse.repo.impl.ProductDo
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/product")
class ProductApi(
    private val productRepo: ProductRepo
) {

    @GetMapping("/query/from_warehouse/{id}")
    fun queryFromWarehouse(
        @PathVariable("id") id: String
    ): ApiResp<List<ProductDo>> {
        val result = productRepo.queryByWarehouseId(id)
        return ApiResp.ok(result)
    }

    @GetMapping("/del/from_warehouse/{id}")
    fun delFromWarehouse(
        @PathVariable("id") id: String
    ): ApiResp<Long> {
        val result = productRepo.delByWarehouseId(id)
        return ApiResp.ok(result)
    }

}