package com.projectronin.test.data.generator.temporal

import java.time.LocalDate

/**
 * The FutureDateGenerator is an extension of [DateGenerator] providing an exclusively future date.
 */
class FutureDateGenerator : DateGenerator {
    constructor() : super(minimumYear = LocalDate.now().year + 1)
    constructor(maximumYear: Int) : super(minimumYear = LocalDate.now().year + 1, maximumYear = maximumYear)
    constructor(minimumYear: Int, maximumYear: Int) : super(minimumYear = minimumYear, maximumYear = maximumYear)
}

/**
 * DSL-friendly method for creating and configuring a [LocalDate] through a [FutureDateGenerator]
 */
fun futureDate(block: FutureDateGenerator.() -> Unit): LocalDate {
    val date = FutureDateGenerator()
    date.apply(block)
    return date.generate()
}
