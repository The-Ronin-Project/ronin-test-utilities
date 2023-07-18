package com.projectronin.test.data.generator.util

import com.projectronin.test.data.generator.faker.FakerDataGenerator

/**
 * Generates a random medical Organization or Location name.
 */
open class HospitalNameGenerator : FakerDataGenerator<String>() {
    override fun generateInternal(): String = randomHospital()
}

