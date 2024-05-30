package org.cyk.common

class AppException(
    status: ApiStatus,
    val log: String
) : RuntimeException(status.msg)
