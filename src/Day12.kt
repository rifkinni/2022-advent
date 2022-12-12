class Grid(val rows: List<List<Coordinate>>,
           val start: Coordinate,
           val end: Coordinate) {

    private val nodes: MutableSet<Coordinate> = mutableSetOf(start)

    fun run(): Int {
        start.distanceFromStart = 0
        nodes.removeAll(nodes)
        nodes.add(start)
        while (end.distanceFromStart == Integer.MAX_VALUE) {
            val node = nodes.first()
            val neighbors = getNeighbors(node)
                .filter { it.value - node.value <= 1 } // the neighbor is no more than 1 character greater than the current
            searchNeighbors(node, neighbors)
        }
        return end.distanceFromStart
    }

    fun run2(): Int {
        end.distanceFromStart = 0
        nodes.removeAll(nodes)
        nodes.add(end)
        while (nodes.isNotEmpty()) {
            val node = nodes.first()
            val neighbors = getNeighbors(node)
                .filter { node.value - it.value <= 1 } // the neighbor is no more than 1 character greater than the current
            searchNeighbors(node, neighbors)
        }
        return rows.flatten().filter { it.value == 'a' }.minOf { it.distanceFromStart }
    }

    private fun getNeighbors(coordinate: Coordinate): List<Coordinate> {
        val neighbors: MutableList<Coordinate> = ArrayList()

        if (coordinate.y - 1 >= 0) {
            neighbors.add(rows[coordinate.y - 1][coordinate.x])
        }
        if (coordinate.y + 1 < rows.size) {
            neighbors.add(rows[coordinate.y + 1][coordinate.x])
        }

        if (coordinate.x - 1 >= 0) {
            neighbors.add(rows[coordinate.y][coordinate.x - 1])
        }
        if (coordinate.x + 1 < rows[0].size) {
            neighbors.add(rows[coordinate.y][coordinate.x + 1])
        }

        return neighbors
    }

    private fun searchNeighbors(node: Coordinate, neighbors: List<Coordinate>) {
        for (neighbor in neighbors) {
            if (neighbor.distanceFromStart == Integer.MAX_VALUE) { // we have not yet visited this node
                nodes.add(neighbor)
            }

            // update if there is a new shortest path
            if (node.distanceFromStart + 1 < neighbor.distanceFromStart) {
                neighbor.distanceFromStart = node.distanceFromStart + 1
            }
        }
        nodes.remove(node)
    }
}

data class Coordinate(val x: Int, val y: Int, val value: Char) {
    var distanceFromStart = Int.MAX_VALUE
}

fun main() {
    fun buildGrid(input: List<String>): Grid {
        val rows: MutableList<List<Coordinate>> = ArrayList()
        var start: Coordinate? = null
        var end: Coordinate? = null
        for (y in input.indices) {
            val row: MutableList<Coordinate> = ArrayList()
            for (x in input[y].indices) {
                val coordinate: Coordinate
                val value = input[y][x]
                when(value) {
                    'S' -> {
                        coordinate = Coordinate(x, y, 'a')
                        start = coordinate
                    }
                    'E' -> {
                        coordinate = Coordinate(x, y, 'z')
                        end = coordinate
                    }
                    else -> {
                        coordinate = Coordinate(x, y, value)
                    }
                }
                row.add(coordinate)
            }
            rows.add(row)
        }
        return Grid(rows, start!!, end!!)
    }

    fun part1(input: List<String>): Int {
        return buildGrid(input).run()
    }

    fun part2(input: List<String>): Int {
        return buildGrid(input).run2()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    check(part1(testInput) == 31)
    check(part2(testInput) == 29)

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}
