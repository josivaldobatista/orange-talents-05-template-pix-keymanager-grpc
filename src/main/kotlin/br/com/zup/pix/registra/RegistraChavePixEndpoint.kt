package br.com.zup.pix.registra

import br.com.zup.ChavePixRequest
import br.com.zup.ChavePixResponse
import br.com.zup.RegistroChavePixGrpc
import br.com.zup.compartilhada.grpc.ErrorHandler
import br.com.zup.pix.ChavePixRepository
import io.grpc.stub.StreamObserver
import javax.inject.Inject
import javax.inject.Singleton

@ErrorHandler
@Singleton
class RegistraChavePixEndpoint(
  @Inject val chavePixRepository: ChavePixRepository,
  @Inject val service: NovaChavePixService
) :
  RegistroChavePixGrpc.RegistroChavePixImplBase() {

  override fun registrar(
    request: ChavePixRequest,
    responseObserver: StreamObserver<ChavePixResponse>?
  ) {

    val novaChave = request.toModel()
    val chaveCriada = service.registra(novaChave)

    responseObserver?.onNext(
      ChavePixResponse.newBuilder()
        .setIdCliente(chaveCriada.idCliente)
        .setId(chaveCriada.id.toString())
        .build()
    )
    responseObserver?.onCompleted()
  }

}