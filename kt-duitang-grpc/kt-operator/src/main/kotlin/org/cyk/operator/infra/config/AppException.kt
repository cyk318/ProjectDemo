package org.cyk.operator.infra.config

class AppException(
    val status: ApiStatus,
    val log: String
) : RuntimeException(status.msg)
