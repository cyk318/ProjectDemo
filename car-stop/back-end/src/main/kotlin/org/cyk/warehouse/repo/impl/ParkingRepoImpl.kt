package org.cyk.warehouse.repo.impl

import org.cyk.warehouse.api.AddWarehouseDto
import org.cyk.warehouse.api.UpdateWarehouseDto
import org.cyk.warehouse.repo.ParkingRepo
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository

@Document("c_parking")
data class ParkingDo(
    @Id
    val id: String,
    val name: String,
    val address: String, //停车场地址地址
)

/**
 * 停车场(与数据库交互的类)
 */
@Repository
class ParkingRepoImpl(
    val mongoTemplate: MongoTemplate
): ParkingRepo {

    override fun save(dto: AddWarehouseDto) {
        val obj: ParkingDo = map(dto)
        mongoTemplate.save(obj)
    }

    override fun queryById(id: String): ParkingDo? {
        return mongoTemplate.findById(id, ParkingDo::class.java)
    }

    override fun queryAll(): List<ParkingDo> {
        return mongoTemplate.findAll(ParkingDo::class.java)
    }

    override fun delById(id: String): Long {
        val c = Criteria.where("_id").`is`(id)
        val result = mongoTemplate.remove(Query.query(c), ParkingDo::class.java)
        return result.deletedCount
    }

    override fun update(dto: UpdateWarehouseDto): Long {
        val c = Criteria.where("_id").`is`(dto.id)
        val u = Update()
        if (dto.name.isNotBlank()) {
            u.set("name", dto.name)
        }
        if (dto.address.isNotBlank()) {
            u.set("address", dto.address)
        }
        val result = mongoTemplate.updateFirst(Query.query(c), u, ParkingDo::class.java)
        return result.modifiedCount
    }

    private fun map(dto: AddWarehouseDto): ParkingDo = with(dto) {
        ParkingDo(
            id = id,
            name = name,
            address = address,
        )
    }

}
