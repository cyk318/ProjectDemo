package org.cyk.warehouse.repo

import org.cyk.warehouse.repo.impl.WarehouseDo

interface WarehouseRepo {

    fun queryById(id: String): WarehouseDo?

}