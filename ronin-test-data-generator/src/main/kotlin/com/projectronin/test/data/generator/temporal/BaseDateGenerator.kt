package com.projectronin.test.data.generator.temporal

import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.faker.IntGenerator
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.ResolverStyle

/**
 * Base generator for creating valid Dates of any format.
 */
abstract class BaseDateGenerator<T>(
    minimumYear: Int = 1920,
    maximumYear: Int = LocalDate.now().year + 10
) : DataGenerator<T>() {
    abstract fun convert(localDate: LocalDate): T

    val year = IntGenerator(minimumYear, maximumYear)
    val month = IntGenerator(1, 12)
    val day = IntGenerator(1, 31)

    private val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE.withResolverStyle(ResolverStyle.SMART)

    override fun generateInternal(): T =
        convert(
            LocalDate.parse(
                "%d-%02d-%02d".format(year.generate(), month.generate(), day.generate()),
                dateFormatter
            )
        )
}
