package io.github.kmpauto.core.navigation

import io.github.kmpauto.core.template.AutoMessageTemplate
import io.github.kmpauto.core.template.AutoTemplate
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * Base abstraction for an in-car screen in Android Auto and Apple CarPlay.
 */
public abstract class AutoScreen {
    private val _invalidationEvents = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    public val invalidationEvents: SharedFlow<Unit> = _invalidationEvents.asSharedFlow()

    /**
     * Declares the template to render for this screen.
     */
    public open fun render(): AutoTemplate = AutoMessageTemplate(message = "")

    /**
     * Request the vehicle host to re-render or update this screen template.
     */
    public fun invalidate() {
        _invalidationEvents.tryEmit(Unit)
    }
}
