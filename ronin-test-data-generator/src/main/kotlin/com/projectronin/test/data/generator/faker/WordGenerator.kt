package com.projectronin.test.data.generator.faker

class WordGenerator : FakerDataGenerator<String>() {
    override fun generateInternal(): String = randomWord()
}
