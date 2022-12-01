import java.util.Collections.max

class Day01 {
    fun parseSupplies(input: List<String>): List<Int> {
        return input.joinToString(",").split(",,").map { collection ->
            collection.split(",").map { num -> Integer.parseInt(num) }.sum()
        }
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val elfSupplies = Day01().parseSupplies(input)
        return max(elfSupplies)
    }

    fun part2(input: List<String>): Int {
        val elfSupplies = Day01().parseSupplies(input)
        return elfSupplies.sortedDescending().slice(0..2).sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000 )
    check(part2(testInput) == 45000 )

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
