package org.cyk.warehouse.repo

import org.cyk.warehouse.api.AddWarehouseDto
import org.cyk.warehouse.api.UpdateWarehouseDto
import org.cyk.warehouse.repo.impl.ParkingDo

interface ParkingRepo {

    fun save(dto: AddWarehouseDto)

    fun queryById(id: String): ParkingDo?
    fun queryAll(): List<ParkingDo>
    fun delById(id: String): Long
    fun update(dto: UpdateWarehouseDto): Long


}