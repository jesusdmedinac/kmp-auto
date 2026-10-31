package io.github.kmpauto.core.template

import io.github.kmpauto.core.components.AutoAction
import io.github.kmpauto.core.components.AutoListSection

/**
 * Multiplatform template rendering structured lists and sections on car displays.
 */
public data class AutoListTemplate(
    override val title: String,
    override val headerAction: AutoAction? = AutoAction.Back,
    override val actionStrip: List<AutoAction> = emptyList(),
    override val isLoading: Boolean = false,
    public val sections: List<AutoListSection> = emptyList()
) : AutoTemplate {

    public val totalItems: Int
        get() = sections.sumOf { it.items.size }

    init {
        require(totalItems <= MAX_RECOMMENDED_ITEMS) {
            "Total list items ($totalItems) exceeds maximum allowed vehicle items limit ($MAX_RECOMMENDED_ITEMS)."
        }
    }

    public companion object {
        public const val MAX_RECOMMENDED_ITEMS: Int = 24
    }
}
