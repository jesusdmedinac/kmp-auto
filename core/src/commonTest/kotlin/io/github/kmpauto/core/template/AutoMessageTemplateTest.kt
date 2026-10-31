package io.github.kmpauto.core.template

import io.github.kmpauto.core.components.AutoAction
import io.github.kmpauto.core.components.AutoIcon
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AutoMessageTemplateTest {

    @Test
    fun scenario4_creatingMessageTemplateForAlertsAndNotifications() {
        // Given an alert message, an icon, and confirm/dismiss actions
        var confirmed = false
        var dismissed = false

        val confirmAction = AutoAction.Primary(
            title = "Start Charging",
            onClick = { confirmed = true }
        )
        val dismissAction = AutoAction.Standard(
            title = "Not Now",
            onClick = { dismissed = true }
        )

        // When building an AutoMessageTemplate
        val messageTemplate = AutoMessageTemplate(
            title = "Station Available",
            message = "Charger #4 is ready for your vehicle.",
            debugMessage = "station_id=123;port=4",
            icon = AutoIcon.Resource("ic_success"),
            actions = listOf(confirmAction, dismissAction)
        )

        // Then the message template defines the primary and secondary user actions
        assertEquals("Station Available", messageTemplate.title)
        assertEquals("Charger #4 is ready for your vehicle.", messageTemplate.message)
        assertEquals("station_id=123;port=4", messageTemplate.debugMessage)
        assertEquals(2, messageTemplate.actions.size)
        assertTrue(messageTemplate.actions.size <= AutoMessageTemplate.MAX_MESSAGE_ACTIONS)

        confirmAction.onClick()
        assertTrue(confirmed)

        dismissAction.onClick()
        assertTrue(dismissed)
    }

    @Test
    fun messageTemplate_enforcesMax2Actions() {
        val threeActions = listOf(
            AutoAction.Standard("1") {},
            AutoAction.Standard("2") {},
            AutoAction.Standard("3") {}
        )

        kotlin.test.assertFailsWith<IllegalArgumentException> {
            AutoMessageTemplate(
                message = "Too many actions",
                actions = threeActions
            )
        }
    }
}
