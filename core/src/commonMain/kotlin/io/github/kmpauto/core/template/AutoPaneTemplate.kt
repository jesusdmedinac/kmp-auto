package io.github.kmpauto.core.template

import io.github.kmpauto.core.components.AutoAction
import io.github.kmpauto.core.components.AutoPaneItem

/**
 * Multiplatform template rendering structured details, key-value rows, and call-to-action buttons.
 * Maps to Android Auto PaneTemplate and Apple CarPlay CPInformationTemplate.
 */
public data class AutoPaneTemplate(
    override val title: String,
    override val headerAction: AutoAction? = AutoAction.Back,
    override val actionStrip: List<AutoAction> = emptyList(),
    override val isLoading: Boolean = false,
    public val items: List<AutoPaneItem> = emptyList(),
    public val actions: List<AutoAction> = emptyList()
) : AutoTemplate {

    init {
        require(actions.size <= MAX_PANE_ACTIONS) {
            "AutoPaneTemplate action count (${actions.size}) exceeds vehicle platform maximum limit ($MAX_PANE_ACTIONS)."
        }
    }

    public companion object {
        public const val MAX_PANE_ACTIONS: Int = 4
    }
}
