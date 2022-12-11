package aoc.days

import aoc.BaseDay

class Day10(inputFile: String = "input_10.txt") : BaseDay(inputFile) {

    override fun part1(): Int {
        val importantCycles = arrayListOf(20, 60, 100, 140, 180, 220)
        var x = 1
        var cycle = 1

        val resultMap = hashMapOf<Int, Int>()

        inputList.forEach {
            if (it.length == 4) {
                if (importantCycles.contains(cycle)) {
                    resultMap[cycle] = x
                }
                cycle++
            } else {
                if (importantCycles.contains(cycle)) {
                    resultMap[cycle] = x
                } else if (importantCycles.contains(cycle + 1)) {
                    resultMap[cycle + 1] = x
                }
                cycle += 2
                x += it.split(" ")[1].toInt()
            }
        }


        val result = resultMap.map { (k, v) -> k * v }.sum()
        return println(result)
    }

    override fun part2(): Int {
        println()
        var x = 1 // middle of Sprite so 0,1,2 now
        var cycle = 1 // draws at pixel 0 a.k.a cycle-1


        inputList.forEach {
            var drawIndex = (cycle - 1) % 40
            val sprite = (x - 1..x + 1)

            drawIfNeeded(sprite, drawIndex)
            if (it.length == 4) {
                cycle++
            } else {
                drawIndex = cycle % 40
                drawIfNeeded(sprite, drawIndex)
                cycle += 2
                x += it.split(" ")[1].toInt()
            }
        }
        return 0
    }

    private fun drawIfNeeded(sprite: IntRange, drawIndex: Int) {
        if (drawIndex in sprite) {
            print("#")
        } else {
            print(".")
        }

        if (drawIndex == 39) {
            println()
        }
    }
}