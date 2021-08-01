package br.com.zup.pix.registra

import br.com.zup.compartilhada.validation.ValidUUID
import br.com.zup.pix.ChavePix
import br.com.zup.pix.ContaAssociada
import br.com.zup.pix.TipoChave
import br.com.zup.pix.TipoConta
import io.micronaut.core.annotation.Introspected
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@ValidPixKey
@Introspected
data class NovaChavePix(

  @field:ValidUUID
  @field:NotBlank
  val idCliente: String?,

  @field:NotNull
  val tipoChave: TipoChave?,//<- Aqui que estou chamando valiação do enum TipoChave no pacote pix

  @field:Size(max = 77)
  val chave: String?,

  @field:NotNull
  val tipoConta: TipoConta?
) {

  fun toModel(conta: ContaAssociada): ChavePix {
    return ChavePix(
      idCliente = UUID.fromString(this.idCliente),
      tipoChave = TipoChave.valueOf(this.tipoChave!!.name),
      chave = if (this.tipoChave == TipoChave.ALEATORIA) UUID.randomUUID().toString() else this.chave!!,
      tipoConta = TipoConta.valueOf(this.tipoConta!!.name),
      conta = conta
    )
  }
}