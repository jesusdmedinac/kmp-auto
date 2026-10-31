package io.github.kmpauto.core.components

/**
 * An individual key-value information row entry inside an [io.github.kmpauto.core.template.AutoPaneTemplate].
 */
public data class AutoPaneItem(
    val title: String,
    val text: String? = null
)
