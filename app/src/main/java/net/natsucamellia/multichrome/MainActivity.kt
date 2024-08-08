package net.natsucamellia.multichrome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import net.natsucamellia.multichrome.ui.MultichromeApp
import net.natsucamellia.multichrome.ui.theme.MultichromeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MultichromeTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MultichromeApp()
                }
            }
        }
    }
}