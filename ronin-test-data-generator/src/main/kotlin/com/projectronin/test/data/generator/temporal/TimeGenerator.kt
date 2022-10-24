package com.projectronin.test.data.generator.temporal

import com.projectronin.test.data.generator.DataGenerator
import com.projectronin.test.data.generator.faker.IntGenerator
import java.time.LocalTime

class TimeGenerator : DataGenerator<LocalTime>() {
    private val hour = IntGenerator(0, 23)
    private val minute = IntGenerator(0, 59)
    private val second = IntGenerator(0, 59)
    private val nanosecond = IntGenerator(0, 999_999_999)

    override fun generateInternal(): LocalTime =
        LocalTime.of(
            hour.generate(),
            minute.generate(),
            second.generate(),
            nanosecond.generate()
        )
}
