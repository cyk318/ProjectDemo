package org.cyk.common

class AppException(
    val status: ApiStatus,
    val log: String
) : RuntimeException(status.msg)
