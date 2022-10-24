package com.projectronin.test.data.generator

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class DataGeneratorTest {
    @Test
    fun `static value is returned if provided`() {
        val generator = SampleGenerator()
        generator.of("Test")
        assertEquals("Test", generator.generate())
    }

    @Test
    fun `generated value is returned if no static is provided`() {
        val generator = SampleGenerator()
        assertEquals("Sample", generator.generate())
    }

    @Test
    fun `of supports infix style`() {
        val generator = SampleGenerator()
        generator of "Test"
        assertEquals("Test", generator.generate())
    }

    @Test
    fun `nullable types are supported`() {
        class NullableGenerator : DataGenerator<String?>() {
            override fun generateInternal(): String? = null
        }
        
        val generator: DataGenerator<String?> = NullableGenerator()
        assertNull(generator.generate())
    }

    private class SampleGenerator : DataGenerator<String>() {
        override fun generateInternal(): String = "Sample"
    }
}
