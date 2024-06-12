package org.cyk.warehouse.config

class AppException(
    val log: String
) : RuntimeException(log)
