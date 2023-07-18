package com.projectronin.test.data.generator.faker

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class SentenceGeneratorTest {
    @Test
    fun `generates a sentence`() {
        val generator = SentenceGenerator()
        assertNotNull(generator.generate())
    }

    @Test
    fun `supports a static value`() {
        val generator = SentenceGenerator()
        generator of "Write a complete sentence."
        assertEquals(
            "Write a complete sentence.",
            generator.generate()
        )
    }

}
