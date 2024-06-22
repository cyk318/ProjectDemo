package org.cyk.product.repo.impl

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import com.baomidou.mybatisplus.extension.kotlin.KtQueryChainWrapper
import org.apache.ibatis.annotations.Mapper
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
