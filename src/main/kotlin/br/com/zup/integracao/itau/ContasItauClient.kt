package br.com.zup.integracao.itau

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.client.annotation.Client

@Client("\${itau.contas.url}")
interface ContasItauClient {

  @Get("/api/v1/clientes/{idCliente}/contas{?tipoConta}")
  fun buscaContaPorTipo(@PathVariable idCliente: String, @QueryValue tipoConta: String):
      HttpResponse<DadosContaResponse>

}