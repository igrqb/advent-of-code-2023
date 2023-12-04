fun main() {
    val mapping = mapOf(
        "1" to 1,
        "2" to 2,
        "3" to 3,
        "4" to 4,
        "5" to 5,
        "6" to 6,
        "7" to 7,
        "8" to 8,
        "9" to 9,
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9
    )

    val reverseMapping = mapOf(
        "1" to 1,
        "2" to 2,
        "3" to 3,
        "4" to 4,
        "5" to 5,
        "6" to 6,
        "7" to 7,
        "8" to 8,
        "9" to 9,
        "eno" to 1,
        "owt" to 2,
        "eerht" to 3,
        "ruof" to 4,
        "evif" to 5,
        "xis" to 6,
        "neves" to 7,
        "thgie" to 8,
        "enin" to 9
    )

    fun part1(input: List<String>): Int {
        val first = input.map { it.first { c -> c.isDigit() }.digitToInt() }
        val last = input.map { it.last { c -> c.isDigit() }.digitToInt() }
        val comb = first.zip(last).map { it.first * 10 + it.second }
        return comb.sum()

        // Can anyone tell me why this doesn't work? :)
//        return input.parallelStream().map {
//            it.first { c -> c.isDigit() }.digitToInt()
//            + it.last { c -> c.isDigit() }.digitToInt()
//        }.toList().sum()

    }

    fun String.firstDigit() : Int {
        val idx = findAnyOf(mapping.keys)!!
        return mapping[idx.second]!!
    }

    fun String.lastDigit() : Int {
        val idx = reversed().findAnyOf(reverseMapping.keys)!!
        return reverseMapping[idx.second]!!
    }

    fun part2(input: List<String>): Int {
        val first = input.map { it.firstDigit() }
        val last = input.map { it.lastDigit() }
        val comb = first.zip(last).map { it.first * 10 + it.second }
        return comb.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)
    val testInput2 = readInput("Day01_test2")
    check(part2(testInput2) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
