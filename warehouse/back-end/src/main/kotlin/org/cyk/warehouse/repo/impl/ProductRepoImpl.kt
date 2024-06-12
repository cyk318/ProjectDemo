package org.cyk.warehouse.repo.impl

import org.cyk.warehouse.repo.ProductRepo
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.stereotype.Repository

@Document("w_product")
data class ProductDo(
    @Id
    val id: String,
    val warehouseId: String, //仓库 id
    val supplierId: Int, // 供应商 id
    val name: String,
    val description: String = "暂无描述",
    val price: Double = 0.0
)

@Repository
class ProductRepoImpl(
    private val mongoTemplate: MongoTemplate,
):ProductRepo {


}