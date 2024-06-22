package org.cyk.base.design

import org.cyk.base.infra.PageResp

interface QueryCmd<REQ, RESP> {

    fun execute(req: REQ): PageResp<RESP>

}