# ronin-test-data-generator

A test data generator is a class or series of classes capable of generating realistic or semi-realistic data for use in
tests. A data generator supports both a generation flow and a static value flow.

## Creating a new generator

Creating a data generator is as simple as
extending [DataGenerator](src/main/kotlin/com/projectronin/test/data/generator/DataGenerator.kt).

```kotlin
class MyDataGenerator : DataGenerator<MyData>() {
    val value1: DataGenerator<String> = WordGenerator()
    val value2: DataGenerator<Int> = IntGenerator()
    val value3: DataGenerator<String> = NullDataGenerator()

    override fun generateInternal(): MyData =
        MyData(
            value1.generate(),
            value2.generate(),
            value3.generate()
        )
}
```

It is highly recommended that a DSL-friendly version of the generator be created by adding a method like the following,
for the example above:

```kotlin
fun myData(block: MyDataGenerator.() -> Unit): MyData {
    val myData = MyDataGenerator()
    myData.apply(block)
    return myData.generate()
}
```

By adding the above method, you can now create a fully generated MyData with a simple invocation:

```kotlin
val generated = myData { }
```

If you would like to provide a static value for one or more fields, you can do that using the `of` infix method on all
DataGenerators. The below example will set value2 and value3, while value1 will continue to be generated.

```kotlin
val generated = myData {
    value2 of 25
    value3 of "not null"
}
```

## Included generators

This library includes several generators or base generators that can assist with some common needs:

* [ListDataGenerator](src/main/kotlin/com/projectronin/test/data/generator/collection/ListDataGenerator.kt) - for
  generating Lists of data based off generators
* [EmptyListDataGenerator](src/main/kotlin/com/projectronin/test/data/generator/collection/EmptyListDataGenerator.kt) -
  a special type of List data generator focused on Lists that are empty by default
* [FakerDataGenerator](src/main/kotlin/com/projectronin/test/data/generator/faker/FakerDataGenerator.kt) - a base class
  for data generators wanting to use [Faker](https://github.com/DiUS/java-faker).
    * Several classes extending this are also provided, such
      as [IntGenerator](src/main/kotlin/com/projectronin/test/data/generator/faker/IntGenerator.kt),
      [WordGenerator](src/main/kotlin/com/projectronin/test/data/generator/faker/WordGenerator.kt),
      and [NameGenerator](src/main/kotlin/com/projectronin/test/data/generator/faker/NameGenerator.kt)
* [DateGenerator](src/main/kotlin/com/projectronin/test/data/generator/temporal/DateGenerator.kt) - as well as versions
  focused exclusively on past or future dates.
* [TimeGenerator](src/main/kotlin/com/projectronin/test/data/generator/temporal/TimeGenerator.kt)
* [DateTimeGenerator](src/main/kotlin/com/projectronin/test/data/generator/temporal/DateTimeGenerator.kt) - as well as
  versions focused exclusively on dates and times for past or future dates.
