package org.cyk.user.infra.config

import io.grpc.*
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor

@GrpcGlobalServerInterceptor
class GlobalGrpcExceptionHandler : ServerInterceptor {

    override fun <ReqT : Any?, RespT : Any?> interceptCall(
        p0: ServerCall<ReqT, RespT>,
        p1: io.grpc.Metadata,
        p2: ServerCallHandler<ReqT, RespT>,
    ): ServerCall.Listener<ReqT> {
        val delegate = p2.startCall(p0, p1)
        return object : ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(delegate) {
            override fun onHalfClose() {
                try {
                    super.onHalfClose()
                } catch (e: Exception) {
                    p0.close(
                        Status.INTERNAL.withCause(e).withDescription(e.message),
                        Metadata()
                    )
                }
            }
        }
    }

}
