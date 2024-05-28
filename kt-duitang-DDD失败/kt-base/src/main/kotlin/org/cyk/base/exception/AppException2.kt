package org.cyk.base.exception

import org.cyk.base.handler.ApiResp
import org.cyk.base.handler.ApiStatus

/**
 * 自定义异常
 */
class AppException2(val errorResult: ApiResp<*>) : RuntimeException(errorResult.msg)

class AppException(
    val status: ApiStatus,
    val log: String
) : RuntimeException(status.msg)

