package com.projectronin.test.data.generator.temporal

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Test
import java.time.ZoneOffset
import java.time.ZonedDateTime

class PastDateTimeGeneratorTest {
    @Test
    fun `supports static value`() {
        val now = ZonedDateTime.now()

        val generator = PastDateTimeGenerator()
        generator of now

        assertEquals(now, generator.generate())
    }

    @Test
    fun `uses PastDateGenerator`() {
        val generator = PastDateTimeGenerator()
        assertInstanceOf(PastDateGenerator::class.java, generator.date)
    }

    @Test
    fun `generates with UTC timezone`() {
        val generator = PastDateTimeGenerator()
        val dateTime = generator.generate()
        assertEquals(ZoneOffset.UTC, dateTime.offset)
    }

    @Test
    fun `DSL supports setting date`() {
        val dateTime = pastDateTime {
            date of date {
                year of 2018
            }
        }
        assertEquals(2018, dateTime.year)
    }

    @Test
    fun `DSL supports setting time`() {
        val dateTime = pastDateTime {
            time of time {
                hour of 5
            }
        }
        assertEquals(5, dateTime.hour)
    }

    @Test
    fun `DSL supports setting date and time`() {
        val dateTime = pastDateTime {
            date of date {
                year of 2022
                month of 10
                day of 24
            }
            time of time {
                hour of 15
                minute of 2
                second of 12
                nanosecond of 123456789
            }
        }
        assertEquals(ZonedDateTime.of(2022, 10, 24, 15, 2, 12, 123456789, ZoneOffset.UTC), dateTime)
    }
}
