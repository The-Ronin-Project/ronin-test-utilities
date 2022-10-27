package com.projectronin.test.data.generator.faker

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class IntGeneratorTest {
    @Test
    fun `supports default minimum and maximum`() {
        val generator = IntGenerator()
        testGenerate(generator, 1..50)
    }

    @Test
    fun `supports custom minimum and default maximum`() {
        val generator = IntGenerator(minimum = 10)
        testGenerate(generator, 10..50)
    }

    @Test
    fun `supports default minimum and custom maximum`() {
        val generator = IntGenerator(maximum = 5)
        testGenerate(generator, 1..5)
    }

    @Test
    fun `supports custom minimum and maximum`() {
        val generator = IntGenerator(12, 15)
        testGenerate(generator, 12..15)
    }

    @Test
    fun `supports static value`() {
        val generator = IntGenerator()
        generator of 300
        assertEquals(300, generator.generate())
    }

    private fun testGenerate(generator: IntGenerator, range: IntRange) {
        (1..10_000).forEach {
            val int = generator.generate()
            assertTrue(range.contains(int))
        }
    }
}
