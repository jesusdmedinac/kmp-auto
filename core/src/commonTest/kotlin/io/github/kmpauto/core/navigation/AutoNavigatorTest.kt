package io.github.kmpauto.core.navigation

import kotlin.test.Test
import kotlin.test.assertEquals

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
}
