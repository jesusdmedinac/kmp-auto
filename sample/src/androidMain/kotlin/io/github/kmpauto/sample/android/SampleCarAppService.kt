package io.github.kmpauto.sample.android

import androidx.car.app.CarAppService
import androidx.car.app.Session
import androidx.car.app.SessionInfo
import androidx.car.app.validation.HostValidator
import io.github.kmpauto.core.engine.android.KmpCarSession
import io.github.kmpauto.core.navigation.AutoNavigator
import io.github.kmpauto.core.navigation.AutoScreen
import io.github.kmpauto.sample.screens.ChargingStationListScreen

/**
 * Android Auto [CarAppService] entry point for the EV Charging sample app.
 */
public class SampleCarAppService : CarAppService() {

    override fun createHostValidator(): HostValidator {
        return HostValidator.ALLOW_ALL_HOSTS_VALIDATOR
    }

    override fun onCreateSession(sessionInfo: SessionInfo): Session {
        return object : KmpCarSession() {
            override fun createRootScreen(navigator: AutoNavigator): AutoScreen {
                return ChargingStationListScreen(navigator)
            }
        }
    }
}
