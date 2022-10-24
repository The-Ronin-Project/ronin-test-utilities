package com.projectronin.test.data.generator.collection

import com.projectronin.test.data.generator.NullDataGenerator

class EmptyListDataGenerator<T> : ListDataGenerator<T?>(0, NullDataGenerator())
