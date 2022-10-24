package com.projectronin.test.data.generator.temporal

import java.time.LocalDate

class FutureDateGenerator : DateGenerator(minimumYear = LocalDate.now().year + 1)
