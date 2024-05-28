package org.cyk.mq1.server.core

enum class ExchangeType(
    private val code: Int
) {

    UNKNOWN(0),
    DIRECT(1),
    FANOUT(2),
    TOPIC(3),
    ;

    fun getType(code: Int) = ExchangeType.entries.firstOrNull { it.code == code } ?: UNKNOWN

}