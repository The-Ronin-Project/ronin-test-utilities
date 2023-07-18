package com.projectronin.test.data.generator.faker

/**
 * Generates a sentence for a note, comment, description, annotation, narrative.
 */
class SentenceGenerator : FakerDataGenerator<String>() {
    override fun generateInternal(): String = randomSentence()
}
