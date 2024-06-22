package org.cyk.product.service

import org.cyk.base.infra.PageResp
import org.cyk.product.facade.api.PageProductDto
import org.cyk.product.facade.api.ProductVo

interface ProductService {

    fun pageProductVo(dto: PageProductDto): PageResp<ProductVo>

}