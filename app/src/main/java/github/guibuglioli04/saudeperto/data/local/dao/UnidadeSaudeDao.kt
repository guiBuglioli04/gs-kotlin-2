package github.guibuglioli04.saudeperto.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import github.guibuglioli04.saudeperto.data.local.entity.UnidadeSaudeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UnidadeSaudeDao {

    @Query("SELECT * FROM unidades_saude ORDER BY isFavorito DESC, nome ASC")
    fun getAllUnidades(): Flow<List<UnidadeSaudeEntity>>

    @Query("SELECT * FROM unidades_saude WHERE id = :id")
    fun getUnidadeById(id: Int): UnidadeSaudeEntity?

    @Query("SELECT COUNT(*) FROM unidades_saude")
    fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUnidade(unidade: UnidadeSaudeEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(unidades: List<UnidadeSaudeEntity>)

    @Update
    fun updateUnidade(unidade: UnidadeSaudeEntity)

    @Delete
    fun deleteUnidade(unidade: UnidadeSaudeEntity)
}
