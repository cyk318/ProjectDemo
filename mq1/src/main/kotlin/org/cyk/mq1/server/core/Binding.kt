package org.cyk.mq1.server.core

data class Binding(
    val exchangeName: String,
    val queueName: String,
    val bindingKey: String,
)
