package br.com.zup.integracao.itau

data class DadosContaResponse(
  val tipo: String,
  val instituicao: InstituicaoResponse,
  val agencia: String,
  val numero: String,
  val titular: TitularResponse
)

data class TitularResponse(val nome: String, val cpf: String)
data class InstituicaoResponse(val nome: String, val ispb: String)
