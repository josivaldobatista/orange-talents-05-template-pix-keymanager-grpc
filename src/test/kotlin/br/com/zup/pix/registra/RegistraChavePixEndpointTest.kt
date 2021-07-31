package br.com.zup.pix.registra


import br.com.zup.ChavePixRequest
import br.com.zup.RegistroChavePixGrpc
import br.com.zup.TipoChave
import br.com.zup.TipoConta
import br.com.zup.integracao.itau.ContasItauClient
import br.com.zup.integracao.itau.DadosContaResponse
import br.com.zup.integracao.itau.InstituicaoResponse
import br.com.zup.integracao.itau.TitularResponse
import br.com.zup.pix.ChavePixRepository
import br.com.zup.pix.ContaAssociada
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import io.micronaut.grpc.server.GrpcServerChannel
import io.micronaut.http.HttpResponse
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import java.util.*
import javax.inject.Inject

@MicronautTest(transactional = false)
internal class RegistraChavePixEndpointTest(
  @Inject val repository: ChavePixRepository,
  @Inject val grpcClient: RegistroChavePixGrpc.RegistroChavePixBlockingStub
) {

  @Inject
  lateinit var itauClient: ContasItauClient

  companion object {
    val ID_CLIENTE: UUID = UUID.randomUUID()
  }

  @BeforeEach
  fun setUp() {
    repository.deleteAll()
  }

  @Test
  fun `deve registrar uma nova chave pix`() {
    // Cenário
    `when`(itauClient.buscaContaPorTipo(idCliente = ID_CLIENTE.toString(), tipo = "CONTA_CORRENTE"))
      .thenReturn(HttpResponse.ok(dadosDaContaResponse()))

    // Ação
    val response = grpcClient.registrar(
      ChavePixRequest.newBuilder()
        .setIdCliente(ID_CLIENTE.toString())
        .setTipoChave(TipoChave.CPF)
        .setTipoConta(TipoConta.CONTA_CORRENTE)
        .setChave("02467781054")
        .build()
    )

    // Validação
    with(response) {
      Assertions.assertEquals(ID_CLIENTE.toString(), idCliente)
      Assertions.assertNotNull(id)
    }
  }


  private fun dadosDaContaResponse(): DadosContaResponse {
    return DadosContaResponse(
      tipo = "CONTA_CORRENTE",
      instituicao = InstituicaoResponse("UNIBANCO ITAU SA", ContaAssociada.ITAU_UNIBANCO_ISPB),
      agencia = "1218",
      numero = "291900",
      titular = TitularResponse("Rafael Ponte", "63657520325")
    )
  }

  @MockBean(ContasItauClient::class)
  fun itauClient(): ContasItauClient? {
    return Mockito.mock(ContasItauClient::class.java)
  }

  @Factory
  class Clients {
    @Bean
    fun blockingStub(@GrpcChannel(GrpcServerChannel.NAME) channel: ManagedChannel):
        RegistroChavePixGrpc.RegistroChavePixBlockingStub? {
      return RegistroChavePixGrpc.newBlockingStub(channel)
    }
  }
}

