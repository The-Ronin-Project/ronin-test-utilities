package com.projectronin.test.data.generator.temporal

import java.time.ZonedDateTime

/**
 * The FutureDateTimeGenerator is an extension of [DateTimeGenerator] providing an exclusively future date.
 */
class FutureDateTimeGenerator : DateTimeGenerator(FutureDateGenerator())

/**
 * DSL-friendly method for creating and configuring a [ZonedDateTime] through a [FutureDateTimeGenerator]
 */
fun futureDateTime(block: FutureDateTimeGenerator.() -> Unit): ZonedDateTime {
    val dateTime = FutureDateTimeGenerator()
    dateTime.apply(block)
    return dateTime.generate()
}
