package org.cyk.warehouse.repo.impl

import org.cyk.warehouse.repo.WarehouseRepo
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.stereotype.Repository


@Document("w_warehouse")
data class WarehouseDo(
    @Id
    val id: String? = null,
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



}