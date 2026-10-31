package io.github.kmpauto.core

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class KmpAutoTest {

    @Test
    fun version_isConfigured() {
        assertEquals("0.1.0-SNAPSHOT", KmpAuto.VERSION)
    }

    @Test
    fun platformName_isNotBlank() {
        assertTrue(platformName.isNotBlank())
    }
}
