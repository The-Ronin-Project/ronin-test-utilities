package com.projectronin.test.data.generator.temporal

import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.faker.IntGenerator
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.ResolverStyle

/**
 * A DateGenerator is capable of creating a random, valid [LocalDate] between the minimum and maximum years. By default,
 * this data may be in the future or the past. If you need an explicit past date, please use [PastDateGenerator]. Likewise,
 * [FutureDateGenerator] can be used for an explicitly future date.
 */
open class DateGenerator(
    minimumYear: Int = 1920,
    maximumYear: Int = LocalDate.now().year + 10
) : DataGenerator<LocalDate>() {
    val year = IntGenerator(minimumYear, maximumYear)
    val month = IntGenerator(1, 12)
    val day = IntGenerator(1, 31)

    private val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE.withResolverStyle(ResolverStyle.SMART)

    override fun generateInternal(): LocalDate =
        LocalDate.parse("%d-%02d-%02d".format(year.generate(), month.generate(), day.generate()), dateFormatter)
}

/**
 * DSL-friendly method for creating and configuring a [LocalDate] through a [DateGenerator]
 */
fun date(block: DateGenerator.() -> Unit): LocalDate {
    val date = DateGenerator()
    date.apply(block)
    return date.generate()
}
