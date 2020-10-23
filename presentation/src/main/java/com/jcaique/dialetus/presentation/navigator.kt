package com.jcaique.dialetus.presentation

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.addCallback
import androidx.compose.runtime.ambientOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.jcaique.dialetus.domain.models.Region

sealed class Screen {
    object Regions : Screen()
    data class Dialects(val region: Region) : Screen()
}

class Navigator(
  defaultScreen: Screen,
  private val onBackPressedDispatcher: OnBackPressedDispatcher
) {
    val stack = ArrayDeque(listOf(defaultScreen))
    var currentScreen by mutableStateOf(stack.first())
        private set

    val backPressedCallback: OnBackPressedCallback

    init {
        backPressedCallback = onBackPressedDispatcher.addCallback { back() }
    }

    fun navigate(screen: Screen) {
        stack.addLast(screen)
        currentScreen = screen
        backPressedCallback.isEnabled = true
    }

    fun back() {
        stack.removeLastOrNull()
        stack.lastOrNull()
            ?.let(::currentScreen::set)
            ?: run {
                backPressedCallback.isEnabled = false
                onBackPressedDispatcher.onBackPressed()
            }
    }
}

val NavigatorAmbient = ambientOf<Navigator> { error("Navigator not initialized") }
