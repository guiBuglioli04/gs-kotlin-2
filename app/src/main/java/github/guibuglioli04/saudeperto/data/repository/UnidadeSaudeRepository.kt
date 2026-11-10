package github.guibuglioli04.saudeperto.data.repository

import github.guibuglioli04.saudeperto.data.local.dao.UnidadeSaudeDao
import github.guibuglioli04.saudeperto.data.local.entity.UnidadeSaudeEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class UnidadeSaudeRepository(private val dao: UnidadeSaudeDao) {

    val allUnidades: Flow<List<UnidadeSaudeEntity>> = dao.getAllUnidades()

    suspend fun getUnidadeById(id: Int): UnidadeSaudeEntity? = withContext(Dispatchers.IO) {
        dao.getUnidadeById(id)
    }

    suspend fun insert(unidade: UnidadeSaudeEntity) = withContext(Dispatchers.IO) {
        dao.insertUnidade(unidade)
    }

    suspend fun update(unidade: UnidadeSaudeEntity) = withContext(Dispatchers.IO) {
        dao.updateUnidade(unidade)
    }

    suspend fun delete(unidade: UnidadeSaudeEntity) = withContext(Dispatchers.IO) {
        dao.deleteUnidade(unidade)
    }

    suspend fun toggleFavorito(unidade: UnidadeSaudeEntity) = withContext(Dispatchers.IO) {
        val updated = unidade.copy(isFavorito = !unidade.isFavorito)
        dao.updateUnidade(updated)
    }
}
