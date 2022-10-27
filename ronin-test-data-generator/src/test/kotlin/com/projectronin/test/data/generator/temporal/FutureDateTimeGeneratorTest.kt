package com.projectronin.test.data.generator.temporal

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Test
import java.time.ZoneOffset
import java.time.ZonedDateTime

class FutureDateTimeGeneratorTest {
    @Test
    fun `supports static value`() {
        val now = ZonedDateTime.now()

        val generator = FutureDateTimeGenerator()
        generator of now

        assertEquals(now, generator.generate())
    }

    @Test
    fun `uses FutureDateGenerator`() {
        val generator = FutureDateTimeGenerator()
        assertInstanceOf(FutureDateGenerator::class.java, generator.date)
    }

    @Test
    fun `generates with UTC timezone`() {
        val generator = FutureDateTimeGenerator()
        val dateTime = generator.generate()
        assertEquals(ZoneOffset.UTC, dateTime.offset)
    }

    @Test
    fun `empty DSL generates a date-time`() {
        val dateTime = futureDateTime { }
        Assertions.assertNotNull(dateTime)
        assertInstanceOf(ZonedDateTime::class.java, dateTime)
    }

    @Test
    fun `DSL supports setting date`() {
        val dateTime = futureDateTime {
            date of date {
                year of 2018
            }
        }
        assertEquals(2018, dateTime.year)
    }

    @Test
    fun `DSL supports setting time`() {
        val dateTime = futureDateTime {
            time of time {
                hour of 5
            }
        }
        assertEquals(5, dateTime.hour)
    }

    @Test
    fun `DSL supports setting date and time`() {
        val dateTime = futureDateTime {
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
