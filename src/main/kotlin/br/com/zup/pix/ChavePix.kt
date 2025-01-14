package br.com.zup.pix

import java.time.LocalDateTime
import java.util.*
import javax.persistence.*
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
@Table(uniqueConstraints = [UniqueConstraint(
  name = "uk_chave_pix",
  columnNames = ["chave"]
)])
class ChavePix(
  @field:NotNull
  @Column(nullable = false)
  val idCliente: UUID,

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

  @field:Valid
  @Embedded
  val conta: ContaAssociada

) {

  @Id
  @GeneratedValue
  val id: UUID? = null

  @Column(nullable = false)
  val criadoEm: LocalDateTime = LocalDateTime.now()

}
