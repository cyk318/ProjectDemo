package org.cyk.user.facade.user

import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService
import org.cyk.UserinfoProto
import org.cyk.UserinfoServiceGrpc.UserinfoServiceImplBase

@GrpcService
class UserinfoFacadeImpl: UserinfoServiceImplBase() {

    override fun login(
        request: UserinfoProto.LoginReq,
        responseObserver: StreamObserver<UserinfoProto.LoginResp>
    ) {
        super.login(request, responseObserver)
    }

    override fun reg(
        request: UserinfoProto.RegReq,
        responseObserver: StreamObserver<UserinfoProto.RegResp>
    ) {
        super.reg(request, responseObserver)
    }

}