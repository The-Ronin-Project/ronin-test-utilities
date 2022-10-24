package com.projectronin.test.data.generator.temporal

import java.time.LocalDate

class PastDateGenerator : DateGenerator(maximumYear = LocalDate.now().year - 1)
