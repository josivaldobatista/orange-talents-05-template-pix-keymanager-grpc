package br.com.zup.pix.registra

import br.com.zup.ChavePixRequest
import br.com.zup.pix.TipoChave //<- Meu enum
import br.com.zup.TipoChave.* //<- GRPC enum
import br.com.zup.TipoConta.*
import br.com.zup.pix.TipoConta

fun ChavePixRequest.toModel(): NovaChavePix {
  return NovaChavePix(
    idCliente = idCliente,
    tipoChave = when (tipoChave) {
      UNKNOWN_TIPO_CHAVE -> null
      else -> TipoChave.valueOf(tipoChave.name)
    },
    chave = chave,
    tipoConta = when (tipoConta) {
      UNKNOWN_TIPO_CONTA -> null
      else -> TipoConta.valueOf(tipoConta.name)
    }
  )
}