package org.cyk.order.repo.impl

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.cyk.order.repo.OrderRepo
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

interface OrderInfoMapper: BaseMapper<OrderInfo>
interface OrderProductRelationMapper: BaseMapper<OrderProductRelation>

@TableName("ord_info")
data class OrderInfo (
    @TableId
    val id: Long? = null,
    val payChannel: Int,
    val postUserId: Long,
    val status: Int,
    val cTime: LocalDateTime = LocalDateTime.now(),
    val uTime: LocalDateTime = LocalDateTime.now(),
)

@TableName("ord_product_relation")
data class OrderProductRelation(
    val orderId: Long,
    val productId: Long,
)

@Repository
class OrderRepoImpl(
    private val orderInfoMapper: OrderInfoMapper,
    private val orderProductRelationMapper: OrderProductRelationMapper
): OrderRepo {

}