package org.cyk.warehouse.repo

import org.cyk.warehouse.api.RegDto
import org.cyk.warehouse.api.UpdateAdminDto
import org.cyk.warehouse.repo.impl.AdminDo


interface AdminRepo {

    fun queryByUsername(username: String): AdminDo?
    fun save(dto: RegDto)
    fun queryAll(): List<AdminDo>
    fun del(id: String): Long
    fun update(dto: UpdateAdminDto): Long
    fun queryById(id: String): AdminDo?

}