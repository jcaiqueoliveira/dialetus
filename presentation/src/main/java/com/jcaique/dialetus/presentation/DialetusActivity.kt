package com.jcaique.dialetus.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.setContent
import androidx.ui.tooling.preview.Preview
import com.jcaique.dialetus.domain.models.Dialect
import com.jcaique.dialetus.domain.models.Region
import com.jcaique.dialetus.presentation.ui.screen.DialectsScreen
import com.jcaique.dialetus.presentation.ui.screen.RegionsScreen
import com.jcaique.dialetus.presentation.ui.theme.DialetusTheme

class DialetusActivity : AppCompatActivity() {

    private val regions by lazy {
        (0..10).map { index ->
            Region(
                name = "Region $index",
                total = index
            )
        }
    }

    private val dialects by lazy {
        (0..10).map { index ->
            Dialect(
                slug = "slug-$index",
                dialect = "Dialect $index",
                examples = listOf("Example 1", "Example 2", "Example 3"),
                meanings = listOf("Meaning 1", "Meaning 2")
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DialetusApp()
        }
    }

    @Composable
    private fun DialetusApp() {
        DialetusTheme {
            val navigator = remember {
                mutableStateOf(Navigator(Screen.Regions, onBackPressedDispatcher))
            }

            Providers(
                NavigatorAmbient provides navigator.value
            ) {
                Crossfade(current = navigator.value.currentScreen) { screen ->
                    when (screen) {
                        is Screen.Regions -> RegionsScreen(regions)
                        is Screen.Dialects -> DialectsScreen(screen.region, dialects)
                    }
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        DialetusApp()
    }
}
