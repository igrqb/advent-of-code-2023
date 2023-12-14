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

    fun part2(input: List<String>): Long {
        val direction = input[0]
        val nodeMap = input.takeLast(input.size - 2)
            .map { it.toDay08Node() }
            .associateBy { it.id }


        var currentNodeIds = nodeMap.keys.filter { it.endsWith("A") }
        var step = 0
        val match = List(currentNodeIds.size) { false }.toMutableList()
        val stepsToFirstMatch = List(currentNodeIds.size) { 0 }.toMutableList()

        while (!match.all { it }) {
            if (direction[step % direction.length] == 'R') {
                currentNodeIds = currentNodeIds.map { nodeMap[it]!!.r }
            } else if (direction[step % direction.length] == 'L') {
                currentNodeIds = currentNodeIds.map { nodeMap[it]!!.l }
            } else {
                throw RuntimeException("Invalid direction detected")
            }
            step++
            val currentMatches = currentNodeIds.map { it.endsWith("Z") }
            currentMatches.forEachIndexed { i, b ->
                if (!match[i] && b) {
                    match[i] = true
                    stepsToFirstMatch[i] = step
                    println("$currentNodeIds : $step")
                }
            }
        }

        var steps = stepsToFirstMatch[0].toLong()
        stepsToFirstMatch.forEach { steps = lcm(steps, it.toLong()) }
        return steps
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 2)
    val testInput2 = readInput("Day08_test2")
    check(part1(testInput2) == 6)
    val testInput3 = readInput("Day08_test3")
    check(part2(testInput3) == 6L)


    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()

}

data class Day08Node(val id: String, val l: String, val r: String)

fun String.toDay08Node(): Day08Node {
    val tokens = this.split(" = (", ", ", ")")
        .map { it.trim() }
        .filter { it.isNotBlank() }
    return Day08Node(tokens[0], tokens[1], tokens[2])
}

// See https://stackoverflow.com/a/4202033/2049647
fun gcf(a: Long, b: Long): Long {
    return if (b == 0L) a
    else gcf(b, a % b)
}

fun lcm(a: Long, b: Long): Long {
    return a * b / gcf(a, b)
}