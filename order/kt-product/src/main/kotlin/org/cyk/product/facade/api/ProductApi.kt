package org.cyk.product.facade.api

import org.cyk.base.infra.ApiResp
import org.cyk.base.infra.PageResp
import org.cyk.product.service.ProductService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal
import java.time.LocalDateTime

@RestController
@RequestMapping("/product")
class ProductApi(
    private val productService: ProductService
) {

    @GetMapping("/page")
    fun page(
        @RequestParam(defaultValue = "1") start: Long,
        @RequestParam(defaultValue = "10") limit: Long,
    ): ApiResp<PageResp<ProductVo>> {
        val dto = PageProductDto (
            start = start,
            limit = limit,
        )
        val result = productService.pageProductVo(dto)
        return ApiResp.ok(result)
    }

}

data class PageProductDto (
    val start: Long,
    val limit: Long,
)

data class ProductVo (
    val storeVo: ProductStoreVo,
    val infoVo: ProductInfoVo,
) {
    data class ProductStoreVo (
        val id: Long,
        val name: String,
        val avatar: String,
    )
    data class ProductInfoVo (
        val id: Long,
        val title: String,
        val description: String,
        val price: BigDecimal,
        val count: Int,
        val cTime: LocalDateTime,
        val uTime: LocalDateTime,
    )
}


