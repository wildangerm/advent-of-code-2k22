package aoc.days

import aoc.BaseDay

class Day8(inputFile: String = "input_8.txt") : BaseDay(inputFile) {

    override fun part1(): Int {
        val map = Map(inputList)
        map.calcVisibility()

        val result = map.getVisibilityScore()
        return println(result)
    }

    override fun part2(): Int {
        val map = Map(inputList)

        map.calcSeenTrees()

        val result = map.getScenicScore()
        return println(result)
    }

}

class Map(input: List<String>) {
    private val map = mutableListOf<List<Tree>>()
    private var maxY = 0
    private var maxX = 0

    init {
        input.forEach { line ->
            val heights = line.toCharArray().map { Tree(it.digitToInt()) }
            map.add(heights)
        }
        maxY = map.size - 1
        maxX = map[0].size - 1
    }

    fun calcVisibility() {
        markEdgesVisible()

        for (y in 1 until maxY) {
            for (x in 1 until maxX) {
                map[y][x].visible = isVisibleFromTop(x, y) || isVisibleFromBottom(x, y) ||
                        isVisibleFromLeft(x, y) || isVisibleFromRight(x, y)
            }
        }
    }

    private fun markEdgesVisible() {
        map.forEachIndexed { y, row ->
            row.forEachIndexed { x, value ->
                if (x == 0 || x == maxX || y == 0 || y == maxY) {
                    value.visible = true
                }
            }
        }
    }

    fun calcSeenTrees() {
        for (y in 0..maxY) {
            for (x in 0..maxX) {
                isVisibleFromTop(x, y)
                isVisibleFromBottom(x, y)
                isVisibleFromLeft(x, y)
                isVisibleFromRight(x, y)
            }
        }
    }

    private fun isVisibleFromTop(x: Int, _y: Int): Boolean {
        var y = _y - 1
        val curr = map[_y][x]

        while (y >= 0) {
            val next = map[y][x]
            curr.viewDistanceTop++
            if (next.height < curr.height) {
                y--
            } else {
                return false
            }
        }
        return true
    }

    private fun isVisibleFromBottom(x: Int, _y: Int): Boolean {
        var y = _y + 1
        val curr = map[_y][x]

        while (y <= maxY) {
            val next = map[y][x]
            curr.viewDistanceBottom++
            if (next.height < curr.height) {
                y++
            } else {
                return false
            }
        }
        return true
    }

    private fun isVisibleFromLeft(_x: Int, y: Int): Boolean {
        var x = _x - 1
        val curr = map[y][_x]

        while (x >= 0) {
            val next = map[y][x]
            curr.viewDistanceLeft++
            if (next.height < curr.height) {
                x--
            } else {
                return false
            }
        }
        return true
    }

    private fun isVisibleFromRight(_x: Int, y: Int): Boolean {
        var x = _x + 1
        val curr = map[y][_x]

        while (x <= maxX) {
            val next = map[y][x]
            curr.viewDistanceRight++
            if (next.height < curr.height) {
                x++
            } else {
                return false
            }
        }
        return true
    }

    fun getVisibilityScore(): Int {
        return map.sumOf { row -> row.count { it.visible!! } }
    }

    fun getScenicScore(): Int {
        return map.maxOf { row -> row.maxOf { it.getScenicScore() } }
    }

}

data class Tree(
    val height: Int, var visible: Boolean? = null,
    var viewDistanceTop: Int = 0,
    var viewDistanceBottom: Int = 0,
    var viewDistanceLeft: Int = 0,
    var viewDistanceRight: Int = 0
) {
    fun getScenicScore(): Int {
        return viewDistanceRight * viewDistanceLeft * viewDistanceBottom * viewDistanceTop
    }
}
