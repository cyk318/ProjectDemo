package org.cyk.warehouse.api

import org.cyk.warehouse.config.ApiResp
import org.cyk.warehouse.repo.WarehouseRepo
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

}

data class AddWarehouseDto (
    val id: String,
    val name: String,
    val address: String, //仓库地址
)