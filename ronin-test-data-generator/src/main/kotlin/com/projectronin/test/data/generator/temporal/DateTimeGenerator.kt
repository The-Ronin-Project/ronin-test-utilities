package com.projectronin.test.data.generator.temporal

import com.projectronin.test.data.generator.DataGenerator
import java.time.ZoneOffset
import java.time.ZonedDateTime

open class DateTimeGenerator(private val dateGenerator: DateGenerator = DateGenerator()) :
    DataGenerator<ZonedDateTime>() {
    private val timeGenerator = TimeGenerator()

    override fun generateInternal(): ZonedDateTime =
        ZonedDateTime.of(dateGenerator.generate(), timeGenerator.generate(), ZoneOffset.UTC)
}
