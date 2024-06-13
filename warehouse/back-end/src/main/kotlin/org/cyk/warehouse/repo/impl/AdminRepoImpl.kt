package org.cyk.warehouse.repo.impl

import org.cyk.warehouse.api.RegDto
import org.cyk.warehouse.api.UpdateDto
import org.cyk.warehouse.repo.AdminRepo
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.core.query.UpdateDefinition
import org.springframework.data.mongodb.core.update
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

    override fun del(id: String): Long {
        val c = Criteria.where("_id").`is`(id)
        val result = mongoTemplate.remove(Query.query(c), AdminDo::class.java)
        return result.deletedCount
    }

    override fun update(dto: UpdateDto): Long {
        val c = Criteria.where("_id").`is`(dto.id)
        val u = Update()
        if (dto.username.isNotBlank()) {
            u.set("username", dto.username)
        }
        if (dto.password.isNotBlank()) {
            u.set("password", dto.password)
        }
        val result = mongoTemplate.updateFirst(Query.query(c), u, AdminDo::class.java)
        return result.modifiedCount
    }

    private fun map(dto: RegDto) = with(dto) {
        AdminDo (
            username = username,
            password = password,
        )
    }

}