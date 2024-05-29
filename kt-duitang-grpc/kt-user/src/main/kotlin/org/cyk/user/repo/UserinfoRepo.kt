package org.cyk.user.repo

import org.cyk.UserinfoProto
import org.cyk.user.repo.impl.Userinfo

interface UserinfoRepo {

    fun save(req: UserinfoProto.RegReq)
    fun saveToken(token: String, id: String)
    fun queryByUsername(username: String): Userinfo?

}