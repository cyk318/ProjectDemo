package org.cyk.warehouse.api

import org.cyk.warehouse.config.ApiResp
import org.cyk.warehouse.config.AppException
import org.cyk.warehouse.repo.ProductRepo
import org.cyk.warehouse.repo.WarehouseRepo
import org.cyk.warehouse.repo.impl.ProductDo
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/product")
class ProductApi(
    private val productRepo: ProductRepo,
    private val warehouseRepo: WarehouseRepo,
) {

    @PostMapping("/add")
    fun add(
        @RequestBody dto: AddProductDto
    ): ApiResp<Int> {
        //1.判断对应的库存是否存在
        warehouseRepo.queryById(dto.warehouseId) ?: throw AppException("库存不存在，无法添加产品！")
        //2.将产品信息添加到数据
        productRepo.save(dto)
        return ApiResp.ok(1)
    }

    @GetMapping("list")
    fun list(): ApiResp<List<ProductDo>> {
        val result = productRepo.queryAll()
        return ApiResp.ok(result)
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

    @GetMapping("/del/{id}")
    fun del(
        @PathVariable("id") id: String
    ): ApiResp<Long> {
        val result = productRepo.delById(id)
        return ApiResp.ok(result)
    }

}

data class AddProductDto(
    val warehouseId: String, //仓库 id
    val name: String,
    val description: String = "暂无描述",
    val price: Double = 0.0
)
