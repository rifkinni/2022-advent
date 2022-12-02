enum class RockPaperScissors(val score: Int)
{
    ROCK(1),
    PAPER(2),
    SCISSORS(3);

    fun winsOver(): RockPaperScissors {
        return when(this) {
            ROCK -> SCISSORS
            PAPER -> ROCK
            SCISSORS -> PAPER
        }
    }

    fun losesTo(): RockPaperScissors {
        return when(this) {
            ROCK -> PAPER
            PAPER -> SCISSORS
            SCISSORS -> ROCK
        }
    }

    companion object {
        fun fromChar(input: Char): RockPaperScissors {
            return when(input) {
                'A', 'X' -> ROCK
                'B', 'Y' -> PAPER
                'C', 'Z' -> SCISSORS
                else -> throw Exception()
            }
        }
    }
}

enum class Outcome(val score: Int)
{
    WIN(6),
    DRAW(3),
    LOSE(0);

    fun shapeForOutcome(opponent: RockPaperScissors): RockPaperScissors {
        return when (this) {
            DRAW -> opponent
            LOSE -> opponent.winsOver()
            WIN -> opponent.losesTo()
        }
    }

    companion object {
        fun fromChar(input: Char): Outcome {
            return when(input) {
                'X' -> LOSE
                'Y' -> DRAW
                'Z' -> WIN
                else -> throw Exception()
            }
        }
    }
}

class Game(private val opponent: RockPaperScissors, private val player: RockPaperScissors)
{
    constructor(opponent: RockPaperScissors, outcome: Outcome) : this(opponent, outcome.shapeForOutcome(opponent))

    fun score(): Int
    {
        return player.score + scoreWinner()
    }

    private fun scoreWinner(): Int {
        val outcome = if (player == opponent) {
            Outcome.DRAW
        } else if (player.losesTo() == opponent) {
            Outcome.LOSE
        } else if (player.winsOver() == opponent) {
            Outcome.WIN
        } else { throw Exception() }
        return outcome.score
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { Game(RockPaperScissors.fromChar(it[0]), RockPaperScissors.fromChar(it[2])).score() }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { Game(RockPaperScissors.fromChar(it[0]), Outcome.fromChar(it[2])).score() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15 )
    check(part2(testInput) == 12 )

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
