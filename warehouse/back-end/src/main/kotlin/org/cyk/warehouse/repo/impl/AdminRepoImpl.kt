package org.cyk.warehouse.repo.impl

import org.cyk.warehouse.repo.AdminRepo
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.stereotype.Repository

@Document("admin")
data class Admin(
    @Id
    val id: String,
    val username: String,
    val password: String,
)

@Repository
class AdminRepoImpl(
    private val mongoTemplate: MongoTemplate,
): AdminRepo {



}