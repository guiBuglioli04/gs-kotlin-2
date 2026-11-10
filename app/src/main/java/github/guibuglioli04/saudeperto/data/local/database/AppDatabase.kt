package github.guibuglioli04.saudeperto.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import github.guibuglioli04.saudeperto.data.local.dao.UnidadeSaudeDao
import github.guibuglioli04.saudeperto.data.local.entity.UnidadeSaudeEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [UnidadeSaudeEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun unidadeSaudeDao(): UnidadeSaudeDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "saude_perto_database"
                )
                    .addCallback(AppDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database.unidadeSaudeDao())
                }
            }
        }

        fun populateDatabase(dao: UnidadeSaudeDao) {
            if (dao.getCount() == 0) {
                val initialData = listOf(
                    UnidadeSaudeEntity(
                        nome = "UBS Central Vila Saúde",
                        endereco = "Av. Paulista, 1000 - Bela Vista",
                        especialidade = "Clínica Geral / Pediatria",
                        telefone = "(11) 3211-1000",
                        horarioFuncionamento = "07:00 - 19:00",
                        status = "Disponível",
                        isFavorito = true,
                        observacao = "Possui rampa de acessibilidade e vacinação contínua."
                    ),
                    UnidadeSaudeEntity(
                        nome = "Centro de Vacinação Zona Norte",
                        endereco = "Rua Conselheiro Saraiva, 450 - Santana",
                        especialidade = "Vacinação e Imunização",
                        telefone = "(11) 2977-8000",
                        horarioFuncionamento = "08:00 - 17:00",
                        status = "Disponível",
                        isFavorito = false,
                        observacao = "Atendimento prioritário para idosos e gestantes."
                    ),
                    UnidadeSaudeEntity(
                        nome = "Posto de Saúde Comunitário Sol Nascente",
                        endereco = "Rua das Flores, 120 - Jardim Primavera",
                        especialidade = "Odontologia e Enfermagem",
                        telefone = "(11) 4002-8922",
                        horarioFuncionamento = "07:30 - 18:00",
                        status = "Lotado",
                        isFavorito = false,
                        observacao = "Agendamento antecipado recomendado."
                    )
                )
                dao.insertAll(initialData)
            }
        }
    }
}
