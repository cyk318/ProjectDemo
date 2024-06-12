package org.cyk.warehouse.repo.impl

import org.cyk.warehouse.repo.SupplierRepo
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.stereotype.Repository


@Document("supplier")
data class Supplier(
    @Id
    val id: String,
    val customerId: String, // 客户 id
    val name: String,
    val phone: String,
)

@Repository
class SupplierRepoImpl(
    private val mongoTemplate: MongoTemplate,
): SupplierRepo {
}