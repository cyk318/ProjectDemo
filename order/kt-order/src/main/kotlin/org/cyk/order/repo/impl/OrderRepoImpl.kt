package org.cyk.order.repo.impl

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import com.baomidou.mybatisplus.core.mapper.BaseMapper
import org.cyk.order.repo.OrderRepo
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

interface OrderInfoMapper: BaseMapper<OrderInfoDo>
interface OrderProductRelationMapper: BaseMapper<OrderProductRelationDo>

@TableName("ord_info")
data class OrderInfoDo (
    @TableId
    val id: Long? = null,
    val payChannel: Int, //支付方式 0微信 1支付宝
    val postUserId: Long, //订单状态 0待发货 1待收获 2已收获
    val status: Int = 0,
    val cTime: LocalDateTime = LocalDateTime.now(),
    val uTime: LocalDateTime = LocalDateTime.now(),
)

@TableName("ord_product_relation")
data class OrderProductRelationDo(
    val orderId: Long,
    val productId: Long,
)

@Repository
class OrderRepoImpl: OrderRepo {

}