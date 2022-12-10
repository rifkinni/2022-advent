import kotlin.math.abs

data class Coordinate(var x: Int, var y: Int) {
    private fun isTouching(other: Coordinate): Boolean {
        if (abs(this.x - other.x) <= 1 && abs(this.y - other.y) <= 1) {
            return true
        }
        return false
    }

    fun follow(lead: Coordinate) {
        if (isTouching(lead)) { return }

        val deltaX = lead.x - this.x
        val deltaY = lead.y - this.y

        if (abs(deltaX) == 2) {
            this.x += (deltaX/abs(deltaX))
            if (abs(deltaY) >= 1) { //diagonal
                this.y += (deltaY/abs(deltaY))
            }
        } else if (abs(deltaY) == 2) {
            this.y += (deltaY/abs(deltaY))
            if (abs(deltaX) >= 1) { //diagonal
                this.x += (deltaX/abs(deltaX))
            }
        }
    }
}

class Rope(size: Int) {
    private val head = Coordinate(0, 0)
    private val knots: List<Coordinate>
    val tail: Coordinate

    init {
        knots = (0 until size - 1).map { Coordinate(0, 0) }
        tail = knots.last()
    }

    fun parseInstructions(input: List<String>): Int {
        val tails: MutableSet<Coordinate> = mutableSetOf(tail.copy())

        for (line in input) {
            val (symbol, stepString ) = line.split(" ")
            val direction = Direction.fromChar(symbol[0])
            val steps = Integer.parseInt(stepString)

            for (i in 0 until steps) {
                move(direction)
                tails.add(tail.copy())
            }
        }
        return tails.size
    }

    private fun move(direction: Direction) {
        direction.move(head)
        knots[0].follow(head)
        for (i in 0 until knots.size - 1) {
            knots[i + 1].follow(knots[i])
        }
    }
}

enum class Direction(val symbol: Char) {
    UP('U'),
    DOWN('D'),
    LEFT('L'),
    RIGHT('R');

    fun move(coordinate: Coordinate) {
        when(this) {
            UP -> coordinate.y ++
            DOWN -> coordinate.y --
            LEFT -> coordinate.x --
            RIGHT -> coordinate.x ++
        }
    }

    companion object {
        fun fromChar(char: Char): Direction {
            return values().first { it.symbol == char }
        }
    }
}



fun main() {
    fun part1(input: List<String>): Int {
        return Rope(2).parseInstructions(input)
    }

    fun part2(input: List<String>): Int {
        return Rope(10).parseInstructions(input)
    }

    // test if implementation meets criteria from the description, like:
    val testCases = listOf(
        TestCase(0, 13, 1),
        TestCase(1, 88, 36)
    )
    val testInputs = readChunk("Day09_test")
        .split("\n\n")
        .map { it.split("\n").filter { it.isNotBlank() } }

    for (case in testCases) {
        check(part1(testInputs[case.index]) == case.part1)
        check(part2(testInputs[case.index]) == case.part2)
    }

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
