package org.cyk.user.repo

import org.cyk.UserinfoProto
import org.cyk.user.domain.Userinfo

interface UserinfoRepo {

    fun save(req: UserinfoProto.RegReq)
    fun saveToken(token: String, id: Long)

    fun queryByUsername(username: String): Userinfo?

}