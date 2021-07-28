package br.com.zup.pix.registra

import br.com.zup.ChavePixRequest
import br.com.zup.TipoChave
import br.com.zup.TipoConta

fun ChavePixRequest.toModel(): NovaChavePix {
  return NovaChavePix(
    idCliente = idCliente,
    tipoChave = when (tipoChave) {
      TipoChave.UNKNOWN_TIPO_CHAVE -> null
      else -> TipoChave.valueOf(tipoChave.name)
    },
    chave = chave,
    tipoConta = when (tipoConta) {
      TipoConta.UNKNOWN_TIPO_CONTA -> null
      else -> TipoConta.valueOf(tipoConta.name)
    }
  )
}