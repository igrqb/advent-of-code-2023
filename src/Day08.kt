fun main() {

  fun part1(input: List<String>): Int {
    val direction = input[0]
    val nodeMap = input.takeLast(input.size - 2)
            .map { it.toDay08Node() }
            .associateBy { it.id }


    var currentNodeId = "AAA"
    var step = 0

    while (currentNodeId != "ZZZ") {
      if (direction[step % direction.length] == 'R') {
        currentNodeId = nodeMap[currentNodeId]!!.r
      } else if (direction[step % direction.length] == 'L') {
        currentNodeId = nodeMap[currentNodeId]!!.l
      } else {
        throw RuntimeException("Invalid direction detected")
      }
      step++

    }

    return step
  }

  fun part2(input: List<String>): Int {
    return 0
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day08_test")
  check(part1(testInput) == 2)
  val testInput2 = readInput("Day08_test2")
  check(part1(testInput2) == 6)
  check(part2(testInput) == 0)


  val input = readInput("Day08")
  part1(input).println()
  part2(input).println()

}

data class Day08Node(val id: String, val l: String, val r: String)

fun String.toDay08Node() : Day08Node {
  val tokens = this.split(" = (", ", ", ")")
          .map { it.trim() }
          .filter { it.isNotBlank() }
  return Day08Node(tokens[0], tokens[1], tokens[2])
}