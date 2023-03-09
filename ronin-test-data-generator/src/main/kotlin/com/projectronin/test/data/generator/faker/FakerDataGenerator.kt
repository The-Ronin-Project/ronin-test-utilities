package com.projectronin.test.data.generator.faker

import net.datafaker.Faker
import com.projectronin.test.data.generator.DataGenerator

/**
 * A FakerDataGenerator supplies [Faker](https://github.com/DiUS/java-faker) support to a DataGenerator.
 */
abstract class FakerDataGenerator<T : Any> : DataGenerator<T>() {
    protected val faker = Faker()

    /**
     * Generates a random word using Faker.
     */
    protected fun randomWord(): String = faker.lorem().word()

    /**
     * Generates a random integer between [minimum] and [maximum] using Faker.
     */
    protected fun randomInt(minimum: Int, maximum: Int) = faker.number().numberBetween(minimum, maximum)
}
