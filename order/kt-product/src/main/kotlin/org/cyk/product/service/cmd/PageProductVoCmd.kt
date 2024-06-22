package org.cyk.product.service.cmd

import org.cyk.base.design.QueryCmd
import org.cyk.base.infra.PageResp
import org.cyk.product.facade.api.PageProductDto
import org.cyk.product.facade.api.ProductVo
import org.cyk.product.repo.ProductRepo
import org.cyk.product.repo.impl.ProductInfo
import org.cyk.product.repo.impl.ProductStore
import org.springframework.stereotype.Component

@Component
class PageProductVoCmd(
    private val productRepo: ProductRepo,
): QueryCmd<PageProductDto, ProductVo> {

    override fun execute(req: PageProductDto): PageResp<ProductVo> {
        val infos = productRepo.pageProductInfo(req)
        val storeIds = infos.result.map(ProductInfo::storeId)

        val stores = productRepo.queryStoreByIds(storeIds)

        val result = infos.result.map { map(it, stores) }
        return PageResp.ok(
            infos.hasMore,
            infos.nextStart,
            result,
            infos.total,
        )
    }

    private fun map(
        info: ProductInfo,
        stores: List<ProductStore>
    ): ProductVo {
        val store = stores.first { it.id == info.storeId }
        return ProductVo(
            storeVo = with(store) {
                ProductVo.ProductStoreVo(
                    id = id,
                    name = name,
                    avatar = avatar,
                )
            },
            infoVo = with(info) {
                ProductVo.ProductInfoVo(
                    id = id,
                    title = title,
                    description = description,
                    price = price,
                    count = count,
                    cTime = cTime,
                    uTime = uTime,
                )
            }
        )
    }

}