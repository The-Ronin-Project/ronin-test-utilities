package com.projectronin.test.data.generator.temporal

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.LocalDate

class DateGeneratorTest {
    private val now = LocalDate.now()
    private val tenYearsFromNow = now.year + 10

    @Test
    fun `supports static value`() {
        val generator = DateGenerator()
        generator of now

        assertEquals(now, generator.generate())
    }

    @Test
    fun `honors default years range`() {
        val generator = DateGenerator()
        testAndValidateYears(generator, 1920..tenYearsFromNow)
    }

    @Test
    fun `honors year range when minimum is customized`() {
        val generator = DateGenerator(minimumYear = 1984)
        testAndValidateYears(generator, 1984..tenYearsFromNow)
    }

    @Test
    fun `honors year range when maximum is customized`() {
        val generator = DateGenerator(maximumYear = 1987)
        testAndValidateYears(generator, 1920..1987)
    }

    @Test
    fun `honors year range when minimum and maximum are customized`() {
        val generator = DateGenerator(minimumYear = 1939, maximumYear = 1945)
        testAndValidateYears(generator, 1939..1945)
    }

    @Test
    fun `supports generating valid dates when an invalid day is chosen`() {
        val generator = DateGenerator()
        generator.month of 4
        generator.day of 31

        val date = generator.generate()
        assertEquals(4, date.monthValue)
        assertEquals(30, date.dayOfMonth)
    }

    @Test
    fun `empty DSL generates a date`() {
        val date = date { }
        assertNotNull(date)
        assertInstanceOf(LocalDate::class.java, date)
    }

    @Test
    fun `DSL supports setting year`() {
        val date = date {
            year of 1991
        }
        assertEquals(1991, date.year)
    }

    @Test
    fun `DSL supports setting month`() {
        val date = date {
            month of 8
        }
        assertEquals(8, date.monthValue)
    }

    @Test
    fun `DSL supports setting day`() {
        val date = date {
            day of 15
        }
        assertEquals(15, date.dayOfMonth)
    }

    @Test
    fun `DSL supports setting all fields`() {
        val date = date {
            year of 1991
            month of 8
            day of 15
        }
        assertEquals(LocalDate.of(1991, 8, 15), date)
    }

    private fun testAndValidateYears(generator: DateGenerator, yearRange: IntRange) {
        (1..10_000).forEach {
            val date = generator.generate()
            assertTrue(yearRange.contains(date.year))
        }
    }
}
