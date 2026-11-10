package github.guibuglioli04.saudeperto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import github.guibuglioli04.saudeperto.ui.navigation.NavGraph
import github.guibuglioli04.saudeperto.ui.theme.SaudePertoTheme
import github.guibuglioli04.saudeperto.ui.viewmodel.UnidadeSaudeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SaudePertoTheme {
                val navController = rememberNavController()
                val viewModel: UnidadeSaudeViewModel = viewModel()

                NavGraph(
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }
    }
}
