package org.cyk.warehouse.repo

import org.cyk.warehouse.api.LoginDto


interface AdminRepo {

    fun login(dto: LoginDto): String {

    }

}