package com.projectronin.test.data.generator.temporal

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.LocalDate

class PastDateGeneratorTest {
    private val now = LocalDate.now()
    private val oneYearAgo = now.year - 1

    @Test
    fun `supports static value`() {
        val generator = DateGenerator()
        generator of now

        assertEquals(now, generator.generate())
    }

    @Test
    fun `honors default years range`() {
        val generator = PastDateGenerator()
        testAndValidateYears(generator, 1920..oneYearAgo)
    }

    @Test
    fun `honors year range when minimum is customized`() {
        val generator = PastDateGenerator(minimumYear = 1963)
        testAndValidateYears(generator, 1963..oneYearAgo)
    }

    @Test
    fun `honors year range when minimum and maximum are customized`() {
        val generator = PastDateGenerator(minimumYear = 1955, maximumYear = 1975)
        testAndValidateYears(generator, 1955..1975)
    }

    @Test
    fun `empty DSL generates a date`() {
        val date = pastDate { }
        Assertions.assertNotNull(date)
        Assertions.assertInstanceOf(LocalDate::class.java, date)
    }

    @Test
    fun `DSL supports setting year`() {
        val date = pastDate {
            year of 1991
        }
        assertEquals(1991, date.year)
    }

    @Test
    fun `DSL supports setting month`() {
        val date = pastDate {
            month of 8
        }
        assertEquals(8, date.monthValue)
    }

    @Test
    fun `DSL supports setting day`() {
        val date = pastDate {
            day of 15
        }
        assertEquals(15, date.dayOfMonth)
    }

    @Test
    fun `DSL supports setting all fields`() {
        val date = pastDate {
            year of 1991
            month of 8
            day of 15
        }
        assertEquals(LocalDate.of(1991, 8, 15), date)
    }

    private fun testAndValidateYears(generator: PastDateGenerator, yearRange: IntRange) {
        (1..10_000).forEach {
            val date = generator.generate()
            assertTrue(yearRange.contains(date.year))
        }
    }
}
