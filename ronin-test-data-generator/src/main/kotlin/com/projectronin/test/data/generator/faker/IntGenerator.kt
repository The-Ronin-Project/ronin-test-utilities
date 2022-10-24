package com.projectronin.test.data.generator.faker

class IntGenerator(private val minimum: Int = 1, private val maximum: Int = 50) : FakerDataGenerator<Int>() {
    override fun generateInternal(): Int = randomInt(minimum, maximum)
}
