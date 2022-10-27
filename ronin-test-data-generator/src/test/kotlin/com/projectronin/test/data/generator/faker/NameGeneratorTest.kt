package com.projectronin.test.data.generator.faker

import com.github.javafaker.Name
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.reflect.KFunction1
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.jvm.jvmErasure

class NameGeneratorTest {
    @Test
    fun `supports static value`() {
        val generator = NameGenerator(Name::name)
        generator of "Josh"
        assertEquals("Josh", generator.generate())
    }

    @Test
    fun `supports Name functions`() {
        val acceptableFunctions =
            Name::class.declaredFunctions.filter {
                it.returnType.jvmErasure == String::class && it.parameters.size == 1
            }.filterIsInstance<KFunction1<Name, String>>()
        assertTrue(acceptableFunctions.isNotEmpty())

        acceptableFunctions.forEach {
            val generator = NameGenerator(it)
            assertNotNull(generator.generate())
        }
    }
}
