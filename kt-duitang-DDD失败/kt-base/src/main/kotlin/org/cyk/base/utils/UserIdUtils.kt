package org.cyk.base.utils

import org.cyk.base.model.HeaderConst
import org.cyk.base.model.JwtConst
import jakarta.servlet.http.HttpServletRequest

object UserIdUtils {

    fun getId(httpServletRequest: HttpServletRequest): Long {
        val token = httpServletRequest.getHeader(HeaderConst.TOKEN)
        val userId = JwtUtils.getTokenInfo(token).getClaim(JwtConst.USER_ID).asLong()
        return userId
    }

}