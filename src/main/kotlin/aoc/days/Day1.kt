package aoc.days

import aoc.BaseDay

class Day1(inputFile: String = "input_1.txt") : BaseDay(inputFile) {

    override fun part1(): Int {
        var max = 0
        var current = 0

        inputList.forEach {
            val calories = it.toIntOrNull()

            if (calories != null) {
                current += calories
            } else {
                if (current > max) {
                    max = current
                }
                current = 0
            }
        }

        println(max)
        return max
    }

    override fun part2(): Int {
        val caloriesByElves = arrayListOf<Int>()
        var current = 0

        inputList.forEach {
            val calories = it.toIntOrNull()

            if (calories != null) {
                current += calories
            } else {
                caloriesByElves += current
                current = 0
            }
        }

        val top3 = caloriesByElves.sortedDescending().take(3).sum()

        println(top3)

        return top3
    }
}