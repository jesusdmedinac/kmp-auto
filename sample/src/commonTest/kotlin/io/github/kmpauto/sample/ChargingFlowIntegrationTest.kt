package io.github.kmpauto.sample

import io.github.kmpauto.core.navigation.AutoNavigator
import io.github.kmpauto.core.template.AutoListTemplate
import io.github.kmpauto.core.template.AutoMessageTemplate
import io.github.kmpauto.core.template.AutoPaneTemplate
import io.github.kmpauto.sample.screens.ChargingActiveScreen
import io.github.kmpauto.sample.screens.ChargingStationDetailScreen
import io.github.kmpauto.sample.screens.ChargingStationListScreen
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class ChargingFlowIntegrationTest {

    @Test
    fun completeInCarChargingFlow_navigationAndTemplates() {
        // 1. Initial State: List Screen
        val navigator = AutoNavigator()
        val listScreen = ChargingStationListScreen(navigator)
        navigator.setRoot(listScreen)

        assertEquals(1, navigator.depth)
        val listTemplate = listScreen.render()
        assertIs<AutoListTemplate>(listTemplate)
        assertEquals("Estaciones de Carga", listTemplate.title)
        assertEquals(3, listTemplate.totalItems)

        // 2. Select Station -> Push Detail Screen
        val firstItem = listTemplate.sections.first().items.first()
        firstItem.onClick?.invoke()

        assertEquals(2, navigator.depth)
        val detailScreen = navigator.top
        assertIs<ChargingStationDetailScreen>(detailScreen)
        val paneTemplate = detailScreen.render()
        assertIs<AutoPaneTemplate>(paneTemplate)
        assertEquals("Reforma Supercharger", paneTemplate.title)
        assertEquals(4, paneTemplate.items.size)

        // 3. Start Charging -> Push Active Screen
        val primaryCta = paneTemplate.actions.first()
        when (primaryCta) {
            is io.github.kmpauto.core.components.AutoAction.Primary -> primaryCta.onClick()
            else -> kotlin.test.fail("Expected primary action")
        }

        assertEquals(3, navigator.depth)
        val activeScreen = navigator.top
        assertIs<ChargingActiveScreen>(activeScreen)
        val messageTemplate = activeScreen.render()
        assertIs<AutoMessageTemplate>(messageTemplate)
        assertEquals("Carga en Curso", messageTemplate.title)

        // 4. Stop Session -> Pop to Root
        val stopCta = messageTemplate.actions.first()
        when (stopCta) {
            is io.github.kmpauto.core.components.AutoAction.Destructive -> stopCta.onClick()
            else -> kotlin.test.fail("Expected destructive stop action")
        }

        assertEquals(1, navigator.depth)
        assertEquals(listScreen, navigator.top)
    }
}
