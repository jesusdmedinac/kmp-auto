package io.github.kmpauto.core.template

import io.github.kmpauto.core.components.AutoAction
import io.github.kmpauto.core.components.AutoIcon

/**
 * Multiplatform template rendering an informative message, alert, or prompt on vehicle displays.
 * Maps to Android Auto's MessageTemplate and Apple CarPlay's CPAlertTemplate.
 * Strictly enforces vehicle platform limit of maximum 2 action buttons.
 */
public data class AutoMessageTemplate(
    val message: String,
    override val title: String? = null,
    val debugMessage: String? = null,
    val icon: AutoIcon? = null,
    override val headerAction: AutoAction? = null,
    override val actionStrip: List<AutoAction> = emptyList(),
    override val isLoading: Boolean = false,
    public val actions: List<AutoAction> = emptyList()
) : AutoTemplate {

    init {
        require(actions.size <= MAX_MESSAGE_ACTIONS) {
            "AutoMessageTemplate action count (${actions.size}) exceeds vehicle platform maximum limit ($MAX_MESSAGE_ACTIONS)."
        }
    }

    public companion object {
        public const val MAX_MESSAGE_ACTIONS: Int = 2
    }
}
