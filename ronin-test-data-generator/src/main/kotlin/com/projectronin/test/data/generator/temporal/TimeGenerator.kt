package com.projectronin.test.data.generator.temporal

import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.faker.IntGenerator
import java.time.LocalTime

/**
 * The TimeGenerator will generate a random LocalTime with nanosecond precision.
 */
class TimeGenerator : DataGenerator<LocalTime>() {
    val hour = IntGenerator(0, 23)
    val minute = IntGenerator(0, 59)
    val second = IntGenerator(0, 59)
    val nanosecond = IntGenerator(0, 999_999_999)

    override fun generateInternal(): LocalTime =
        LocalTime.of(
            hour.generate(),
            minute.generate(),
            second.generate(),
            nanosecond.generate()
        )
}

/**
 * DSL-friendly method for creating and configuring a [LocalTime] through a [TimeGenerator]
 */
fun time(block: TimeGenerator.() -> Unit): LocalTime {
    val time = TimeGenerator()
    time.apply(block)
    return time.generate()
}
