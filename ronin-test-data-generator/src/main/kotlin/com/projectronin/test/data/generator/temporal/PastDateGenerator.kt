package com.projectronin.test.data.generator.temporal

import java.time.LocalDate

/**
 * The PastDateGenerator is an extension of [DateGenerator] providing an exclusively past date.
 */
class PastDateGenerator : DateGenerator {
    constructor() : super(maximumYear = LocalDate.now().year - 1)
    constructor(minimumYear: Int) : super(minimumYear = minimumYear, maximumYear = LocalDate.now().year - 1)
    constructor(minimumYear: Int, maximumYear: Int) : super(minimumYear = minimumYear, maximumYear = maximumYear)
}

/**
 * DSL-friendly method for creating and configuring a [LocalDate] through a [PastDateGenerator]
 */
fun pastDate(block: PastDateGenerator.() -> Unit): LocalDate {
    val date = PastDateGenerator()
    date.apply(block)
    return date.generate()
}
