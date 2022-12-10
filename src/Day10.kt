class Cycle {
    private var cycle = 1
    private var x = 1
    private val signalStrengths: MutableList<Int> = ArrayList()
    val art: StringBuilder = java.lang.StringBuilder()

    fun run(line: String) {
        if (line == "noop") {
            noop()
        } else if (line.startsWith("addx")) {
            val value = Integer.parseInt(line.split(" ")[1])
            noop(); noop()
            x += value
        }
    }

    private fun makeArt() {
        val spritePosition = (cycle - 1) % 40
        val symbol = if ((x - 1 .. x + 1).contains(spritePosition)) '#' else '.'
        art.append(symbol)
        if (cycle % 40 == 0) {
            art.append("\n")
        }
    }

    private fun noop() {
        signalStrengths.add(cycle * x)
        makeArt()
        cycle++
    }

    fun getResult(): Int {
        var sum = 0; var i = 20
        while (i < signalStrengths.size) {
            sum += signalStrengths[i - 1]
            i += 40
        }
        return sum
    }
}

fun main() {

    fun solve(input: List<String>): Int {
        val cycle = Cycle()
        for (line in input) {
            cycle.run(line)
        }
        println(cycle.art.toString())
        println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n")
        return cycle.getResult()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(solve(testInput) == 13140)

    val input = readInput("Day10")
    println(solve(input))
}
