package com.projectronin.test.data.generator.util

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class IdentifierStringGeneratorTest {
    @Test
    fun `can generate`() {
        val generator = IdentifierStringGenerator()
        val string = generator.generate()
        assertNotNull(string)
        assertTrue(string.length >= 15)
    }

    @Test
    fun `can generate with length`() {
        val generator = IdentifierStringGenerator(64)
        val string = generator.generate()
        assertNotNull(string)
        assertTrue(string.length >= 64)
        assertTrue(string.matches("[a-zA-Z0-9]+".toRegex()))
    }

    @Test
    fun `supports a static value`() {
        val generator = IdentifierStringGenerator()
        generator of "ITYSL"
        Assertions.assertEquals(
            "ITYSL",
            generator.generate()
        )
    }
}
