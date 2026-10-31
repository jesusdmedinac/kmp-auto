package io.github.kmpauto.core.template

import io.github.kmpauto.core.components.AutoAction
import io.github.kmpauto.core.components.AutoPaneItem
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AutoPaneTemplateTest {

    @Test
    fun scenario3_creatingInformationPaneTemplate() {
        // Given key-value information rows and call-to-action buttons
        var primaryClicked = false
        val items = listOf(
            AutoPaneItem(title = "Location", text = "Av. Reforma 222, CDMX"),
            AutoPaneItem(title = "Power", text = "350 kW Ultra-Fast"),
            AutoPaneItem(title = "Available Connectors", text = "3 of 8 free")
        )
        val actions = listOf(
            AutoAction.Primary(title = "Start Charging", onClick = { primaryClicked = true }),
            AutoAction.Standard(title = "Directions", onClick = {})
        )

        // When building an AutoPaneTemplate
        val paneTemplate = AutoPaneTemplate(
            title = "Station Details",
            headerAction = AutoAction.Back,
            items = items,
            actions = actions
        )

        // Then the pane encapsulates rows and action buttons within platform limits
        assertEquals("Station Details", paneTemplate.title)
        assertEquals(3, paneTemplate.items.size)
        assertEquals(2, paneTemplate.actions.size)
        assertTrue(paneTemplate.actions.size <= AutoPaneTemplate.MAX_PANE_ACTIONS)

        // Action trigger
        val primaryAction = paneTemplate.actions.first() as AutoAction.Primary
        primaryAction.onClick()
        assertTrue(primaryClicked)
    }

    @Test
    fun paneTemplate_enforcesMaxActionsLimit() {
        val fiveActions = (1..5).map {
            AutoAction.Standard(title = "Act $it", onClick = {})
        }

        kotlin.test.assertFailsWith<IllegalArgumentException> {
            AutoPaneTemplate(
                title = "Overflow Actions",
                items = listOf(AutoPaneItem(title = "Test")),
                actions = fiveActions
            )
        }
    }
}
