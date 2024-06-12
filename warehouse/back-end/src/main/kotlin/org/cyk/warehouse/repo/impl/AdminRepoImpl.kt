package org.cyk.warehouse.repo.impl

import org.cyk.warehouse.api.RegDto
import org.cyk.warehouse.repo.AdminRepo
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Document("w_admin")
data class AdminDo(
    @Id
    val id: String? = null,
    val username: String,
    val password: String,
)

@Repository
class AdminRepoImpl(
    private val mongoTemplate: MongoTemplate,
): AdminRepo {

    override fun queryByUsername(username: String): AdminDo? {
        val c = Criteria.where("username").`is`(username)
        return mongoTemplate.findOne(Query.query(c), AdminDo::class.java)
    }

    override fun save(dto: RegDto) {
        val o = map(dto)
        mongoTemplate.save(o)
    }

    override fun queryAll(): List<AdminDo> {
        return mongoTemplate.findAll(AdminDo::class.java)
    }

    private fun map(dto: RegDto) = with(dto) {
        AdminDo (
            username = username,
            password = password,
        )
    }

}