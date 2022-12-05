class LoadingDock(input: String) {
    val stacks: MutableList<Stack>

    init {
        val columnIndexes = regex.getMatchUnsafe(input, "columnIndex")
        val stacksString = regex.getMatchUnsafe(input, "stacks")

        val numColumns = maxColumn(columnIndexes)
        stacks = initializeStacks(numColumns)

        val rows = stacksString.split("\n")
        for (row in rows) {
            for (idx in row.indices step crateWidth) {
                val maybeCrate = row.slice(idx until idx + crateWidth - 1)
                val regex = Regex(Crate.pattern)
                val columnId = idx/ crateWidth
                if (regex.matches(maybeCrate)) {
                    val value = regex.getMatchUnsafe(maybeCrate, "crate")[0]
                    stacks[columnId].crates.add(Crate(value))
                }
           }
        }
    }

    fun execute(instruction: Instruction) {
        val fromStack = stacks[instruction.startColumn - 1]
        val toStack = stacks[instruction.endColumn - 1]
        for (i in 1..instruction.numberOfCrates) {
            val element = fromStack.crates.removeFirst()
            toStack.crates.add(0, element)
        }
    }

    fun execute2(instruction: Instruction) {
        val fromStack = stacks[instruction.startColumn - 1]
        val toStack = stacks[instruction.endColumn - 1]

        val range = 0 until instruction.numberOfCrates
        val elementsToMove = fromStack.crates.slice(range)
        for (i in range) {
            fromStack.crates.removeAt(0)
        }
        toStack.crates.addAll(0, elementsToMove)
    }

    fun result(): String {
        return stacks
            .filter { it.crates.isNotEmpty() }
            .map { it.crates[0].value }.joinToString("")
    }

    private fun maxColumn(input: String): Int {
        return input.split(Regex("\\s"))
            .filter { it.isNotBlank() }
            .map { Integer.parseInt(it) }
            .max()
    }

    private fun initializeStacks(size: Int): MutableList<Stack> {
        val arr: MutableList<Stack> = ArrayList(size)
        for (i in 0 until size) {
            arr.add(i, Stack(i + 1, ArrayList()))
        }
        return arr
    }

    companion object {
        private const val crateWidth = 4
        private val regex = Regex("^${Stack.pattern}(?<columnIndex>\\s*1[0-9\\s]+)")
    }
}

data class Crate(val value: Char) {
    companion object {
        const val pattern = "\\[(?<crate>[A-Z])\\]"
    }
}

data class Stack(val columnId: Int, val crates: MutableList<Crate>) {
    companion object {
        const val pattern = "(?<stacks>(\\s*${Crate.pattern})+)"
    }
}

class Instructions(input: String) {
    val instructions: List<Instruction>

    init {
        instructions = input.split("\n")
            .filter { it.isNotBlank() }
            .map { Instruction.buildViaRegex(it) }
    }
}

data class Instruction(val numberOfCrates: Int, val startColumn: Int, val endColumn: Int) {
    companion object {
        private const val pattern = "(move\\s(?<numberOfCrates>[0-9]+)\\sfrom\\s(?<startColumn>[0-9]+)\\sto\\s(?<endColumn>[0-9]+)[\n\\s]*)"

        fun buildViaRegex(input: String): Instruction {
            val regex = Regex(pattern)
            val numberOfCrates = Integer.parseInt(regex.getMatchUnsafe(input, "numberOfCrates"))
            val startColumn = Integer.parseInt(regex.getMatchUnsafe(input, "startColumn"))
            val endColumn = Integer.parseInt(regex.getMatchUnsafe(input, "endColumn"))
            return Instruction(numberOfCrates, startColumn, endColumn)
        }
    }
}

fun main() {
    fun part1(input: String, instructions: Instructions): String {
        var loadingDock = LoadingDock(input)
        instructions.instructions.forEach { instruction ->
            loadingDock.execute(instruction)
        }
        return loadingDock.result()
    }

    fun part2(input: String, instructions: Instructions): String {
        var loadingDock = LoadingDock(input)
        instructions.instructions.forEach { instruction ->
            loadingDock.execute2(instruction)
        }
        return loadingDock.result()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readChunk("Day05_test").split("\n\n")
    var instructions = Instructions(testInput[1])
    check(part1(testInput[0], instructions) == "CMZ" )
    check(part2(testInput[0], instructions) == "MCD" )

    val input = readChunk("Day05").split("\n\n")
    instructions = Instructions(input[1])
    println(part1(input[0], instructions))
    println(part2(input[0], instructions))
}
