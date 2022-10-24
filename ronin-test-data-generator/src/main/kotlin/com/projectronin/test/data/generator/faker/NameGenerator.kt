package com.projectronin.test.data.generator.faker

import com.github.javafaker.Name
import kotlin.reflect.KFunction1

class NameGenerator(private val function: KFunction1<Name, String>) : FakerDataGenerator<String>() {
    private val name = faker.name()

    override fun generateInternal(): String = function.call(name)
}
