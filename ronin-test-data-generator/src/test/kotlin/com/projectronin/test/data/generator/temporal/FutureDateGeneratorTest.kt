package com.projectronin.test.data.generator.temporal

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.LocalDate

class FutureDateGeneratorTest {
    private val now = LocalDate.now()
    private val oneYearFromNow = now.year + 1
    private val twoYearsFromNow = now.year + 2
    private val sixYearsFromNow = now.year + 2
    private val tenYearsFromNow = now.year + 10

    @Test
    fun `supports static value`() {
        val generator = DateGenerator()
        generator of now

        assertEquals(now, generator.generate())
    }

    @Test
    fun `honors default years range`() {
        val generator = FutureDateGenerator()
        testAndValidateYears(generator, oneYearFromNow..tenYearsFromNow)
    }

    @Test
    fun `honors year range when maximum is customized`() {
        val generator = FutureDateGenerator(maximumYear = twoYearsFromNow)
        testAndValidateYears(generator, oneYearFromNow..twoYearsFromNow)
    }

    @Test
    fun `honors year range when minimum and maximum are customized`() {
        val generator = FutureDateGenerator(minimumYear = twoYearsFromNow, maximumYear = sixYearsFromNow)
        testAndValidateYears(generator, twoYearsFromNow..sixYearsFromNow)
    }

    @Test
    fun `empty DSL generates a date`() {
        val date = futureDate { }
        Assertions.assertNotNull(date)
        Assertions.assertInstanceOf(LocalDate::class.java, date)
    }

    @Test
    fun `DSL supports setting year`() {
        val date = futureDate {
            year of 1991
        }
        assertEquals(1991, date.year)
    }

    @Test
    fun `DSL supports setting month`() {
        val date = futureDate {
            month of 8
        }
        assertEquals(8, date.monthValue)
    }

    @Test
    fun `DSL supports setting day`() {
        val date = futureDate {
            day of 15
        }
        assertEquals(15, date.dayOfMonth)
    }

    @Test
    fun `DSL supports setting all fields`() {
        val date = futureDate {
            year of 1991
            month of 8
            day of 15
        }
        assertEquals(LocalDate.of(1991, 8, 15), date)
    }

    private fun testAndValidateYears(generator: FutureDateGenerator, yearRange: IntRange) {
        (1..10_000).forEach {
            val date = generator.generate()
            assertTrue(yearRange.contains(date.year))
        }
    }
}
