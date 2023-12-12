import java.util.concurrent.atomic.AtomicLong
import kotlin.math.min
import kotlin.math.pow

fun main() {

  fun part1(input: List<String>): Long {
    val mapStart = input.mapIndexed { index, s -> index to s }
      .filter { it.second.endsWith("map:") }
      .map { it.first }

    val mapEnd = mapStart.takeLast(mapStart.size - 1)
      .map { it - 1 } + input.size - 1

    val recipeMaps = (mapStart zip mapEnd).map { input.subList(it.first, it.second).toRecipeMap() }

    val seeds = input[0].split("seeds: ", " ")
      .filter { it.isNotBlank() }
      .map { it.trim().toLong() }

    val locations = seeds.map { seed ->
      var output = seed
      recipeMaps.forEach { output = it.io(output) }
      seed to output
    }

    return locations.minOf { it.second }
  }

  fun part2(input: List<String>): Long {
    val mapStart = input.mapIndexed { index, s -> index to s }
      .filter { it.second.endsWith("map:") }
      .map { it.first }

    val mapEnd = mapStart.takeLast(mapStart.size - 1)
      .map { it - 1 } + input.size - 1

    val recipeMaps = (mapStart zip mapEnd).map { input.subList(it.first, it.second).toRecipeMap() }

    val seedRanges = input[0].split("seeds: ", " ")
      .filter { it.isNotBlank() }
      .map { it.trim().toLong() }

    val minLocation = AtomicLong(Long.MAX_VALUE)

    seedRanges.indices.filter { it % 2 == 0 }
      .map { seedRanges[it] until seedRanges[it] + seedRanges[it+1] }
      .parallelStream()
      .forEach { seedRange -> for (seed in seedRange) {
        var output = seed
        recipeMaps.forEach { output = it.io(output) }
        minLocation.set(min(minLocation.get(), output))
      }}

    return minLocation.get()
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day05_test")
  check(part1(testInput) == 35L)
  check(part2(testInput) == 46L)


  val input = readInput("Day05")
  part1(input).println()
  part2(input).println()

}

data class RecipeMap(val from: String, val to: String, val ioMaps: List<Pair<LongRange, LongRange>>) {
  fun io(input: Long) : Long {
    val selectedRange = ioMaps.firstOrNull { input in it.first }
    return if (selectedRange == null) input
    else selectedRange.second.first + input - selectedRange.first.first
  }
}

fun String.toIoMap() : Pair<LongRange, LongRange> {
  val tokens = this.split(" ").map { it.trim().toLong() }
  return LongRange(tokens[1], tokens[1] + tokens[2]) to LongRange(tokens[0], tokens[0] + tokens[2])
}

fun List<String>.toRecipeMap() : RecipeMap {
  val tokens = this[0].split("-to-", " ")
  val ioMaps = this.takeLast(this.size - 1)
    .filter { it.isNotBlank() }
    .map { it.toIoMap() }
  return RecipeMap(tokens[0], tokens[1], ioMaps)
}