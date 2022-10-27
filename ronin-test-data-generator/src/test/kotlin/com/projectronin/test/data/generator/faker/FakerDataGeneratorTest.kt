package com.projectronin.test.data.generator.faker

import com.github.javafaker.Faker
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class FakerDataGeneratorTest {
    @Test
    fun `provides access to faker`() {
        val faker = Impl().getFakerForTest()
        assertInstanceOf(Faker::class.java, faker)
    }

    @Test
    fun `generates a random word`() {
        val word = Impl().getRandomWordForTest()
        assertNotNull(word)
    }

    @Test
    fun `generates a random integer`() {
        val minimum = 1
        val maximum = 10

        // Run this a good number of times just to verify we weren't lucky the first time.
        (1..10_000).forEach {
            val int = Impl().getRandomIntForTest(minimum, maximum)
            assertTrue((minimum..maximum).contains(int))
        }
    }

    private class Impl : FakerDataGenerator<String>() {
        override fun generateInternal(): String {
            TODO("Not yet implemented")
        }

        fun getFakerForTest(): Faker = faker
        fun getRandomWordForTest(): String = randomWord()
        fun getRandomIntForTest(minimum: Int, maximum: Int): Int = randomInt(minimum, maximum)
    }
}
