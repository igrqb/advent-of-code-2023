fun main() {

    fun part1(input: List<String>): Int {
        val gridNumbers = input.flatMapIndexed { index: Int, s: String -> s.toGridNumbers(index) }
        val symbolCoords = input.flatMapIndexed { index: Int, s: String -> s.symbolCoords(index) }
        val symbolBoundaries = symbolCoords.flatMap { it.boundary() }.toSet()
        val gridNumbersTouchingSymbols = gridNumbers.filter { it.touchesSymbols(symbolBoundaries) }
        val gridNumbersNotTouchingSymbols = gridNumbers.filter { !it.touchesSymbols(symbolBoundaries) }

        return gridNumbersTouchingSymbols.sumOf { it.value }
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
//    val testInput2 = readInput("Day02_test")
//    check(part2(testInput2) == 2286)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}

data class GridNumber(val row: Int, val cols: List<Int>, val value: Int) {
    val coords : Set<Pair<Int, Int>> get() = cols.map { row to it }.toSet()

    fun touchesSymbols(symbolBoundaries: Set<Pair<Int, Int>>) = coords.intersect(symbolBoundaries).isNotEmpty()
}

fun String.toGridNumbers(row: Int) : List<GridNumber> {
    val cleaned = this.map { if (it.isDigit()) it else ' ' }.joinToString("")
    if (cleaned.isBlank()) return emptyList()
    val digitIdx = cleaned.mapIndexed { index, c -> index to c }.filter { it.second.isDigit() }.map { it.first }
    val numbers = cleaned.trim().split(' ').map { it.trim().toIntOrNull() }.filterNotNull()
    val gridNumbers = mutableListOf<GridNumber>()
    var cumulativeIdx = 0
    for (i in numbers.indices) {
        val numberLength = numbers[i].toString().length
        gridNumbers.add(GridNumber(row, digitIdx.subList(cumulativeIdx, cumulativeIdx+numberLength), numbers[i]))
        cumulativeIdx += numberLength
    }
    return gridNumbers
}

fun String.symbolCoords(row: Int) : List<Pair<Int, Int>> =
    this.mapIndexed { index, c ->  index to c }
        .filter { !it.second.isLetterOrDigit() && it.second != '.' }
        .map { row to it.first }

fun Pair<Int, Int>.boundary() : List<Pair<Int, Int>> =
    listOf(first-1 to second-1, first-1 to second, first-1 to second+1) +
    listOf(first   to second-1,                    first   to second+1) +
    listOf(first+1 to second-1, first+1 to second, first+1 to second+1)