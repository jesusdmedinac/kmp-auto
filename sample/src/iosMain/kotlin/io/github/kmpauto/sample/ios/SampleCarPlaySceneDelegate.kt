package io.github.kmpauto.sample.ios

import io.github.kmpauto.core.engine.ios.IosNavigatorCoordinator
import io.github.kmpauto.core.navigation.AutoNavigator
import io.github.kmpauto.sample.screens.ChargingStationListScreen
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ObjCSignatureOverride
import platform.CarPlay.CPInterfaceController
import platform.CarPlay.CPTemplateApplicationScene
import platform.CarPlay.CPTemplateApplicationSceneDelegateProtocol
import platform.CarPlay.CPWindow
import platform.darwin.NSObject

/**
 * Apple CarPlay scene delegate for the EV Charging sample app.
 */
@OptIn(BetaInteropApi::class)
public class SampleCarPlaySceneDelegate : NSObject(), CPTemplateApplicationSceneDelegateProtocol {

    private var coordinator: IosNavigatorCoordinator? = null

    @ObjCSignatureOverride
    override fun templateApplicationScene(
        templateApplicationScene: CPTemplateApplicationScene,
        didConnectInterfaceController: CPInterfaceController,
        toWindow: CPWindow
    ) {
        val navigator = AutoNavigator()
        val rootScreen = ChargingStationListScreen(navigator)
        navigator.setRoot(rootScreen)

        val coord = IosNavigatorCoordinator(
            interfaceController = didConnectInterfaceController,
            navigator = navigator
        )
        coord.startSync()
        this.coordinator = coord
    }

    @ObjCSignatureOverride
    override fun templateApplicationScene(
        templateApplicationScene: CPTemplateApplicationScene,
        didDisconnectInterfaceController: CPInterfaceController,
        fromWindow: CPWindow
    ) {
        coordinator = null
    }
}
