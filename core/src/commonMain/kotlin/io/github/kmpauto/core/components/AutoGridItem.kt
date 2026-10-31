package io.github.kmpauto.core.components

/**
 * An individual icon card or button entry inside an [io.github.kmpauto.core.template.AutoGridTemplate].
 */
public data class AutoGridItem(
    val title: String,
    val subtitle: String? = null,
    val icon: AutoIcon,
    val onClick: (() -> Unit)? = null
)
