package com.projectronin.test.data.generator.faker

import com.github.javafaker.Name
import kotlin.reflect.KFunction1

/**
 * NameGenerator relies on Faker's [Name] to generate a name based off the given [function]. For example, the following
 * example will create a first name:
 *
 *     NameGenerator(Name::firstName).generate()
 *
 * All no-argument, String-producing methods on Name are acceptable for the [function].
 */
class NameGenerator(private val function: KFunction1<Name, String>) : FakerDataGenerator<String>() {
    private val name = faker.name()

    override fun generateInternal(): String = function.call(name)
}
