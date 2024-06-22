package org.cyk.product.repo

import org.cyk.product.repo.impl.ProductInfo

interface ProductRepo {

    fun queryProductInfoById(id: Long): ProductInfo?

}