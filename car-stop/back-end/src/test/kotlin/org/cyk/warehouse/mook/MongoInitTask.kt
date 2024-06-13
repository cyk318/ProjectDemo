package org.cyk.warehouse.mook

import jakarta.annotation.Resource
import org.cyk.warehouse.repo.impl.CarDo
import org.cyk.warehouse.repo.impl.ParkingDo
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate

@SpringBootTest
class MongoInitTask {

    @Resource
    private lateinit var mongoTemplate: MongoTemplate

    @Test
    fun init() {
        val warehouse1 = ParkingDo(
            id = "WH001",
            name = "一号仓库",
            address = "北京市朝阳区仓库路1号"
        )

        val warehouse2 = ParkingDo(
            id = "WH002",
            name = "二号仓库",
            address = "上海市浦东新区仓库路2号"
        )

        val product1 = CarDo(
            id = "PRD001", // 为产品指定一个id
            warehouseId = warehouse1.id!!, // 使用仓库1的ID（确保不是null）
            name = "产品A",
            description = "高品质产品A",
        )

        val product2 = CarDo(
            id = "PRD002", // 为产品指定一个id
            warehouseId = warehouse1.id!!, // 假设这个产品也存放在仓库1
            name = "产品B",
            description = "与产品A配套使用",
        )

        val product3 = CarDo(
            id = "PRD003", // 为产品指定一个id
            warehouseId = warehouse2.id!!, // 这个产品存放在仓库2
            name = "产品C",
        )

        val warehouseList = mutableListOf(warehouse1, warehouse2)
        val productList = mutableListOf(product1,product2,product3)

        mongoTemplate.insertAll(warehouseList)
        mongoTemplate.insertAll(productList)
    }

}