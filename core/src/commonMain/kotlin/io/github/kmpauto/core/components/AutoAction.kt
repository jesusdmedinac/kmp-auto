package io.github.kmpauto.core.components

/**
 * Multiplatform representation of vehicle user actions (buttons, header navigation).
 */
public sealed interface AutoAction {
    /**
     * Standard secondary action button.
     */
    public data class Standard(
        val title: String,
        val onClick: () -> Unit
    ) : AutoAction

    /**
     * Emphasized primary action button (e.g. "Start route", "Pay").
     */
    public data class Primary(
        val title: String,
        val onClick: () -> Unit
    ) : AutoAction

    /**
     * Destructive warning action button (e.g. "Cancel reservation", "Clear history").
     */
    public data class Destructive(
        val title: String,
        val onClick: () -> Unit
    ) : AutoAction

    /**
     * Vehicle back navigation button.
     */
    public data object Back : AutoAction

    /**
     * Vehicle app icon affordance button.
     */
    public data object AppIcon : AutoAction
}
