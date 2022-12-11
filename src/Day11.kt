import kotlin.math.floor
import kotlin.math.pow

class Round(list: List<Monkey>) {
    private val lookup: Map<Int, Monkey>
    private val sharedModulus: Int
    var adjust = true

    init {
        lookup = list.associateBy { it.id }
        sharedModulus = lookup.values.map { it.modulus }.reduce { res, mod -> res * mod }
    }

    fun execute(times: Int): Long {
        for (i in 1..times) {
            for (monkey in lookup.values) {
                for (item in monkey.items) {
                    val passTo = monkey.execute(item, adjust)
                    item.worry = item.worry % sharedModulus
                    lookup[passTo]!!.items.add(item)
                }
                monkey.items.removeAll(monkey.items)
            }
        }
        return result
    }

    private val result: Long
        get() {
            val result = lookup.values.map { it.inspections }.sortedDescending()
            return result[0] * result[1]
        }
}

class Monkey(val id: Int,
             val items: MutableList<Item>,
             private val operation: Char,
             private val operationAmount: Int,
             val modulus: Int,
             private val monkeyIdTrueCase: Int,
             private val monkeyIdFalseCase: Int) {

    var inspections = 0L

    fun execute(item: Item, adjust: Boolean): Int {
        inspections ++
        operate(item)
        if (adjust) { item.adjust() }
        return passTo(test(item))
    }

    private fun operate(item: Item) {
        when (operation) {
            '*' -> item.worry = item.worry * operationAmount
            '+' -> item.worry = item.worry + operationAmount
            '^' -> item.worry = floor(item.worry.toDouble().pow(operationAmount)).toLong()
        }
    }

    private fun test(item: Item): Boolean {
        return item.worry % modulus == 0L
    }

    private fun passTo(testResult: Boolean): Int {
        return if (testResult) monkeyIdTrueCase else monkeyIdFalseCase
    }
}

class Item(var worry: Long) {
    fun adjust() {
        worry /= 3L
    }
}

fun main() {
    fun parse(input: String): List<Monkey> {
        val monkeysRaw = input.split("\n\n")
        val monkeys: MutableList<Monkey> = ArrayList()
        for (str in monkeysRaw) {
            val lines = str.split("\n")
            val id = Integer.parseInt(lines[0].replace("Monkey ", "").replace(":", ""))
            val items = lines[1].replace(Regex("\\s+Starting items:\\s+"), "").split(", ").map {
                Item(Integer.parseInt(it).toLong())
            }.toMutableList()
            val operation: Char
            val operationAmount: Int
            val operationStringSplit = lines[2].replace(Regex("\\s+Operation: new = old\\s"), "").split(" ")
            if (operationStringSplit[1] == "old") {
                operation = '^'
                operationAmount = 2
            } else {
                operation = operationStringSplit[0][0]
                operationAmount = Integer.parseInt(operationStringSplit[1])
            }
            val modulus = Integer.parseInt(lines[3].replace(Regex("\\s+Test: divisible by\\s"), ""))
            val monkeyIdTrueCase = Integer.parseInt(lines[4].replace(Regex("\\s+If true: throw to monkey\\s"), ""))
            val monkeyIdFalseCase = Integer.parseInt(lines[5].replace(Regex("\\s+If false: throw to monkey\\s"), ""))
            val monkey = Monkey(id, items, operation, operationAmount, modulus, monkeyIdTrueCase, monkeyIdFalseCase)
            monkeys.add(monkey)
        }
        return monkeys
    }

    fun part1(input: String): Long {
        val round = Round(parse(input))
        return round.execute(20)
    }

    fun part2(input: String): Long {
        val round = Round(parse(input))
        round.adjust = false
        return round.execute(10000)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readChunk("Day11_test")
    check(part1(testInput) == 10605L)
    check(part2(testInput) == 2713310158L)

    val input = readChunk("Day11")
    println(part1(input))
    println(part2(input))
}
