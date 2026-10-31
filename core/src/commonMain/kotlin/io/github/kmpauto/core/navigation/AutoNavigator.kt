package io.github.kmpauto.core.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Unified in-car navigation coordinator with backstack state tracking and depth guard protection.
 */
public class AutoNavigator(
    root: AutoScreen,
    public val guard: BackstackGuard = BackstackGuard()
) {
    private val _stack = MutableStateFlow(listOf(root))
    public val stack: StateFlow<List<AutoScreen>> = _stack.asStateFlow()

    public val depth: Int
        get() = _stack.value.size

    public val top: AutoScreen
        get() = _stack.value.last()

    public fun push(screen: AutoScreen) {
        val currentSize = _stack.value.size
        if (currentSize >= guard.maxDepth) {
            when (guard.policy) {
                BackstackGuardPolicy.REPLACE_TOP -> {
                    _stack.value = _stack.value.dropLast(1) + screen
                }
                BackstackGuardPolicy.THROW_EXCEPTION -> {
                    throw BackstackOverflowException(
                        "Attempted to push screen beyond maximum allowed vehicle backstack depth (${guard.maxDepth})."
                    )
                }
                BackstackGuardPolicy.IGNORE -> {
                    // Intentionally ignore push to maintain compliance
                }
            }
        } else {
            _stack.value = _stack.value + screen
        }
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
