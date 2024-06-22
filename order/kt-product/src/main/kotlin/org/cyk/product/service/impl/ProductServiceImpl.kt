package org.cyk.product.service.impl

import org.cyk.base.infra.PageResp
import org.cyk.product.facade.api.PageProductDto
import org.cyk.product.facade.api.ProductVo
import org.cyk.product.repo.ProductRepo
import org.cyk.product.service.ProductService
import org.cyk.product.service.cmd.PageProductVoCmd
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl(
    private val pageProductVoCmd: PageProductVoCmd,
): ProductService {

    override fun pageProductVo(dto: PageProductDto): PageResp<ProductVo> {
        return pageProductVoCmd.execute(dto)
    }

}