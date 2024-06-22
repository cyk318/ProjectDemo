package org.cyk.product.repo

import org.cyk.base.infra.PageResp
import org.cyk.product.facade.api.PageProductDto
import org.cyk.product.repo.impl.ProductInfo
import org.cyk.product.repo.impl.ProductStore
import org.cyk.product.repo.impl.ProductStoreDo

interface ProductRepo {

    fun queryProductInfoById(id: Long): ProductInfo?
    fun pageProductInfo(req: PageProductDto): PageResp<ProductInfo>
    fun queryStoreByIds(storeIds: List<Long>): List<ProductStore>

}