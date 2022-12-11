package aoc.days

import aoc.BaseDay

class Day2(inputFile: String = "input_2.txt") : BaseDay(inputFile) {

    override fun part1(): Int {
        val score = inputList.sumOf {
            val parts = it.split(" ")
            val enemy = parts[0].single()
            val me = parts[1].single()

            calculateScore(enemy, me)
        }

        return println(score)
    }

    override fun part2(): Int {
        val mx = hashMapOf(
            "AX" to 'C',
            "AY" to 'A',
            "AZ" to 'B',

            "BX" to 'A',
            "BY" to 'B',
            "BZ" to 'C',

            "CX" to 'B',
            "CY" to 'C',
            "CZ" to 'A'

        )

        val score = inputList.sumOf {
            val parts = it.split(" ")
            val enemy = parts[0].single()
            val outCome = parts[1].single()

            calculateScoreWithNeededOutcome(enemy, outCome, mx)
        }

        return println(score)
    }

    private fun calculateScore(enemy: Char, me: Char): Int {
        return scoreInput(me) + scoreIfIWon(enemy, me)
    }

    private fun calculateScoreWithNeededOutcome(enemy: Char, outcome: Char, mx: HashMap<String, Char>): Int {
        val neededInput = calcMyInputToReachOutcome(enemy, outcome, mx)

        return scoreInput(neededInput) + scoreIfIWon2(outcome)
    }

    private fun scoreIfIWon2(outcome: Char): Int {
        return when (outcome) {
            'X' -> 0
            'Y' -> 3
            'Z' -> 6
            else -> 0
        }
    }

    private fun calcMyInputToReachOutcome(enemy: Char, outcome: Char, mx: HashMap<String, Char>): Char {
        return mx[enemy.toString() + outcome.toString()]!!
    }

    private fun scoreIfIWon(enemy: Char, me: Char): Int {
        val mappedMe = when (me) {
            'X' -> 'A'
            'Y' -> 'B'
            'Z' -> 'C'
            else -> Char.MIN_VALUE
        }

        if (enemy == mappedMe) {
            return 3
        } else {
            if (enemy == 'A' && mappedMe == 'B' ||
                enemy == 'B' && mappedMe == 'C' ||
                enemy == 'C' && mappedMe == 'A'
            ) {
                return 6
            }
            return 0
        }
    }

    private fun scoreInput(input: Char): Int {
        return when (input) {
            'A', 'X' -> 1
            'B', 'Y' -> 2
            'C', 'Z' -> 3
            else -> 0
        }
    }

}