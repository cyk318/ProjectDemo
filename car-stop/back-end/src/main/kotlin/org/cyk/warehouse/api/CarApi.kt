package org.cyk.warehouse.api

import org.cyk.warehouse.config.ApiResp
import org.cyk.warehouse.config.AppException
import org.cyk.warehouse.repo.CarRepo
import org.cyk.warehouse.repo.ParkingRepo
import org.cyk.warehouse.repo.impl.CarDo
import org.springframework.web.bind.annotation.*



@RestController
@RequestMapping("/product")
class CarApi(
    private val carRepo: CarRepo,
    private val parkingRepo: ParkingRepo,
) {

    @PostMapping("/add")
    fun add(
        @RequestBody dto: AddProductDto
    ): ApiResp<Int> {
        //1.判断对应的库存是否存在
        parkingRepo.queryById(dto.warehouseId) ?: throw AppException("库存不存在，无法添加产品！")
        //2.将产品信息添加到数据
        carRepo.save(dto)
        return ApiResp.ok(1)
    }

    @GetMapping("list")
    fun list(): ApiResp<List<CarDo>> {
        val result = carRepo.queryAll()
        return ApiResp.ok(result)
    }

    @GetMapping("/query/from_warehouse/{id}")
    fun queryFromWarehouse(
        @PathVariable("id") id: String
    ): ApiResp<List<CarDo>> {
        //根据库存 id 查询该库存下的所有产品
        val result = carRepo.queryByWarehouseId(id)
        return ApiResp.ok(result)
    }

    @GetMapping("/del/from_warehouse/{id}")
    fun delFromWarehouse(
        @PathVariable("id") id: String
    ): ApiResp<Long> {
        //根据库存 id 删除该库存下的所有产品
        val result = carRepo.delByWarehouseId(id)
        return ApiResp.ok(result)
    }

    @GetMapping("/del/{id}")
    fun del(
        @PathVariable("id") id: String
    ): ApiResp<Long> {
        val result = carRepo.delById(id)
        return ApiResp.ok(result)
    }

    @PostMapping("/update")
    fun update(
        @RequestBody dto: UpdateProductDto
    ): ApiResp<Long> {
        carRepo.queryById(dto.id) ?: throw AppException("产品 ${dto.id} 不存在！")
        parkingRepo.queryById(dto.warehouseId) ?: throw AppException("仓库 ${dto.warehouseId} 不存在！")
        val result = carRepo.update(dto)
        return ApiResp.ok(result)
    }

}

data class UpdateProductDto(
    val id: String,
    val warehouseId: String,
    val name: String,
    val description: String,
)

data class AddProductDto(
    val id: String, //车辆 id
    val warehouseId: String, //仓库 id
    val name: String,
    val description: String = "暂无描述",
)

//数据库老师化的重点
//第一张
//1. DBA 要干什么

//第二章没什么

//第三章：什么是登录名，什么是用户名(什么关系)
//什么是角色(几种服务器角色)

//什么是架构
//3.8 权限(授权，回收权限) 不光直到概念，还要会用

//什么是数据库安全性
//管理数据库：文件和文件组(概念是重点)  数据库是又几种文件组成(数据文件，日志文件 分别是干什么的)

//新建数据库代码不用管(不用记)
//什么是数据库快照
//什么是 read (例如read0、read1)

//第八章：什么是索引，重点（聚集索引，非聚集索引）
//创建索引(sql 语句要会) 例如 为某一列建索引

//第九章：什么是数据完整性，五个约束: 默认值约束，check约束、主键约束、唯一约束、外键约束...

//什么是视图，创建视图要会

//十一章：重点是触发器（创建存储过程，会建一个基本的触发器）

//十二章：数据库的三种恢复模式。数据库有几种备份的方式（完整备份、差异备份、动态备份、静态备份）

//十三章：事务的特点(4 个特性).  锁不用看了

//第 5、6、7 章：增删改查 sql    tSQL（新建变量、会基本的循环，例如求累加和，阶乘）
