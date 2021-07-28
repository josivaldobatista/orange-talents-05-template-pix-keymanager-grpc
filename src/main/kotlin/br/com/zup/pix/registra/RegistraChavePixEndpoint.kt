package br.com.zup.pix.registra

import br.com.zup.ChavePixRequest
import br.com.zup.ChavePixResponse
import br.com.zup.RegistroChavePixGrpc
import br.com.zup.integracao.itau.ContasItauClient
import br.com.zup.pix.ChavePix
import br.com.zup.pix.ContaAssociada
import io.grpc.Status
import io.grpc.stub.StreamObserver
import javax.inject.Inject
import javax.inject.Singleton
import javax.validation.ConstraintViolationException
import javax.validation.Valid

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