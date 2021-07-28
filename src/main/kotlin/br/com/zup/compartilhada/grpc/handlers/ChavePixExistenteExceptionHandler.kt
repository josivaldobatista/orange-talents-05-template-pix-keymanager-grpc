package br.com.zup.compartilhada.grpc.handlers

import br.com.zup.compartilhada.grpc.ExceptionHandler
import br.com.zup.pix.ChavePixExistenteException
import io.grpc.Status
import javax.inject.Singleton

@Singleton
class ChavePixExistenteExceptionHandler : ExceptionHandler<ChavePixExistenteException> {

  override fun handle(e: ChavePixExistenteException): ExceptionHandler.StatusWithDetails {
    return ExceptionHandler.StatusWithDetails(
      Status.ALREADY_EXISTS
        .withDescription(e.message)
        .withCause(e)
    )
  }

  override fun supports(e: Exception): Boolean {
    return e is ChavePixExistenteException
  }
}