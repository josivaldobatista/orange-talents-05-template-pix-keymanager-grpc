package br.com.zup.pix.registra

import br.com.zup.pix.ChavePix
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
interface ChavePixRepository: CrudRepository<ChavePix, Long> {
  fun existsByChave(chave: String?): Boolean
}