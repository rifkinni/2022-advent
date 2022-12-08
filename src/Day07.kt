class Parser(root: Directory) {
    private var pwd: Directory = root

    fun parse(line: String) {
        if (line.startsWith("$ cd ")) {
            val name = line.replace("$ cd ", "")
            pwd = when(name) {
                "/" -> pwd //no-op, already set
                ".." -> pwd.parent!!
                else -> {
                    val directory = Directory(name, pwd)
                    pwd.children.add(directory)
                    directory
                }
            }
        } else if (line.matches(Regex("^[0-9]+ .+"))) {
            val size = Integer.parseInt(line.split(" ")[0])
            pwd.cumulativeFileSize += size
        }
    }
}


class Directory(val name: String, val parent: Directory?) {
    val children: MutableList<Directory> = ArrayList()
    var cumulativeFileSize = 0

    fun calculateTotalSize(): Int {
        return cumulativeFileSize + children.sumOf { it.calculateTotalSize() }
    }
}

class FileSystem(input: List<String>) {
    val root = Directory("/", null)

    init {
        val parser = Parser(root)
        input.forEach { parser.parse(it) }
    }

    fun sumSizes(): Int {
        val sizes = recurseDirectorySizes(root.children, ArrayList())
        return sizes.filter { it < MAX_SIZE }.sum()
    }

    fun minMaxSize(): Int {
        val delta = SPACE_NEEDED - (SYSTEM_SIZE - root.calculateTotalSize())
        val sizes = recurseDirectorySizes(root.children, mutableListOf(root.calculateTotalSize()))
        return sizes.filter { it > delta }.min()
    }

    private fun recurseDirectorySizes(directories: List<Directory>, result: MutableList<Int>): MutableList<Int>  {
        for (directory in directories) {
            result.add(directory.calculateTotalSize())
            recurseDirectorySizes(directory.children, result)
        }
        return result
    }

    companion object {
        const val SYSTEM_SIZE = 70000000
        const val SPACE_NEEDED = 30000000
        const val MAX_SIZE = 100000
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val fileSystem = FileSystem(input)
        return fileSystem.sumSizes()
    }

    fun part2(input: List<String>): Int {
        val fileSystem = FileSystem(input)
        return fileSystem.minMaxSize()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
