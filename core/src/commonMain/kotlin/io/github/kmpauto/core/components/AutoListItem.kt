package io.github.kmpauto.core.components

/**
 * An individual row entry displayed inside an [AutoListTemplate].
 */
public data class AutoListItem(
    val title: String,
    val subtitle: String? = null,
    val icon: AutoIcon? = null,
    val onClick: (() -> Unit)? = null
)

/**
 * A group of list items rendered under an optional section header.
 */
public data class AutoListSection(
    val title: String? = null,
    val items: List<AutoListItem> = emptyList()
)
