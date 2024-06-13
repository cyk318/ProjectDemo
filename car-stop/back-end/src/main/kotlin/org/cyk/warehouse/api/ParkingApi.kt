package org.cyk.warehouse.api

import org.cyk.warehouse.config.ApiResp
import org.cyk.warehouse.config.AppException
import org.cyk.warehouse.repo.CarRepo
import org.cyk.warehouse.repo.ParkingRepo
import org.cyk.warehouse.repo.impl.ParkingDo
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/warehouse")
class ParkingApi(
    private val parkingRepo: ParkingRepo,
    private val carRepo: CarRepo,
) {

    @PostMapping("/add")
    fun add(
        @RequestBody dto: AddWarehouseDto
    ): ApiResp<Int> {
        //将仓库信息保存到数据库中
        parkingRepo.save(dto)
        return ApiResp.ok(1)
    }

    @GetMapping("/list")
    fun list(): ApiResp<List<ParkingDo>> {
        //从数据库中查询所有仓库信息
        val result = parkingRepo.queryAll()
        return ApiResp.ok(result)
    }

    @GetMapping("/del/{id}")
    fun del(
        @PathVariable("id") id: String
    ): ApiResp<Long> {
        //1.先删除该仓库下的所有产品信息
        val result1 = carRepo.delByWarehouseId(id)
        //2.再删除仓库信息
        val result2 = parkingRepo.delById(id)
        return ApiResp.ok(result1 + result2)
    }

    @PostMapping("/update")
    fun update(
        @RequestBody dto: UpdateWarehouseDto,
    ): ApiResp<Long> {
        parkingRepo.queryById(dto.id) ?: throw AppException("仓库 ${dto.id} 不存在！")
        val result = parkingRepo.update(dto)
        return ApiResp.ok(result)
    }

}

data class UpdateWarehouseDto(
    val id: String,
    val name: String,
    val address: String,
)

data class AddWarehouseDto (
    val id: String,
    val name: String,
    val address: String, //仓库地址
)