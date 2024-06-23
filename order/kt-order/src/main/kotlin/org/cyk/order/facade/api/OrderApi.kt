package org.cyk.order.facade.api

import org.cyk.base.infra.ApiResp
import org.cyk.order.service.OrderService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/order")
class OrderApi(
    private val orderService: OrderService,
) {

    @PostMapping("/create")
    fun create(
        @RequestBody dto: CreateOrderDto,
    ): ApiResp<Int> {

    }

}

data class CreateOrderDto (
    val productId: List<Long>,
    val payChannel: Int,
    val postUserId: Long,
)

