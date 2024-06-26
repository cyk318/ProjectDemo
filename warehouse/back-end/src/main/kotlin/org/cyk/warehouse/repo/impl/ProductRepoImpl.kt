package org.cyk.warehouse.repo.impl

import org.cyk.warehouse.api.AddProductDto
import org.cyk.warehouse.api.UpdateProductDto
import org.cyk.warehouse.repo.ProductRepo
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.core.remove
import org.springframework.data.mongodb.core.update
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

    override fun save(dto: AddProductDto) {
        val obj: ProductDo = map(dto)
        mongoTemplate.save(obj)
    }

    override fun queryByWarehouseId(id: String): List<ProductDo> {
        val c = Criteria.where("warehouse_id").`is`(id)
        return mongoTemplate.find(Query.query(c), ProductDo::class.java)
    }

    override fun delByWarehouseId(id: String): Long {
        val c = Criteria.where("warehouse_id").`is`(id)
        val result = mongoTemplate.remove(Query.query(c), ProductDo::class.java)
        return result.deletedCount
    }

    override fun delById(id: String): Long {
        val c = Criteria.where("_id").`is`(id)
        val result = mongoTemplate.remove(Query.query(c), ProductDo::class.java)
        return result.deletedCount
    }

    override fun queryAll(): List<ProductDo> {
        return mongoTemplate.findAll(ProductDo::class.java)
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
        u.set("price", dto.price)
        val result = mongoTemplate.updateFirst(Query.query(c), u, ProductDo::class.java)
        return result.modifiedCount
    }

    override fun queryById(id: String): ProductDo? {
        return mongoTemplate.findById(id, ProductDo::class.java)
    }

    private fun map(dto: AddProductDto): ProductDo = with(dto) {
        ProductDo(
            warehouseId = warehouseId,
            name = name,
            description = description,
            price = price,
        )
    }

}