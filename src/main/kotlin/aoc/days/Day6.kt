package aoc.days

import aoc.BaseDay

class Day6(inputFile: String = "input_6.txt") : BaseDay(inputFile) {

    override fun part1(): Int {

        val input = inputList.first().toCharArray().toList()

        val result = findMarker(input, 4)
        println(result)
        return result
    }


    override fun part2(): Int {

        val input = inputList.first().toCharArray().toList()

        val result = findMarkerV2(input, 14)
        println(result)
        return result
    }

    private fun findMarker(input: List<Char>, sizeOfMarker: Int): Int {
        val index = input.windowed(sizeOfMarker)
            .indexOfFirst { it.toSet().size == sizeOfMarker }

        return index + sizeOfMarker
    }

    private fun findMarkerV2(input: List<Char>, sizeOfMarker: Int): Int {
        input.windowed(sizeOfMarker)
            .forEachIndexed { index, a ->
                if (a.distinct().size == sizeOfMarker) {
                    return index + sizeOfMarker
                }
            }

        return 0
    }
}
