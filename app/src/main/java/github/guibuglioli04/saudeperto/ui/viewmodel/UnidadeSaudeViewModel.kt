package github.guibuglioli04.saudeperto.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import github.guibuglioli04.saudeperto.data.local.database.AppDatabase
import github.guibuglioli04.saudeperto.data.local.entity.UnidadeSaudeEntity
import github.guibuglioli04.saudeperto.data.repository.UnidadeSaudeRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

sealed class UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent()
    object NavigateBack : UiEvent()
}

class UnidadeSaudeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UnidadeSaudeRepository

    // Full list from Room
    private val _unidades = MutableStateFlow<List<UnidadeSaudeEntity>>(emptyList())

    // Search query & filter state
    val searchQuery = MutableStateFlow("")
    val filterApenasFavoritos = MutableStateFlow(false)

    // Filtered list emitted to UI
    val unidadesFiltered: StateFlow<List<UnidadeSaudeEntity>>

    // One-time UI events
    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    init {
        val database = AppDatabase.getDatabase(application, viewModelScope)
        repository = UnidadeSaudeRepository(database.unidadeSaudeDao())

        // Collect DB changes into _unidades state flow
        viewModelScope.launch {
            repository.allUnidades.collect { list ->
                _unidades.value = list
            }
        }

        // Combine DB flow with search query and filter toggle
        val filteredFlow = combine(_unidades, searchQuery, filterApenasFavoritos) { list, query, apenasFav ->
            list.filter { item ->
                val matchesQuery = item.nome.contains(query, ignoreCase = true) ||
                        item.endereco.contains(query, ignoreCase = true) ||
                        item.especialidade.contains(query, ignoreCase = true)
                val matchesFav = if (apenasFav) item.isFavorito else true
                matchesQuery && matchesFav
            }
        }

        unidadesFiltered = MutableStateFlow<List<UnidadeSaudeEntity>>(emptyList()).apply {
            viewModelScope.launch {
                filteredFlow.collect { value = it }
            }
        }.asStateFlow()
    }

    suspend fun getUnidadeById(id: Int): UnidadeSaudeEntity? {
        return repository.getUnidadeById(id)
    }

    fun saveUnidade(
        id: Int,
        nome: String,
        endereco: String,
        especialidade: String,
        telefone: String,
        horarioFuncionamento: String,
        status: String,
        isFavorito: Boolean,
        observacao: String
    ): Boolean {
        // Validation check
        if (nome.isBlank() || endereco.isBlank()) {
            viewModelScope.launch {
                _uiEvent.emit(UiEvent.ShowSnackbar("Nome e Endereço são campos obrigatórios!"))
            }
            return false
        }

        val entity = UnidadeSaudeEntity(
            id = if (id > 0) id else 0,
            nome = nome.trim(),
            endereco = endereco.trim(),
            especialidade = especialidade.ifBlank { "Clínica Geral" }.trim(),
            telefone = telefone.trim(),
            horarioFuncionamento = horarioFuncionamento.ifBlank { "08:00 - 17:00" }.trim(),
            status = status.ifBlank { "Disponível" }.trim(),
            isFavorito = isFavorito,
            observacao = observacao.trim()
        )

        viewModelScope.launch {
            if (id > 0) {
                repository.update(entity)
                _uiEvent.emit(UiEvent.ShowSnackbar("Unidade atualizada com sucesso!"))
            } else {
                repository.insert(entity)
                _uiEvent.emit(UiEvent.ShowSnackbar("Nova unidade cadastrada com sucesso!"))
            }
            _uiEvent.emit(UiEvent.NavigateBack)
        }
        return true
    }

    fun toggleFavorito(unidade: UnidadeSaudeEntity) {
        viewModelScope.launch {
            repository.toggleFavorito(unidade)
            val novoStatus = if (!unidade.isFavorito) "marcada como favorita" else "removida dos favoritos"
            _uiEvent.emit(UiEvent.ShowSnackbar("${unidade.nome} $novoStatus."))
        }
    }

    fun deleteUnidade(unidade: UnidadeSaudeEntity) {
        viewModelScope.launch {
            repository.delete(unidade)
            _uiEvent.emit(UiEvent.ShowSnackbar("${unidade.nome} foi excluída."))
        }
    }
}
