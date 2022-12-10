fun main() {
    fun traverseWindows(input: MutableList<Char>, size: Int): Int {
        val windows = input.windowed(size)
        for (i in windows.indices) {
            if (windows[i].distinct().size == size) {
                return i + size
            }
        }
        return 0
    }

    fun part1(input: MutableList<Char>): Int {
        return traverseWindows(input, 4)
    }

    fun part2(input: MutableList<Char>): Int {
        return traverseWindows(input, 14)
    }

    // test if implementation meets criteria from the description, like:

    val testCases = listOf(
        TestCase(0, 7, 19),
        TestCase(1, 5, 23),
        TestCase(2, 6, 23),
        TestCase(3, 10, 29),
        TestCase(4, 11, 26)
    )

    for (case in testCases) {
        val testInput = readInput("Day06_test")[case.index].toCharArray().toMutableList()
        check(part1(testInput) == case.part1)
        check(part2(testInput) == case.part2)
    }

    val input = readInput("Day06")[0].toCharArray().toMutableList()
    println(part1(input))
    println(part2(input))
}
