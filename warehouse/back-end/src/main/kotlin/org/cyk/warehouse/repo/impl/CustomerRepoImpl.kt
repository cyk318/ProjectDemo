package org.cyk.warehouse.repo.impl

import org.cyk.warehouse.repo.CustomerRepo
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.Document

@Document("customer")
data class Customer(
    @Id
    val id: String,
    val supplier: String, // 供应商id
    val name: String,
    val phone: String,
)

class CustomerRepoImpl(
    private val mongoTemplate: MongoTemplate,
): CustomerRepo {



}