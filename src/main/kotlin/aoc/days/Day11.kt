package aoc.days

import aoc.BaseDay
import java.util.*
import java.util.function.Function

fun Iterable<Long>.product(): Long {
    return this.reduce { acc, num -> acc * num }
}

class Day11(inputFile: String = "input_11.txt") : BaseDay(inputFile) {

    override fun part1(): Long {
        return findMonkeyBusiness()
    }

    private fun findMonkeyBusiness(): Long {
        val monkeys = parseMonkeys()

        repeat(20) { monkeys.forEach { it.testAllAndThrow(monkeys, true) } }

        return println(monkeys.map { it.inspected }.sortedDescending().take(2).product())
    }

    override fun part2(): Long {
        val monkeys = parseMonkeys()

        val lcm = monkeys.map { it.divisor }.product() // since all primes
        repeat(10000) { monkeys.forEach { it.testAllAndThrow(monkeys, false, lcm) } }

        return println(monkeys.map { it.inspected }.sortedDescending().take(2).product())
    }

    private fun parseMonkeys() = inputList.filter { it.isNotBlank() }.chunked(6).map { monkeyInput ->
        Monkey(
            worryLevels = LinkedList(
                monkeyInput[1]
                    .substringAfter(":")
                    .replace(" ", "")
                    .split(",")
                    .map { it.toLong() }
            ),
            operation = createOperation(monkeyInput[2].substringAfter("old ").split(" ")),
            divisor = monkeyInput[3].substringAfterLast(" ").toLong(),
            throwToIfTrue = monkeyInput[4].substringAfterLast(" ").toInt(),
            throwToIfFalse = monkeyInput[5].substringAfterLast(" ").toInt()
        )
    }

    private fun createOperation(operationParts: List<String>): Function<Long, Long> {
        val operator = operationParts[0]
        val operand = operationParts[1].toLongOrNull()

        return when (operator) {
            "*" -> Function { x -> x * (operand ?: x) }
            "+" -> Function { x -> x + (operand ?: x) }
            else -> throw IllegalArgumentException("Unknown operation specified!")
        }
    }

    class Monkey(
        private val worryLevels: Queue<Long> = LinkedList(),
        private val operation: Function<Long, Long>,
        val divisor: Long,
        private val throwToIfTrue: Int,
        private val throwToIfFalse: Int,
        var inspected: Long = 0
    ) {
        fun testAllAndThrow(monkeys: List<Monkey>, canManageWorryLevels: Boolean, lcm: Long = 0) {
            inspected += worryLevels.size

            while (!worryLevels.isEmpty()) {
                var worryLevel = worryLevels.remove()
                worryLevel = inspect(worryLevel, lcm)
                worryLevel = if (canManageWorryLevels) worryLevel.floorDiv(3) else worryLevel

                if (getTestResult(worryLevel))
                    monkeys[throwToIfTrue].catchItem(worryLevel)
                else
                    monkeys[throwToIfFalse].catchItem(worryLevel)
            }
        }

        private fun getTestResult(worryLevel: Long) = worryLevel % divisor == 0L

        private fun inspect(worryLevel: Long, lcm: Long) =
            if (lcm == 0L) operation.apply(worryLevel) else operation.apply(worryLevel).mod(lcm)

        fun catchItem(worryLevel: Long) = worryLevels.add(worryLevel)
    }
}