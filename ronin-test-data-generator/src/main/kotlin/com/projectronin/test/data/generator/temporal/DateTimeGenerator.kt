package com.projectronin.test.data.generator.temporal

import com.projectronin.test.data.generator.DataGenerator
import java.time.ZoneOffset
import java.time.ZonedDateTime

/**
 * The DateTimeGenerator will generate a UTC-based [ZonedDateTime] based off the [dateGenerator].
 *
 * If a static value is set, there is no guarantee provided that the
 */
open class DateTimeGenerator(val date: DateGenerator = DateGenerator()) :
    DataGenerator<ZonedDateTime>() {
    val time = TimeGenerator()

    override fun generateInternal(): ZonedDateTime =
        ZonedDateTime.of(date.generate(), time.generate(), ZoneOffset.UTC)
}

/**
 * DSL-friendly method for creating and configuring a [ZonedDateTime] through a [DateTimeGenerator]
 */
fun dateTime(block: DateTimeGenerator.() -> Unit): ZonedDateTime {
    val dateTime = DateTimeGenerator()
    dateTime.apply(block)
    return dateTime.generate()
}
