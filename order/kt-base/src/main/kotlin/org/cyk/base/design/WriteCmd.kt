package org.cyk.base.design

interface WriteCmd<REQ> {

    fun execute(req: REQ)

}