package org.cyk.warehouse.api

import org.cyk.warehouse.config.ApiResp
import org.cyk.warehouse.repo.WarehouseRepo
import org.cyk.warehouse.repo.impl.WarehouseDo
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/warehouse")
class WarehouseApi(
    private val warehouseRepo: WarehouseRepo,
) {

    @PostMapping("/add")
    fun add(
        @RequestBody dto: AddWarehouseDto
    ): ApiResp<Int> {
        //将仓库信息保存到数据库中
        warehouseRepo.save(dto)
        return ApiResp.ok(1)
    }

    @GetMapping("/list")
    fun list(): ApiResp<List<WarehouseDo>> {
        //从数据库中查询所有仓库信息
        val result = warehouseRepo.queryAll()
        return ApiResp.ok(result)
    }

}

data class AddWarehouseDto (
    val id: String,
    val name: String,
    val address: String, //仓库地址
)