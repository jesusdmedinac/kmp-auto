package io.github.kmpauto.core.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Unified in-car navigation coordinator with backstack state tracking.
 */
public class AutoNavigator(root: AutoScreen) {
    private val _stack = MutableStateFlow(listOf(root))
    public val stack: StateFlow<List<AutoScreen>> = _stack.asStateFlow()

    public val depth: Int
        get() = _stack.value.size

    public val top: AutoScreen
        get() = _stack.value.last()

    public fun push(screen: AutoScreen) {
        _stack.value = _stack.value + screen
    }

    public fun pop(): Boolean {
        if (_stack.value.size <= 1) return false
        _stack.value = _stack.value.dropLast(1)
        return true
    }

    public fun popToRoot() {
        val root = _stack.value.first()
        _stack.value = listOf(root)
    }
}
