import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt")
    .readLines()

fun readChunk(name: String) = File("src", "$name.txt")
    .readText()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

fun Regex.getMatchUnsafe(input: String, name: String): String {
    return this.matchEntire(input)!!.groups.get(name)!!.value
}

data class TestCase(val index: Int, val part1: Int, val part2: Int)
