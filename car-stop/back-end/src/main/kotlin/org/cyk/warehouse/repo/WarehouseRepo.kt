package org.cyk.warehouse.repo

import org.cyk.warehouse.api.AddWarehouseDto
import org.cyk.warehouse.api.UpdateWarehouseDto
import org.cyk.warehouse.repo.impl.WarehouseDo

interface WarehouseRepo {

    fun save(dto: AddWarehouseDto)

    fun queryById(id: String): WarehouseDo?
    fun queryAll(): List<WarehouseDo>
    fun delById(id: String): Long
    fun update(dto: UpdateWarehouseDto): Long


}