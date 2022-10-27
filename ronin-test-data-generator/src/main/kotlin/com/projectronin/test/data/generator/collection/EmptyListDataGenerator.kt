package com.projectronin.test.data.generator.collection

import com.projectronin.test.data.generator.NullDataGenerator

/**
 * Generates an empty List if no static value is provided.
 */
class EmptyListDataGenerator<T> : ListDataGenerator<T?>(0, NullDataGenerator()) {
    /**
     * This method is disabled for an empty List and will throw an [UnsupportedOperationException]
     */
    override infix fun generates(count: Int): ListDataGenerator<T?> {
        throw UnsupportedOperationException("generates is not supported on EmptyListDataGenerator")
    }

    /**
     * This method is disabled for an empty List and will throw an [UnsupportedOperationException]
     */
    override infix fun plus(value: T?): ListDataGenerator<T?> {
        throw UnsupportedOperationException("plus is not supported on EmptyListDataGenerator")
    }
}
