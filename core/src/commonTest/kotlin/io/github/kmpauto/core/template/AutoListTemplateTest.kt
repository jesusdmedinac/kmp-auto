package io.github.kmpauto.core.template

import io.github.kmpauto.core.components.AutoAction
import io.github.kmpauto.core.components.AutoIcon
import io.github.kmpauto.core.components.AutoListItem
import io.github.kmpauto.core.components.AutoListSection
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AutoListTemplateTest {

    @Test
    fun scenario1_creatingListTemplateWithSectionsAndActions() {
        // Given a list of items with titles, subtitles, and icons
        var clicked = false
        val item1 = AutoListItem(
            title = "Charging Station Alpha",
            subtitle = "150 kW • 3 available",
            icon = AutoIcon.Resource("ic_ev_station"),
            onClick = { clicked = true }
        )
        val item2 = AutoListItem(
            title = "Charging Station Beta",
            subtitle = "350 kW • 8 available",
            icon = AutoIcon.Resource("ic_ev_station")
        )

        val section1 = AutoListSection(
            title = "Fast Chargers",
            items = listOf(item1, item2)
        )

        // When building an AutoListTemplate with a back HeaderAction
        val listTemplate = AutoListTemplate(
            title = "Nearby Chargers",
            headerAction = AutoAction.Back,
            sections = listOf(section1)
        )

        // Then the template contains the expected sections and item count
        assertEquals("Nearby Chargers", listTemplate.title)
        assertEquals(AutoAction.Back, listTemplate.headerAction)
        assertEquals(1, listTemplate.sections.size)
        assertEquals(2, listTemplate.totalItems)

        // And it complies with maximum list item constraints
        assertTrue(listTemplate.totalItems <= AutoListTemplate.MAX_RECOMMENDED_ITEMS)

        // Click action execution
        item1.onClick?.invoke()
        assertTrue(clicked)
    }

    @Test
    fun listTemplate_enforcesItemLimits() {
        val manyItems = (1..30).map { AutoListItem(title = "Item $it") }
        kotlin.test.assertFailsWith<IllegalArgumentException> {
            AutoListTemplate(
                title = "Overflow",
                sections = listOf(AutoListSection(items = manyItems))
            )
        }
    }
}
