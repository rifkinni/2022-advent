

class PairRange(private val range1: Set<Int>, private val range2: Set<Int>) {
    fun fullyContains(): Boolean {
        return if (range1.size > range2.size) {
            range1.containsAll(range2)
        } else {
            range2.containsAll(range1)
        }
    }

    fun containsAny(): Boolean {
        return range1.intersect(range2).isNotEmpty()
    }

    companion object {
        fun parseSets(input: String): Pair<Set<Int>, Set<Int>> {
            val pair = input.split(",")
            val set1 = parseSet(pair[0])
            val set2 = parseSet(pair[1])
            return Pair(set1, set2)
        }

        private fun parseSet(input: String): Set<Int> {
            val splitString = input.split("-")
            return (Integer.parseInt(splitString[0])..Integer.parseInt(splitString[1])).toSet()
        }
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        return input.count {
            val sets = PairRange.parseSets(it)
            PairRange(sets.first, sets.second).fullyContains()
        }
    }

    fun part2(input: List<String>): Int {
        return input.count {
            val sets = PairRange.parseSets(it)
            PairRange(sets.first, sets.second).containsAny()
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2 )
    check(part2(testInput) == 4 )

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
