package br.com.zup.pix.registra

import br.com.zup.ChavePixRequest
import br.com.zup.ChavePixResponse
import br.com.zup.RegistroChavePixGrpc
import br.com.zup.pix.ChavePix
import io.grpc.Status
import io.grpc.stub.StreamObserver
import javax.inject.Inject
import javax.inject.Singleton
import javax.validation.ConstraintViolationException

@Singleton
class RegistraChavePixEndpoint(@Inject val chavePixRepository: ChavePixRepository) :
  RegistroChavePixGrpc.RegistroChavePixImplBase() {

  override fun registrar(
    request: ChavePixRequest,
    responseObserver: StreamObserver<ChavePixResponse>?
  ) {

    if (chavePixRepository.existsByChave(request.chave)) {
      responseObserver?.onError(
        (Status.ALREADY_EXISTS
          .withDescription("Ja existe uma chave com esse id")
          .asRuntimeException())
      )
      return
    }

    val chavePix = ChavePix(
      idCliente = request.idCliente,
      chave = request.chave,
      tipoChave = request.tipoChave,
      tipoConta = request.tipoConta
    )

    try {
      chavePixRepository.save(chavePix)
    } catch (e: ConstraintViolationException) {
      responseObserver?.onError(
        Status.INVALID_ARGUMENT
          .withDescription("Dados de entrada invalido")
          .asRuntimeException()
      )
      return
    }

    responseObserver?.onNext(
      ChavePixResponse.newBuilder()
        .setIdCliente(chavePix.idCliente)
        .setId(chavePix.id.toString())
        .build()
    )
    responseObserver?.onCompleted()
  }

}