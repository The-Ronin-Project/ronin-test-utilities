package com.projectronin.test.data.generator.temporal

import java.time.LocalDate

/**
 * A DateGenerator is capable of creating a random, valid [LocalDate] between the minimum and maximum years. By default,
 * this data may be in the future or the past. If you need an explicit past date, please use [PastDateGenerator]. Likewise,
 * [FutureDateGenerator] can be used for an explicitly future date.
 */
open class DateGenerator(
    minimumYear: Int = 1920,
    maximumYear: Int = LocalDate.now().year + 10
) : BaseDateGenerator<LocalDate>(minimumYear, maximumYear) {
    override fun convert(localDate: LocalDate): LocalDate = localDate
}

/**
 * DSL-friendly method for creating and configuring a [LocalDate] through a [DateGenerator]
 */
fun date(block: DateGenerator.() -> Unit): LocalDate {
    val date = DateGenerator()
    date.apply(block)
    return date.generate()
}
