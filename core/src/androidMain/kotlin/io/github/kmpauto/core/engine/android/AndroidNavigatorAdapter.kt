package io.github.kmpauto.core.engine.android

import androidx.car.app.CarContext
import androidx.car.app.ScreenManager
import io.github.kmpauto.core.navigation.AutoNavigator
import io.github.kmpauto.core.navigation.AutoScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * Synchronizes unified [AutoNavigator] state changes with Android Auto's native [ScreenManager].
 */
public class AndroidNavigatorAdapter(
    public val carContext: CarContext,
    public val navigator: AutoNavigator,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main.immediate)
) {
    private val screenManager: ScreenManager = carContext.getCarService(ScreenManager::class.java)
    private var lastDepth: Int = 1

    public fun startSync() {
        navigator.stack
            .onEach { stack ->
                val currentDepth = stack.size
                if (currentDepth > lastDepth) {
                    // Push the new top screen
                    val topScreen = stack.last()
                    screenManager.push(AndroidCarScreen(carContext, topScreen))
                } else if (currentDepth < lastDepth) {
                    // Pop screens until depth matches
                    val popsNeeded = lastDepth - currentDepth
                    repeat(popsNeeded) {
                        screenManager.pop()
                    }
                } else if (stack.isNotEmpty()) {
                    // Top screen was replaced (e.g. BackstackGuard REPLACE_TOP)
                    val topScreen = stack.last()
                    val currentTop = screenManager.top
                    if (currentTop is AndroidCarScreen && currentTop.autoScreen != topScreen) {
                        screenManager.pop()
                        screenManager.push(AndroidCarScreen(carContext, topScreen))
                    }
                }
                lastDepth = currentDepth
            }
            .launchIn(scope)
    }

    public fun createInitialScreen(): AndroidCarScreen {
        return AndroidCarScreen(carContext, navigator.top)
    }
}
