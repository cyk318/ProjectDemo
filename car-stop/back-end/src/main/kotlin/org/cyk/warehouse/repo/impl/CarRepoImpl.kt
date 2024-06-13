package org.cyk.warehouse.repo.impl

import org.cyk.warehouse.api.AddProductDto
import org.cyk.warehouse.api.UpdateProductDto
import org.cyk.warehouse.repo.CarRepo
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository

@Document("c_car")
data class CarDo(
    @Id
    val id: String? = null,
    val warehouseId: String, //停车场Id
    val name: String,
    val description: String = "暂无描述",
)

@Repository
class CarRepoImpl(
    private val mongoTemplate: MongoTemplate,
):CarRepo {

    override fun save(dto: AddProductDto) {
        val obj: CarDo = map(dto)
        mongoTemplate.save(obj)
    }

    override fun queryByWarehouseId(id: String): List<CarDo> {
        val c = Criteria.where("warehouse_id").`is`(id)
        return mongoTemplate.find(Query.query(c), CarDo::class.java)
    }

    override fun delByWarehouseId(id: String): Long {
        val c = Criteria.where("warehouse_id").`is`(id)
        val result = mongoTemplate.remove(Query.query(c), CarDo::class.java)
        return result.deletedCount
    }

    override fun delById(id: String): Long {
        val c = Criteria.where("_id").`is`(id)
        val result = mongoTemplate.remove(Query.query(c), CarDo::class.java)
        return result.deletedCount
    }

    override fun queryAll(): List<CarDo> {
        return mongoTemplate.findAll(CarDo::class.java)
    }

    override fun update(dto: UpdateProductDto): Long {
        val c = Criteria.where("_id").`is`(dto.id)
        val u = Update()
        if (dto.warehouseId.isNotBlank()) {
            u.set("warehouse_id", dto.warehouseId)
        }
        if (dto.warehouseId.isNotBlank()) {
            u.set("name", dto.name)
        }
        if (dto.description.isNotBlank()) {
            u.set("description", dto.description)
        }
        val result = mongoTemplate.updateFirst(Query.query(c), u, CarDo::class.java)
        return result.modifiedCount
    }

    override fun queryById(id: String): CarDo? {
        return mongoTemplate.findById(id, CarDo::class.java)
    }

    private fun map(dto: AddProductDto): CarDo = with(dto) {
        CarDo(
            id = id,
            warehouseId = warehouseId,
            name = name,
            description = description,
        )
    }

}