package com.projectronin.test.data.generator

/**
 * A NullDataGenerator provides a [DataGenerator] that never produces a generated version. Only a provided static value
 * will be returned from NullDataGenerators.
 */
open class NullDataGenerator<T> : DataGenerator<T?>() {
    override fun generateInternal(): T? = null
}
