package com.projectronin.test.data.generator.faker

import com.github.javafaker.Faker
import com.projectronin.test.data.generator.DataGenerator

abstract class FakerDataGenerator<T : Any> : DataGenerator<T>() {
    protected val faker = Faker()

    protected fun randomWord(): String = faker.lorem().word()

    protected fun randomInt(minimum: Int, maximum: Int) = faker.number().numberBetween(minimum, maximum)
}
