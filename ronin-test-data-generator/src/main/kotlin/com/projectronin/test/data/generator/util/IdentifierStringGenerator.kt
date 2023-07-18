package com.projectronin.test.data.generator.util

import com.projectronin.test.data.generator.DataGenerator
import java.util.concurrent.ThreadLocalRandom
import kotlin.streams.asSequence

/**
 * Generates a random string identifier value using only a-z, A-Z, and 0-9.
 */
open class IdentifierStringGenerator(
    private val minimumLength: Int = 15
) : DataGenerator<String>() {
    private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    override fun generateInternal(): String = ThreadLocalRandom.current()
        .ints(minimumLength.toLong(), 0, charPool.size)
        .asSequence()
        .map(charPool::get)
        .joinToString("")
}
