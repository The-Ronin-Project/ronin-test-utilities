package com.projectronin.test.data.generator.faker

/**
 * The WordGenerator generates a single word.
 */
class WordGenerator : FakerDataGenerator<String>() {
    override fun generateInternal(): String = randomWord()
}
