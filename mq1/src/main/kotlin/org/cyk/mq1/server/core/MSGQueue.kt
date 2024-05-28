package org.cyk.mq1.server.core

data class MSGQueue(
    val name: String,
    val durable: Boolean = false,
    val exclusive: Boolean = false,
    val autoDelete: Boolean = false,
    val arguments: MutableMap<String, Any> = mutableMapOf()
)
