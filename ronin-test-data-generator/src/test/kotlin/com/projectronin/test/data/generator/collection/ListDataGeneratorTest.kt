package com.projectronin.test.data.generator.collection

import com.projectronin.test.data.generator.faker.WordGenerator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ListDataGeneratorTest {
    @Test
    fun `supports static value`() {
        val generator = ListDataGenerator(5, WordGenerator())
        generator of listOf("1", "2")
        assertEquals(listOf("1", "2"), generator.generate())
    }

    @Test
    fun `supports 0 count`() {
        val generator = ListDataGenerator(0, WordGenerator())
        assertEquals(0, generator.generate().size)
    }

    @Test
    fun `supports lowering initial count`() {
        val generator = ListDataGenerator(10, WordGenerator())
        generator generates 3
        assertEquals(3, generator.generate().size)
    }

    @Test
    fun `supports increasing initial count`() {
        val generator = ListDataGenerator(2, WordGenerator())
        generator generates 5
        assertEquals(5, generator.generate().size)
    }

    @Test
    fun `supports adding additional value to 0 count`() {
        val generator = ListDataGenerator(0, WordGenerator())
        generator plus "1"
        assertEquals(listOf("1"), generator.generate())
    }

    @Test
    fun `supports adding additional value to positive count`() {
        val generator = ListDataGenerator(3, WordGenerator())
        generator plus "1"

        val generated = generator.generate()
        assertEquals(4, generated.size)
        assertEquals("1", generated.last())
    }

    @Test
    fun `supports adding multiple additional values`() {
        val generator = ListDataGenerator(3, WordGenerator())
        generator plus "1"
        generator plus "2"

        val generated = generator.generate()
        assertEquals(5, generated.size)
        assertEquals(listOf("1", "2"), generated.takeLast(2))
    }

    @Test
    fun `supports builder infix style`() {
        val generator = ListDataGenerator(0, WordGenerator())
        generator generates 2 plus "3" plus "4"

        val generated = generator.generate()
        assertEquals(4, generated.size)
        assertEquals(listOf("3", "4"), generated.takeLast(2))
    }
}
