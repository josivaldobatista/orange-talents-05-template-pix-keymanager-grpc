package br.com.zup.pix.registra

import br.com.zup.TipoChave
import br.com.zup.TipoConta
import br.com.zup.pix.ChavePix
import br.com.zup.pix.ContaAssociada
import io.micronaut.core.annotation.Introspected
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Introspected
data class NovaChavePix(

  @field:NotBlank
  val idCliente: String?,
  @field:NotNull
  val tipoChave: TipoChave?,
  @field:Size(max = 77)
  val chave: String?,
  @field:NotNull
  val tipoConta: TipoConta?
) {

  fun toModel(conta: ContaAssociada): ChavePix {
    return ChavePix(
      idCliente = UUID.fromString(this.idCliente).toString(),
      tipoChave = TipoChave.valueOf(this.tipoChave!!.name),
      chave = if (this.tipoChave == TipoChave.ALEATORIA) UUID.randomUUID().toString() else this.chave!!,
      tipoConta = TipoConta.valueOf(this.tipoConta!!.name),
      conta = conta
    )
  }
}