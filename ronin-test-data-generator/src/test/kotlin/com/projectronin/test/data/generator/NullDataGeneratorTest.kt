package com.projectronin.test.data.generator

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class NullDataGeneratorTest {
    @Test
    fun `returns static value when set`() {
        val generator = NullDataGenerator<String>()
        generator of "Hello"
        assertEquals("Hello", generator.generate())
    }

    @Test
    fun `returns null when no static value set`() {
        val generator = NullDataGenerator<String>()
        assertNull(generator.generate())
    }
}
