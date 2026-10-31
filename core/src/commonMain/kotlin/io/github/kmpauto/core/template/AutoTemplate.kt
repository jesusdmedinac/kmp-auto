package io.github.kmpauto.core.template

import io.github.kmpauto.core.components.AutoAction

/**
 * Common interface for all vehicle templates across Android Auto and Apple CarPlay.
 */
public sealed interface AutoTemplate {
    public val title: String?
    public val headerAction: AutoAction?
    public val actionStrip: List<AutoAction>
    public val isLoading: Boolean
}
