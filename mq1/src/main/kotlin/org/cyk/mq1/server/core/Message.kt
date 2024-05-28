package org.cyk.mq1.server.core

data class Message(
    val basicProperties: BasicProperties,
    val offsetBeg: Long = 0,
    val offsetEnd: Long = 0,
    val isValid: Boolean = true,
)

data class BasicProperties(
    val messageId: String,
    val routingKey: String,
    val durable: Boolean = false
)