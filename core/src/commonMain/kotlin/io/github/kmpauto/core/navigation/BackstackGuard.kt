package io.github.kmpauto.core.navigation

/**
 * Strategy executed when in-car navigation stack reaches maximum allowable depth (default 5 steps).
 */
public enum class BackstackGuardPolicy {
    /**
     * Replaces the top screen with the newly pushed screen, preventing host crash while keeping depth at max.
     */
    REPLACE_TOP,

    /**
     * Throws a [BackstackOverflowException] to enforce strict navigation hierarchy during testing.
     */
    THROW_EXCEPTION,

    /**
     * Drops the push request silently, preserving current screen and depth.
     */
    IGNORE
}

/**
 * Thrown when navigation backstack depth exceeds the safety threshold and policy is [BackstackGuardPolicy.THROW_EXCEPTION].
 */
public class BackstackOverflowException(message: String) : IllegalStateException(message)

/**
 * Backstack depth guard to conform to vehicle driver distraction limits (Android Auto & Apple CarPlay).
 */
public data class BackstackGuard(
    public val maxDepth: Int = DEFAULT_MAX_CAR_DEPTH,
    public val policy: BackstackGuardPolicy = BackstackGuardPolicy.REPLACE_TOP
) {
    public companion object {
        public const val DEFAULT_MAX_CAR_DEPTH: Int = 5
    }
}
