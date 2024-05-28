package org.cyk.mq1.server.core

import com.fasterxml.jackson.databind.ObjectMapper
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "exchange")
data class Exchange(
    @Id
    val name: String,
    val type: ExchangeType = ExchangeType.DIRECT,
    val durable: Boolean = false,
    val autoDelete: Boolean = false,
    private var arguments: String,
)

