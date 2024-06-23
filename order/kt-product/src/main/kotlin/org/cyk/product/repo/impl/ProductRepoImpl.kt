package org.cyk.product.repo.impl

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import org.apache.ibatis.annotations.Mapper
import org.cyk.base.infra.PageResp
import org.cyk.product.facade.api.PageProductDto
import org.cyk.product.repo.ProductRepo
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.time.LocalDateTime

@Mapper
interface ProductInfoMapper: BaseMapper<ProductInfoDo>
@Mapper
interface ProductStoreMapper: BaseMapper<ProductStoreDo>

@TableName("prod_info")
data class ProductInfoDo (
    @TableId
    val id: Long? = null,
    val storeId: Long,
    val title: String,
    val description: String,
    val price: BigDecimal,
    val count: Int,
    val cTime: LocalDateTime,
    val uTime: LocalDateTime,
)

@TableName("prod_store")
data class ProductStoreDo (
    @TableId
    val id: Long? = null,
    val name: String,
    val avatar: String = "www.store.com",
    val cTime: LocalDateTime,
    val uTime: LocalDateTime,
)

@Repository
class ProductRepoImpl: ProductRepo {

    override fun queryProductInfoById(id: Long): ProductInfo? {
        val q = KtQueryChainWrapper(ProductInfoDo::class.java)
        return q.eq(ProductInfoDo::id, id)
            .one()
            ?.let { map(it) }
    }

    override fun pageProductInfo(req: PageProductDto): PageResp<ProductInfo> {
        val pageResult = KtQueryChainWrapper(ProductInfoDo::class.java)
            .orderByDesc(ProductInfoDo::cTime)
            .page(Page.of(req.start, req.limit))
        val result = pageResult.records.map(::map).toMutableList()
        return PageResp.ok(
            pageResult.hasNext(),
            req.start + 1,
            result,
            pageResult.total
        )
    }

    override fun queryStoreByIds(storeIds: List<Long>): List<ProductStore> {
         return KtQueryChainWrapper(ProductStoreDo::class.java)
            .`in`(ProductStoreDo::id, storeIds)
            .list()
             .map(::map)
    }


    private fun map(o: ProductInfoDo) = with(o) {
        ProductInfo(
            id = id!!,
            storeId = storeId,
            title = title,
            description = description,
            price = price,
            count = count,
            cTime = cTime,
            uTime = uTime,
        )
    }

    private fun map(o: ProductStoreDo) = with(o) {
        ProductStore(
            id = id!!,
            name = name,
            avatar = avatar,
            cTime = cTime,
            uTime = uTime
        )
    }

}

data class ProductInfo (
    val id: Long,
    val storeId: Long,
    val title: String,
    val description: String,
    val price: BigDecimal,
    val count: Int,
    val cTime: LocalDateTime,
    val uTime: LocalDateTime,
)

data class ProductStore (
    val id: Long,
    val name: String,
    val avatar: String,
    val cTime: LocalDateTime,
    val uTime: LocalDateTime,
)
