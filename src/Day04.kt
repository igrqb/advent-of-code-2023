import java.lang.Math.pow
import kotlin.math.pow

fun main() {

    fun part1(input: List<String>): Int {
        val cards = input.map(String::toCard)
        val scores = cards.map { it.score }
        return cards.sumOf { it.score }
    }

    fun part2(input: List<String>): Int {
        val cards = input.map(String::toCard)
        val matches = cards.map { it.matches }
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)


    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()

}

data class Card(val winningNumbers: List<Int>, val numbersHave: List<Int>) {
    val matches: Int get() = winningNumbers.toSet().intersect(numbersHave.toSet()).size
    val score: Int get() = if (matches == 0) 0 else 2.0.pow(matches.toDouble() - 1.0).toInt()
}

fun String.toCard() : Card {
    val tokens = this.split(':')
    val groups = tokens[1].split('|')
    return Card(
        groups[0].split(' ').mapNotNull { it.trim().toIntOrNull() },
        groups[1].split(' ').mapNotNull { it.trim().toIntOrNull() }
    )
}