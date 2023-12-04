fun main() {

    fun part1(input: List<String>): Int {
        val games = input.map(String::toGame)
        val validGames = games.filter { it.isValid(12, 13, 14) }
        return validGames.sumOf { it.id }
    }

    fun part2(input: List<String>): Int {
        val games = input.map(String::toGame)
        val powers = games.map(Game::power)
        return powers.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    val testInput2 = readInput("Day02_test")
    check(part2(testInput2) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

data class Reveal(var r: Int=0, var g: Int=0, var b: Int=0)

fun String.toReveal() : Reveal {
    val tokens = this.split(',').map(String::trim)
    val reveal = Reveal()
    tokens.forEach {
        val subTokens = it.split(' ').map(String::trim)
        when(subTokens[1]) {
            "red" -> reveal.r = subTokens[0].toInt()
            "green" -> reveal.g = subTokens[0].toInt()
            "blue" -> reveal.b = subTokens[0].toInt()
        }
    }
    return reveal
}

data class Game(val id: Int, val reveals: List<Reveal>) {
    val maxR : Int get() = reveals.maxOf { it.r }
    val maxG : Int get() = reveals.maxOf { it.g }
    val maxB : Int get() = reveals.maxOf { it.b }

    fun isValid(bagR: Int, bagG: Int, bagB: Int) = maxR <= bagR && maxG <= bagG && maxB <= bagB

    val power : Int get() = maxR * maxG * maxB
}

fun String.toGame() : Game {
    val tokens = this.split(':').map(String::trim)
    val gameId = tokens[0].split(' ')[1].toInt()
    val reveals = tokens[1].split(';').map(String::toReveal)
    return Game(gameId, reveals)
}