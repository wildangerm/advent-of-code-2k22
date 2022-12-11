package aoc.days

import aoc.BaseDay

class Day4(inputFile: String = "input_4.txt") : BaseDay(inputFile) {

    override fun part1(): Int {

        val result = inputList.count { input ->
            input.split(",").let {
                val first = it[0].split("-")
                val second = it[1].split("-")

                first.fullyContains(second) || second.fullyContains(first)
            }
        }

        return println(result)
    }

    override fun part2(): Int {
        val result = inputList.count { input ->
            input.split(",").let {
                val first = it[0].split("-")
                val second = it[1].split("-")

                first.overlaps(second) || second.overlaps(first)
            }
        }

        return println(result)
    }

    private fun List<String>.fullyContains(other: List<String>): Boolean {
        val thisRange = getThisRange()

        val otherStart = other[0].toInt()
        val otherEnd = other[1].toInt()

        return (thisRange.contains(otherStart) && thisRange.contains(otherEnd))
    }

    private fun List<String>.overlaps(other: List<String>): Boolean {
        val thisRange = getThisRange()

        val otherStart = other[0].toInt()
        val otherEnd = other[1].toInt()

        return (thisRange.contains(otherStart) || thisRange.contains(otherEnd))
    }

    private fun List<String>.getThisRange(): IntRange {
        return IntRange(this[0].toInt(), this[1].toInt())
    }

}