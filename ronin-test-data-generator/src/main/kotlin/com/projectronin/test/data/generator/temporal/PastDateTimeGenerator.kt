package com.projectronin.test.data.generator.temporal

import java.time.ZonedDateTime

/**
 * The PastDateTimeGenerator is an extension of [DateTimeGenerator] providing an exclusively past date.
 */
class PastDateTimeGenerator : DateTimeGenerator(PastDateGenerator())

/**
 * DSL-friendly method for creating and configuring a [ZonedDateTime] through a [PastDateTimeGenerator]
 */
fun pastDateTime(block: PastDateTimeGenerator.() -> Unit): ZonedDateTime {
    val dateTime = PastDateTimeGenerator()
    dateTime.apply(block)
    return dateTime.generate()
}
