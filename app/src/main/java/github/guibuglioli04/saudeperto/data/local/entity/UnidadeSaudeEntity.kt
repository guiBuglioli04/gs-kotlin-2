package github.guibuglioli04.saudeperto.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "unidades_saude")
data class UnidadeSaudeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    val endereco: String,
    val especialidade: String,
    val telefone: String,
    val horarioFuncionamento: String,
    val status: String, // Ex: "Disponível", "Lotado", "Manutenção"
    val isFavorito: Boolean = false,
    val observacao: String = ""
)
