package org.cyk.warehouse.api

import org.cyk.warehouse.config.ApiResp
import org.cyk.warehouse.repo.ProductRepo
import org.cyk.warehouse.repo.impl.ProductDo
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/product")
class ProductApi(
    private val productRepo: ProductRepo
) {

    @PostMapping("/add")
    fun add(dto: AddProductDto): ApiResp<Int> {
        //将产品信息添加到数据
        productRepo.save(dto)
        return ApiResp.ok(1)
    }

    @GetMapping("/query/from_warehouse/{id}")
    fun queryFromWarehouse(
        @PathVariable("id") id: String
    ): ApiResp<List<ProductDo>> {
        //根据库存 id 查询该库存下的所有产品
        val result = productRepo.queryByWarehouseId(id)
        return ApiResp.ok(result)
    }

    @GetMapping("/del/from_warehouse/{id}")
    fun delFromWarehouse(
        @PathVariable("id") id: String
    ): ApiResp<Long> {
        //根据库存 id 删除该库存下的所有产品
        val result = productRepo.delByWarehouseId(id)
        return ApiResp.ok(result)
    }

}

data class AddProductDto(
    val warehouseId: String, //仓库 id
    val name: String,
    val description: String = "暂无描述",
    val price: Double = 0.0
)
