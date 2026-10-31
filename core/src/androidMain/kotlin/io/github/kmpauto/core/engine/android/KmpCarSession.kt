package io.github.kmpauto.core.engine.android

import android.content.Intent
import androidx.car.app.Screen
import androidx.car.app.Session
import io.github.kmpauto.core.navigation.AutoNavigator
import io.github.kmpauto.core.navigation.AutoScreen

/**
 * Base [Session] for Android Auto that connects the vehicle session to a unified [AutoNavigator].
 */
public abstract class KmpCarSession : Session() {

    public abstract fun createRootScreen(navigator: AutoNavigator): AutoScreen

    public var navigator: AutoNavigator? = null
        private set

    private var navigatorAdapter: AndroidNavigatorAdapter? = null

    override fun onCreateScreen(intent: Intent): Screen {
        val autoNavigator = AutoNavigator()
        val realRoot = createRootScreen(autoNavigator)
        autoNavigator.setRoot(realRoot)
        this.navigator = autoNavigator

        val adapter = AndroidNavigatorAdapter(carContext, autoNavigator)
        adapter.startSync()
        this.navigatorAdapter = adapter

        return AndroidCarScreen(carContext, realRoot)
    }
}
