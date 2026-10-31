package io.github.kmpauto.core.template

import io.github.kmpauto.core.components.AutoAction
import io.github.kmpauto.core.components.AutoGridItem
import io.github.kmpauto.core.components.AutoIcon
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AutoGridTemplateTest {

    @Test
    fun scenario2_creatingGridTemplateWithGridButtons() {
        // Given an icon grid with titles and click actions
        var clicked = false
        val items = (1..6).map {
            AutoGridItem(
                title = "App $it",
                subtitle = "Sub $it",
                icon = AutoIcon.Resource("ic_grid_$it"),
                onClick = { clicked = true }
            )
        }

        // When building an AutoGridTemplate
        val gridTemplate = AutoGridTemplate(
            title = "Quick Controls",
            headerAction = AutoAction.Back,
            items = items
        )

        // Then the grid item count must not exceed the automotive platform limit of 8 items
        assertEquals("Quick Controls", gridTemplate.title)
        assertEquals(6, gridTemplate.items.size)
        assertTrue(gridTemplate.items.size <= AutoGridTemplate.MAX_GRID_ITEMS)

        // Click action execution
        items.first().onClick?.invoke()
        assertTrue(clicked)
    }

    @Test
    fun gridTemplate_enforces8ItemLimit() {
        val nineItems = (1..9).map {
            AutoGridItem(
                title = "Item $it",
                icon = AutoIcon.Resource("ic_grid")
            )
        }

        kotlin.test.assertFailsWith<IllegalArgumentException> {
            AutoGridTemplate(
                title = "Too Many Items",
                items = nineItems
            )
        }
    }
}
