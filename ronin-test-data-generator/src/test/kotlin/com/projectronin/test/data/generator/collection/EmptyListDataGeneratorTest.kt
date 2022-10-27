package com.projectronin.test.data.generator.collection

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class EmptyListDataGeneratorTest {
    @Test
    fun `supports static value`() {
        val generator = EmptyListDataGenerator<String>()
        generator of listOf("1", "2")
        assertEquals(listOf("1", "2"), generator.generate())
    }

    @Test
    fun `returns empty list if no static provided`() {
        val generator = EmptyListDataGenerator<String>()
        assertEquals(0, generator.generate().size)
    }

    @Test
    fun `throws exception if count is changed`() {
        val generator = EmptyListDataGenerator<String>()
        val exception = assertThrows<UnsupportedOperationException> {
            generator generates 2
        }
        assertEquals("generates is not supported on EmptyListDataGenerator", exception.message)
    }

    @Test
    fun `throws exception if an additional value is provided`() {
        val generator = EmptyListDataGenerator<String>()
        val exception = assertThrows<UnsupportedOperationException> {
            generator plus "1"
        }
        assertEquals("plus is not supported on EmptyListDataGenerator", exception.message)
    }
}
