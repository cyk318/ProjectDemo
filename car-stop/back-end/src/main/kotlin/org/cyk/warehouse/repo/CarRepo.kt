package org.cyk.warehouse.repo

import org.cyk.warehouse.api.AddProductDto
import org.cyk.warehouse.api.UpdateProductDto
import org.cyk.warehouse.repo.impl.CarDo

interface CarRepo {

    fun save(dto: AddProductDto)

    fun queryByWarehouseId(id: String): List<CarDo>

    fun delByWarehouseId(id: String): Long
    fun delById(id: String): Long
    fun queryAll(): List<CarDo>
    fun update(dto: UpdateProductDto): Long
    fun queryById(id: String): CarDo?

}