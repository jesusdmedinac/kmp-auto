package io.github.kmpauto.core.components

/**
 * Multiplatform representation of vehicle display icons.
 */
public sealed interface AutoIcon {
    /**
     * Named drawable or vector asset (e.g. "ic_charging", "ic_navigation").
     */
    public data class Resource(val resName: String) : AutoIcon

    /**
     * System-provided icon identifier (e.g. SFSymbols on Apple or system drawables on Android).
     */
    public data class SystemIcon(val name: String) : AutoIcon
}
