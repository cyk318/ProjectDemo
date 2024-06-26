package org.cyk.user.facade.user

import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService
import org.cyk.UserinfoProto
import org.cyk.UserinfoServiceGrpc.UserinfoServiceImplBase
import org.cyk.user.domain.service.LoginHandler
import org.cyk.user.domain.service.RegHandler

@GrpcService
class UserinfoFacadeImpl(
    private val loginHandler: LoginHandler,
    private val regHandler: RegHandler,
): UserinfoServiceImplBase() {

    override fun login(
        request: UserinfoProto.LoginReq,
        responseObserver: StreamObserver<UserinfoProto.LoginResp>
    ) {
        val r = loginHandler.handler(request)
        responseObserver.onNext(r)
        responseObserver.onCompleted()
    }

    override fun reg(
        request: UserinfoProto.RegReq,
        responseObserver: StreamObserver<UserinfoProto.RegResp>
    ) {
        val r = regHandler.handler(request)
        responseObserver.onNext(r)
        responseObserver.onCompleted()
    }

}