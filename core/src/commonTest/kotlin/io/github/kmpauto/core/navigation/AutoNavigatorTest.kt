package io.github.kmpauto.core.navigation

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AutoNavigatorTest {

    private class TestScreen(val name: String) : AutoScreen()

    @Test
    fun scenario1_navigatingForwardWithinSafetyDepthLimits() {
        // Given a freshly initialized AutoNavigator with a root AutoScreen
        val rootScreen = TestScreen("Root")
        val navigator = AutoNavigator(root = rootScreen)

        assertEquals(1, navigator.depth)
        assertEquals(rootScreen, navigator.top)

        // When pushing 3 subsequent AutoScreens
        val screen2 = TestScreen("Screen 2")
        val screen3 = TestScreen("Screen 3")
        val screen4 = TestScreen("Screen 4")

        navigator.push(screen2)
        navigator.push(screen3)
        navigator.push(screen4)

        // Then the active stack depth should be 4
        assertEquals(4, navigator.depth)
        // And the top screen should match the last pushed screen
        assertEquals(screen4, navigator.top)
    }

    @Test
    fun scenario2_preventingCrashesWhenExceeding5StepLimit_replaceTopPolicy() {
        // Given an AutoNavigator with 5 screens on the backstack (default maxDepth = 5, policy = REPLACE_TOP)
        val screens = (1..5).map { TestScreen("Screen $it") }
        val navigator = AutoNavigator(root = screens[0], guard = BackstackGuard(maxDepth = 5, policy = BackstackGuardPolicy.REPLACE_TOP))

        for (i in 1..4) {
            navigator.push(screens[i])
        }
        assertEquals(5, navigator.depth)
        assertEquals(screens[4], navigator.top)

        // When attempting to push a 6th AutoScreen without popping
        val screen6 = TestScreen("Screen 6")
        navigator.push(screen6)

        // Then the navigator should trigger the configured BackstackGuard policy and avoid exceeding platform host limits
        assertEquals(5, navigator.depth)
        assertEquals(screen6, navigator.top)
    }

    @Test
    fun scenario2_preventingCrashesWhenExceedingLimit_throwPolicy() {
        val root = TestScreen("Root")
        val navigator = AutoNavigator(root = root, guard = BackstackGuard(maxDepth = 2, policy = BackstackGuardPolicy.THROW_EXCEPTION))

        navigator.push(TestScreen("Screen 2"))
        assertEquals(2, navigator.depth)

        kotlin.test.assertFailsWith<BackstackOverflowException> {
            navigator.push(TestScreen("Screen 3"))
        }
    }

    @Test
    fun scenario3_poppingScreensAndReturningToRoot() {
        // Given an AutoNavigator with 4 screens on the backstack
        val root = TestScreen("Root")
        val screen2 = TestScreen("Screen 2")
        val screen3 = TestScreen("Screen 3")
        val screen4 = TestScreen("Screen 4")

        val navigator = AutoNavigator(root = root)
        navigator.push(screen2)
        navigator.push(screen3)
        navigator.push(screen4)
        assertEquals(4, navigator.depth)

        // When calling pop
        val popped = navigator.pop()

        // Then the active stack depth should be 3
        assertTrue(popped)
        assertEquals(3, navigator.depth)
        assertEquals(screen3, navigator.top)

        // When calling popToRoot
        navigator.popToRoot()

        // Then the active stack depth should be 1
        assertEquals(1, navigator.depth)
        // And the top screen should be the initial root screen
        assertEquals(root, navigator.top)
    }

    @Test
    fun scenario3_poppingAtRootReturnsFalse() {
        val root = TestScreen("Root")
        val navigator = AutoNavigator(root = root)

        val popped = navigator.pop()
        kotlin.test.assertFalse(popped)
        assertEquals(1, navigator.depth)
        assertEquals(root, navigator.top)
    }
}
