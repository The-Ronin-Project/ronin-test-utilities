package com.projectronin.test.data.generator.faker

class BooleanGenerator() : FakerDataGenerator<Boolean>() {
    override fun generateInternal(): Boolean = randomBool()
}
