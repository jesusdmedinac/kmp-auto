package io.github.kmpauto.core.engine.ios

import io.github.kmpauto.core.navigation.AutoNavigator
import io.github.kmpauto.core.navigation.AutoScreen
import io.github.kmpauto.core.template.AutoListTemplate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import platform.CarPlay.CPInterfaceController
import platform.CarPlay.CPListTemplate
import platform.CarPlay.CPTemplate

/**
 * Coordinates unified [AutoNavigator] state with Apple CarPlay's native [CPInterfaceController].
 */
public class IosNavigatorCoordinator(
    public val interfaceController: CPInterfaceController,
    public val navigator: AutoNavigator,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)
) {
    private var lastDepth: Int = 1
    private val screenTemplateMap = mutableMapOf<AutoScreen, CPTemplate>()
    private var invalidationJob: Job? = null

    public fun startSync() {
        // Set initial root template
        val rootScreen = navigator.top
        val rootCpTemplate = IosTemplateMapper.toCarPlayTemplate(rootScreen.render())
        screenTemplateMap[rootScreen] = rootCpTemplate
        interfaceController.setRootTemplate(rootCpTemplate, animated = false, completion = null)
        observeScreenInvalidation(rootScreen, rootCpTemplate)

        // Observe navigation stack transitions
        navigator.stack
            .onEach { stack ->
                val currentDepth = stack.size
                if (currentDepth > lastDepth) {
                    val topScreen = stack.last()
                    val cpTemplate = IosTemplateMapper.toCarPlayTemplate(topScreen.render())
                    screenTemplateMap[topScreen] = cpTemplate
                    interfaceController.pushTemplate(cpTemplate, animated = true, completion = null)
                    observeScreenInvalidation(topScreen, cpTemplate)
                } else if (currentDepth < lastDepth) {
                    val pops = lastDepth - currentDepth
                    repeat(pops) {
                        interfaceController.popTemplateAnimated(animated = true, completion = null)
                    }
                } else if (stack.isNotEmpty()) {
                    // Top screen was replaced (e.g. BackstackGuard REPLACE_TOP)
                    val topScreen = stack.last()
                    if (!screenTemplateMap.containsKey(topScreen)) {
                        val cpTemplate = IosTemplateMapper.toCarPlayTemplate(topScreen.render())
                        screenTemplateMap[topScreen] = cpTemplate
                        interfaceController.popTemplateAnimated(animated = false) { _, _ ->
                            interfaceController.pushTemplate(cpTemplate, animated = false, completion = null)
                        }
                        observeScreenInvalidation(topScreen, cpTemplate)
                    }
                }
                lastDepth = currentDepth
            }
            .launchIn(scope)
    }

    private fun observeScreenInvalidation(screen: AutoScreen, cpTemplate: CPTemplate) {
        invalidationJob?.cancel()
        invalidationJob = screen.invalidationEvents
            .onEach {
                val updatedAutoTemplate = screen.render()
                if (cpTemplate is CPListTemplate && updatedAutoTemplate is AutoListTemplate) {
                    val newCpTemplate = IosTemplateMapper.toListTemplate(updatedAutoTemplate)
                    cpTemplate.updateSections(newCpTemplate.sections)
                }
            }
            .launchIn(scope)
    }
}
