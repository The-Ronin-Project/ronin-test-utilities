package com.projectronin.test.data.generator.collection

import com.projectronin.test.data.generator.DataGenerator

open class ListDataGenerator<T>(private var count: Int, private val baseGenerator: DataGenerator<T>) :
    DataGenerator<List<T>?>() {
    private var additionalValues: MutableList<T> = mutableListOf()

    override fun generateInternal(): List<T>? {
        return (1..count).mapNotNull { baseGenerator.generate() } + additionalValues
    }

    infix fun generate(count: Int): ListDataGenerator<T> {
        this.count = count
        return this
    }

    infix fun plus(value: T): ListDataGenerator<T> {
        additionalValues.add(value)
        return this
    }
}
