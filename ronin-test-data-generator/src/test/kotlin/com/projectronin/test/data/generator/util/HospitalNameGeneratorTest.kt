package com.projectronin.test.data.generator.util

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class HospitalNameGeneratorTest {
    @Test
    fun `generates a place name`() {
        val generator = HospitalNameGenerator()
        val string = generator.generate()
        assertNotNull(string)
    }

    @Test
    fun `supports a static value`() {
        val generator = HospitalNameGenerator()
        generator of "Seattle Grace"
        Assertions.assertEquals(
            "Seattle Grace",
            generator.generate()
        )
    }
}
