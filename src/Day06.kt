import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.sqrt

fun main() {

  fun part1(input: List<String>): Int {
    val times = input[0].split("Time:", " ").mapNotNull { it.trim().toIntOrNull() }
    val recordDistances = input[1].split("Distance:", " ").mapNotNull { it.trim().toIntOrNull() }
    val possibleDistances = times.map { distances(it) }
    val possibleWins = (recordDistances zip possibleDistances).map { it.second.filter { d -> d > it.first }.size }
    return possibleWins.reduce { acc, i -> i * acc }
  }

  fun part2(input: List<String>): Int {
    val time = input[0].replace("Time:", "").replace(" ", "").toLong()
    val recordDistance = input[1].replace("Distance:", "").replace(" ", "").toLong()
    val root1 = ceil((time - sqrt(time.toDouble().pow(2.0) - 4 * recordDistance)) / 2.0).toInt()
    val root2 = floor((time + sqrt(time.toDouble().pow(2.0) - 4 * recordDistance)) / 2.0).toInt()
    return root2 - root1 + 1
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day06_test")
  check(part1(testInput) == 288)
  check(part2(testInput) == 71503)


  val input = readInput("Day06")
  part1(input).println()
  part2(input).println()

}

fun distance(timeLimit: Int, timePressed: Int) = timePressed * (timeLimit - timePressed)

fun distances(timeLimit: Int) = (0..timeLimit).map { distance(timeLimit, it) }