package aoc.days

import aoc.BaseDay

class Day5(inputFile: String = "input_5.txt") : BaseDay(inputFile) {

    override fun part1(): String {

        val stacksInput = inputList.takeWhile { it != "" }
        val movesInput = inputList.takeLastWhile { it != "" }

        val stacks = getStacks(stacksInput)
        val moves = getMoves(movesInput)

        moves.forEach { move ->
            for (qty in 0 until move.quantity) {
                stacks[move.to].addFirst(stacks[move.from].removeFirst())
            }
        }

        val result = stacks.joinToString("") { s -> s.first() }
        return println(result)
    }

    override fun part2(): String {
        val stacksInput = inputList.takeWhile { it != "" }
        val movesInput = inputList.takeLastWhile { it != "" }

        val stacks = getStacks(stacksInput)
        val moves = getMoves(movesInput)

        moves.forEach { move ->
            val toAdd= ArrayDeque<String>()
            for (qty in 0 until move.quantity) {
                toAdd.addFirst(stacks[move.from].removeFirst())
            }
            for(qty in 0 until move.quantity){
                stacks[move.to].addFirst(toAdd.removeFirst())
            }
        }

        val result = stacks.joinToString("") { s -> s.first() }
        return println(result)
    }

    private fun getMoves(movesInput: List<String>): List<Move> {
        return movesInput.map { input ->
            val moveParts = input.split(" ").filterIndexed { index, _ -> index % 2 == 1 }.map { it.toInt() }
            Move(moveParts[0], moveParts[1] - 1, moveParts[2] - 1)
        }
    }

    private fun getStacks(stacksInput: List<String>): ArrayList<ArrayDeque<String>> {
        val numOfStacks = stacksInput.last().split(" ").filter { it != "" }.size
        val stacks = ArrayList<ArrayDeque<String>>(numOfStacks)
        for (i in 0 until numOfStacks) {
            stacks.add(ArrayDeque())
        }

        stacksInput.asReversed().drop(1).forEach {
            val processedInputParts = it.filterIndexed { index, _ ->
                (index - 1) % 4 == 0
            }.toCharArray()

            processedInputParts.forEachIndexed { index, c ->
                if (!c.isWhitespace()) {
                    stacks[index].addFirst(c.toString())
                }
            }

        }

        return stacks
    }

    data class Move(val quantity: Int, val from: Int, val to: Int)

}