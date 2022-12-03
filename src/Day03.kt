abstract class CharacterPriority {
    abstract fun intersection(): Char

    fun calculate(): Int {
        return getItemPriority(intersection())
    }

    private fun getItemPriority(c: Char): Int {
        return priorityString.indexOf(c) + 1
    }

    companion object {
        private const val priorityString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
    }
}

class Rucksack(private val input: String): CharacterPriority() {
    override fun intersection(): Char {
        val split = (input.length / 2)
        val first = input.slice(0 until split).asIterable()
        val second = input.slice(split until input.length).asIterable()
        return first.intersect(second).first()
    }
}

class Badge(private val input: List<Set<Char>>): CharacterPriority() {
    init {
        assert(input.size == 3)
    }

    override fun intersection(): Char {
        return input[0].intersect(input[1]).intersect(input[2]).first()
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { Rucksack(it).calculate() }
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        for (i in input.indices step 3) {
            val group = input.slice(i..i+2).map { it.toSet() }
            sum += Badge(group).calculate()
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157 )
    check(part2(testInput) == 70 )

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
