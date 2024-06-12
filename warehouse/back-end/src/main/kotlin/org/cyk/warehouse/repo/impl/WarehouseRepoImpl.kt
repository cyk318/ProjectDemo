package org.cyk.warehouse.repo.impl

import org.cyk.warehouse.api.AddWarehouseDto
import org.cyk.warehouse.repo.WarehouseRepo
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Document("w_warehouse")
data class WarehouseDo(
    @Id
    val id: String,
    val name: String,
    val address: String, //仓库地址
)

/**
 * 仓库(与数据库交互的类)
 */
@Repository
class WarehouseRepoImpl(
    val mongoTemplate: MongoTemplate
): WarehouseRepo {

    override fun save(dto: AddWarehouseDto) {
        val obj: WarehouseDo = map(dto)
        mongoTemplate.save(obj)
    }

    override fun queryById(id: String): WarehouseDo? {
        return mongoTemplate.findById(id, WarehouseDo::class.java)
    }

    override fun queryAll(): List<WarehouseDo> {
        return mongoTemplate.findAll(WarehouseDo::class.java)
    }

    override fun delById(id: String): Long {
        val c = Criteria.where("_id").`is`(id)
        val result =mongoTemplate.remove(Query.query(c), WarehouseDo::class.java)
        return result.deletedCount
    }

    private fun map(dto: AddWarehouseDto): WarehouseDo = with(dto) {
        WarehouseDo(
            id = id,
            name = name,
            address = address,
        )
    }

}
