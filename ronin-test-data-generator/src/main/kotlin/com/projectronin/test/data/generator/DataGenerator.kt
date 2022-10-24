package com.projectronin.test.data.generator

/**
 * A DataGenerator is a class capable of generating a value of type [T]. A DataGenerator may also be provided a static
 * value which will always be produced by requests to generate that specific type.
 *
 * Note that DataGenerator itself does not indicate support for nullable-types; however, [T] does support nullable-types.
 * An extending class is allowed to offer whatever types, nullable or non-nullable, that they would like. For example:
 *
 *    val value1 : DataGenerator<String?>
 *
 * In general, if you intend to provide a value that is never generated and only provides a static value if supplied,
 * use [NullDataGenerator].
 *
 * It is strongly suggested that code utilizing DataGenerators be designed as a DSL (domain specific language) allowing
 * for a more fluid and readable code.
 */
abstract class DataGenerator<T> {
    private var staticValue: T? = null

    /**
     * Generates the internal representation of the supported type.
     */
    protected abstract fun generateInternal(): T

    /**
     * Returns the value associated to this DataGenerator. This could be either a provided static value or a dynamically
     * generated one.
     */
    fun generate(): T = staticValue ?: generateInternal()

    /**
     * Sets that static value for this generator to [value].
     *
     * This method is defined as infix to allow for a more readable, DSL-style of setting the value, such as:
     *
     *     value1 of "Example"
     */
    infix fun of(value: T) {
        staticValue = value
    }
}
