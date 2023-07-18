package com.projectronin.test.data.generator.faker

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class UriGeneratorTest {
    @Test
    fun `generates a uri string`() {
        val generator = UriGenerator()
        assertNotNull(generator.generate())
    }

    @Test
    fun `supports a static value`() {
        val generator = UriGenerator()
        generator of "http://projectronin.com"
        assertEquals(
            "http://projectronin.com",
            generator.generate()
        )
    }

}
