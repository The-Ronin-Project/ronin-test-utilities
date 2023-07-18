package com.projectronin.test.data.generator.faker

/**
 * Generates a random URI/URL string.
 */
class UriGenerator : FakerDataGenerator<String>() {
    override fun generateInternal(): String = randomUri()
}
