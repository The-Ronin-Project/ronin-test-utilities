package com.projectronin.test.data.generator.faker

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class BooleanGeneratorTest {

    @Test
    fun `supports all static values`() {
        val generator = BooleanGenerator()
        generator of true
        assertTrue(generator.generate())

        generator of false
        assertFalse(generator.generate())
    }

    @Test
    fun `generates a boolean`() {
        val generator = BooleanGenerator()
        val actual = generator.generate()
        assertTrue(listOf(true, false).contains(actual))
    }
}
