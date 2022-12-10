fun main() {
    fun isVisibleFromLeft(row: String, idx: Int): Boolean {
        val tree = row[idx]
        for (i in idx - 1 downTo 0 ) {
            val neighbor = row[i]
            if (tree <= neighbor) {
                return false
            }
        }
        return true
    }

    fun isVisibleFromRight(row: String, idx: Int): Boolean {
        val tree = row[idx]
        for (i in idx + 1 until row.length ) {
            val neighbor = row[i]
            if (tree <= neighbor) {
                return false
            }
        }
        return true
    }

    fun isVisibleFromTop(input: List<String>, xCoord: Int, yCoord: Int): Boolean {
        val tree = input[yCoord][xCoord]
        for (i in yCoord - 1 downTo 0 ) {
            val neighbor = input[i][xCoord]
            if (tree <= neighbor) {
                return false
            }
        }
        return true
    }

    fun isVisibleFromBottom(input: List<String>, xCoord: Int, yCoord: Int): Boolean {
        val tree = input[yCoord][xCoord]
        for (i in yCoord + 1 until input.size ) {
            val neighbor = input[i][xCoord]
            if (tree <= neighbor) {
                return false
            }
        }
        return true
    }


    fun part1(input: List<String>): Int {
        var count = 0
        for (yCoord in input.indices) {
            for (xCoord in input[0].indices) {
                if (yCoord == 0 || xCoord == 0) {
                    count++
                    continue
                }
                if (yCoord == input.size - 1 || xCoord == input[0].length - 1) {
                    count ++
                    continue
                }
                if (isVisibleFromLeft(input[yCoord], xCoord)) {
                    count++
                    continue
                }
                if (isVisibleFromRight(input[yCoord], xCoord)) {
                    count++
                    continue
                }
                if (isVisibleFromTop(input, xCoord, yCoord)) {
                    count++
                    continue
                }
                if (isVisibleFromBottom(input, xCoord, yCoord)) {
                    count++
                    continue
                }
            }
        }
        return count
    }

    fun scoreLeft(row: String, idx: Int): Int {
        var score = 0
        val tree = row[idx]
        for (i in idx - 1 downTo 0 ) {
            score ++
            val neighbor = row[i]
            if (tree <= neighbor) {
                break
            }
        }
        return score
    }

    fun scoreRight(row: String, idx: Int): Int {
        var score = 0
        val tree = row[idx]
        for (i in idx + 1 until row.length ) {
            score ++
            val neighbor = row[i]
            if (tree <= neighbor) {
                break
            }
        }
        return score
    }

    fun scoreUp(input: List<String>, xCoord: Int, yCoord: Int): Int {
        var score = 0
        val tree = input[yCoord][xCoord]
        for (i in yCoord - 1 downTo 0 ) {
            score++
            val neighbor = input[i][xCoord]
            if (tree <= neighbor) {
                break
            }
        }
        return score
    }

    fun scoreDown(input: List<String>, xCoord: Int, yCoord: Int): Int {
        var score = 0
        val tree = input[yCoord][xCoord]
        for (i in yCoord + 1 until input.size ) {
            score++
            val neighbor = input[i][xCoord]
            if (tree <= neighbor) {
                break
            }
        }
        return score
    }


    fun part2(input: List<String>): Int {
        var max = 0
        for (yCoord in input.indices) {
            for (xCoord in input[0].indices) {
                val row = input[yCoord]
                if (yCoord == 0 || xCoord == 0) {
                    continue
                }
                if (yCoord == input.size - 1 || xCoord == input[0].length - 1) {
                    continue
                }

                val score = scoreUp(input, xCoord, yCoord) * scoreDown(input, xCoord, yCoord) *
                        scoreLeft(row, xCoord) * scoreRight(row, xCoord)
                if (score > max) {
                    max = score
                }
            }
        }
        return max
    }

    // test if implementation meets criteria from the description, like:


    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
