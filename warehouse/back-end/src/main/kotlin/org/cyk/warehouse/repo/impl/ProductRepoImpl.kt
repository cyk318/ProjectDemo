package org.cyk.warehouse.repo.impl

import org.cyk.warehouse.repo.ProductRepo
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.remove
import org.springframework.stereotype.Repository

@Document("w_product")
data class ProductDo(
    @Id
    val id: String? = null,
    val warehouseId: String, //仓库 id
    val name: String,
    val description: String = "暂无描述",
    val price: Double = 0.0
)

@Repository
class ProductRepoImpl(
    private val mongoTemplate: MongoTemplate,
):ProductRepo {

    override fun queryByWarehouseId(id: String): List<ProductDo> {
        val c = Criteria.where("warehouse_id").`is`(id)
        return mongoTemplate.find(Query.query(c), ProductDo::class.java)
    }

    override fun delByWarehouseId(id: String): Long {
        val c = Criteria.where("warehouse_id").`is`(id)
        val result = mongoTemplate.remove(Query.query(c), ProductDo::class.java)
        return result.deletedCount
    }

}