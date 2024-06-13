package org.cyk.warehouse.repo

import org.cyk.warehouse.api.AddProductDto
import org.cyk.warehouse.api.UpdateProductDto
import org.cyk.warehouse.repo.impl.ProductDo

interface ProductRepo {

    fun save(dto: AddProductDto)

    fun queryByWarehouseId(id: String): List<ProductDo>

    fun delByWarehouseId(id: String): Long
    fun delById(id: String): Long
    fun queryAll(): List<ProductDo>
    fun update(dto: UpdateProductDto): Long

}