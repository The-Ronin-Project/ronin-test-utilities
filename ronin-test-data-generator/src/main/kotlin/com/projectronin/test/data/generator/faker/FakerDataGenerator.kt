package com.projectronin.test.data.generator.faker

import com.projectronin.test.data.generator.DataGenerator
import net.datafaker.Faker
import net.datafaker.providers.base.Internet

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
     * Generates a random sentence using Faker.
     */
    protected fun randomSentence(): String = faker.lorem().sentence()

    /**
     * Generates a random integer between [minimum] and [maximum] using Faker.
     */
    protected fun randomInt(minimum: Int, maximum: Int) = faker.number().numberBetween(minimum, maximum)

    /**
     * Generates a random boolean using Faker.
     */
    protected fun randomBool(): Boolean = faker.bool().bool()

    /**
     * Generates a random medical Organization or Location name using Faker.
     */
    protected fun randomHospital(): String = faker.medical().hospitalName()

    /**
     * Generates a random URI/URL string using Faker.
     */
    protected fun randomUri(): String = faker.internet().url()
}
