package io.github.kmpauto.core.template

import io.github.kmpauto.core.components.AutoAction
import io.github.kmpauto.core.components.AutoGridItem

/**
 * Multiplatform template rendering an icon grid on vehicle displays.
 * Strictly enforces Apple CarPlay limit of maximum 8 grid buttons to prevent host rejection.
 */
public data class AutoGridTemplate(
    override val title: String,
    override val headerAction: AutoAction? = AutoAction.Back,
    override val actionStrip: List<AutoAction> = emptyList(),
    override val isLoading: Boolean = false,
    public val items: List<AutoGridItem> = emptyList()
) : AutoTemplate {

    init {
        require(items.size <= MAX_GRID_ITEMS) {
            "AutoGridTemplate item count (${items.size}) exceeds vehicle platform maximum limit ($MAX_GRID_ITEMS)."
        }
    }

    public companion object {
        public const val MAX_GRID_ITEMS: Int = 8
    }
}
