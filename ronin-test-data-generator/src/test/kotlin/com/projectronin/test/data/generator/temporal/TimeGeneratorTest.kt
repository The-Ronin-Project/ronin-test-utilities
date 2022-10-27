package com.projectronin.test.data.generator.temporal

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import java.time.LocalTime

class TimeGeneratorTest {
    @Test
    fun `supports static value`() {
        val now = LocalTime.now()
        val generator = TimeGenerator()
        generator of now

        assertEquals(now, generator.generate())
    }

    @Test
    fun `generates time`() {
        val generator = TimeGenerator()
        assertNotNull(generator.generate())
    }

    @Test
    fun `empty DSL generates a time`() {
        val time = time { }
        assertNotNull(time)
        assertInstanceOf(LocalTime::class.java, time)
    }

    @Test
    fun `DSL supports setting hour`() {
        val time = time {
            hour of 16
        }
        assertEquals(16, time.hour)
    }

    @Test
    fun `DSL supports setting minute`() {
        val time = time {
            minute of 17
        }
        assertEquals(17, time.minute)
    }

    @Test
    fun `DSL supports setting second`() {
        val time = time {
            second of 34
        }
        assertEquals(34, time.second)
    }

    @Test
    fun `DSL supports setting nanosecond`() {
        val time = time {
            nanosecond of 987654321
        }
        assertEquals(987654321, time.nano)
    }

    @Test
    fun `DSL supports setting all fields`() {
        val time = time {
            hour of 16
            minute of 17
            second of 38
            nanosecond of 123456789
        }
        assertEquals(LocalTime.of(16, 17, 38, 123456789), time)
    }
}
