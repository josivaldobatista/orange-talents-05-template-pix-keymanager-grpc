package br.com.zup.pix

import br.com.zup.TipoChave
import br.com.zup.TipoConta
import java.util.*
import javax.persistence.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
class ChavePix(
  @field:NotNull
  @Column(nullable = false)
  val idCliente: String,

  @field:NotNull
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  val tipoChave: TipoChave,

  @field:NotBlank
  @Column(unique = true, nullable = false)
  var chave: String,

  @field:NotNull
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  val tipoConta: TipoConta,

  ) {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null

}
