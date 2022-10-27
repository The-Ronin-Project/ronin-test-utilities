package com.projectronin.test.data.generator.faker

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class WordGeneratorTest {
    @Test
    fun `supports static value`() {
        val generator = WordGenerator()
        generator of "test"
        assertEquals("test", generator.generate())
    }

    @Test
    fun `generates a word`() {
        val generator = WordGenerator()
        assertNotNull(generator.generate())
    }
}
