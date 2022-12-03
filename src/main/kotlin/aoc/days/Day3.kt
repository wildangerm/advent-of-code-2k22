package aoc.days

import aoc.BaseDay

class Day3(inputFile: String = "input_3.txt") : BaseDay(inputFile) {

    override fun part1(): Int {
        val result = inputList.sumOf {
            val mid = it.length / 2

            val first = it.substring(0, mid).toSet()
            val second = it.substring(mid).toSet()

            val inBoth = first.intersect(second)

            getPriority(inBoth.first())
        }

        println(result)
        return result
    }

    override fun part2(): Int {
        val result = inputList.chunked(3).sumOf { listOf3 ->
            val common = listOf3
                .map { it.toSet()}
                .reduce{ acc, it ->
                    acc.intersect(it)
                }

            getPriority(common.first())
        }

        println(result)
        return result
    }

    private fun getPriority(char: Char): Int {
        return if (char.isLowerCase()) {
            char.code - 96
        } else {
            char.code - 38
        }
    }
}