fun main() {

  fun part1(input: List<String>): Int {
    val hands = input.map {
      val tokens = it.split(" ")
      tokens[0].toDesertHand() to tokens[1].toInt()
    }.sortedBy { it.first }

    return hands.mapIndexed { i, pair -> (i + 1) * pair.second }.sum()
  }

  fun part2(input: List<String>): Int {
    return 0
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day07_test")
  check(part1(testInput) == 6440)
  check(part2(testInput) == 0)


  val input = readInput("Day07")
  part1(input).println()
  part2(input).println()

}

enum class DesertCard {
  `2`,  `3`,  `4`,  `5`,  `6`,  `7`,  `8`,  `9`,  T,  J,  Q,  K,  A
}

data class DesertHand(val cards : List<DesertCard>) : Comparable<DesertHand> {
  val fiveOfAKind: Boolean get() = cards.groupBy { it }.any { it.value.size == 5 }
  val fourOfAKind: Boolean get() = cards.groupBy { it }.any { it.value.size == 4 }
  val threeOfAKind: Boolean get() = cards.groupBy { it }.any { it.value.size == 3 }
  val onePair: Boolean get() = cards.groupBy { it }.any { it.value.size == 2 }
  val twoPair: Boolean get() = cards.groupBy { it }.filter { it.value.size == 2 }.size == 2
  val fullHouse: Boolean get() = threeOfAKind && onePair

  val score: Int get() = if (fiveOfAKind) 8
  else if (fourOfAKind) 7
  else if (fullHouse) 6
  else if (threeOfAKind) 5
  else if (twoPair) 4
  else if (onePair) 3
  else 2

  override fun compareTo(other: DesertHand): Int {
    val diff = this.score - other.score
    if (diff != 0) return diff
    else {
      (this.cards zip other.cards).forEach {
        val cDiff = it.first.compareTo(it.second)
        if (cDiff != 0) return cDiff
      }
    }
    return 0
  }

}

fun String.toDesertHand() : DesertHand =
  DesertHand(this.map {
    DesertCard.valueOf(it.toString())
  })