package br.com.zup.pix.registra

import br.com.zup.integracao.itau.ContasItauClient
import br.com.zup.pix.ChavePix
import br.com.zup.pix.ChavePixExistenteException
import br.com.zup.pix.ChavePixRepository
import io.micronaut.grpc.annotation.GrpcService
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional
import javax.validation.Valid

@Singleton
@Validated
@GrpcService
class NovaChavePixService(
  @Inject val repository: ChavePixRepository,
  @Inject val itauClient: ContasItauClient
) {
  private val LOGGER = LoggerFactory.getLogger(this::class.java)

  @Transactional
  fun registra(@Valid novaChave: NovaChavePix): ChavePix {

    if (repository.existsByChave(novaChave.chave))
      throw ChavePixExistenteException("Chave Pix '${novaChave.chave}' existente")

    val responseClientItau = itauClient.buscaContaPorTipo(
      novaChave.idCliente!!, novaChave.tipoConta!!.name
    )

    val conta = responseClientItau.body()?.toModel() ?: throw IllegalStateException(
      "Cliente não encontrado no Itau"
    )

    val chave = novaChave.toModel(conta)
    repository.save(chave)

    return chave
  }
}