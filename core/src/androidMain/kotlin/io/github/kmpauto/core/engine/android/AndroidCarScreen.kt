package io.github.kmpauto.core.engine.android

import androidx.car.app.CarContext
import androidx.car.app.Screen
import androidx.car.app.model.Template
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import io.github.kmpauto.core.navigation.AutoScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * Android Auto [Screen] adapter that renders an [AutoScreen] and binds to its invalidation lifecycle.
 */
public class AndroidCarScreen(
    carContext: CarContext,
    public val autoScreen: AutoScreen
) : Screen(carContext) {

    private val scope = CoroutineScope(Dispatchers.Main.immediate + Job())
    private var observerJob: Job? = null

    init {
        lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onStart(owner: LifecycleOwner) {
                observerJob = autoScreen.invalidationEvents
                    .onEach { invalidate() }
                    .launchIn(scope)
            }

            override fun onStop(owner: LifecycleOwner) {
                observerJob?.cancel()
                observerJob = null
            }
        })
    }

    override fun onGetTemplate(): Template {
        return AndroidTemplateMapper.toAndroidTemplate(autoScreen.render(), carContext)
    }
}
