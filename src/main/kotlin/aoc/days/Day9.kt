package aoc.days

import aoc.BaseDay
import aoc.days.Day9.Direction.*
import kotlin.math.sign

class Day9(inputFile: String = "input_9.txt") : BaseDay(inputFile) {

    override fun part1(): Int {
        val head = Position(0, 0)
        val rope = Rope(head)

        getStepsFromInput().forEach {
            rope.doStep(it)
        }

        return println(rope.knots.last().positions.size)
    }

    override fun part2(): Int {
        val head = Position(0, 0)
        val rope = Rope(head, 9)

        getStepsFromInput().forEach {
            rope.doStep(it)
        }

        return println(rope.knots.last().positions.size)
    }

    private fun getStepsFromInput() = inputList.map {
        val parts = it.split(" ")
        Step(enumValueOf(parts[0]), parts[1].toInt())
    }

    data class Step(val dir: Direction, val amount: Int)
    enum class Direction { U, D, L, R }

    data class Position(val x: Int, val y: Int) {
        fun diff(to: Position): Position {
            return Position(to.x - this.x, to.y - this.y)
        }

    }

    class Rope(var head: Position, size: Int = 1, val knots: ArrayList<Knot> = arrayListOf()) {
        init {
            repeat(size - 1) { knots.add(Knot(head)) }
            knots.add(Knot(head, true))
        }

        fun doStep(step: Step) {
            var curr = head

            repeat(step.amount) {
                curr = when (step.dir) {
                    U -> Position(curr.x, curr.y + 1)
                    D -> Position(curr.x, curr.y - 1)
                    L -> Position(curr.x - 1, curr.y)
                    R -> Position(curr.x + 1, curr.y)
                }

                var prevKnot = curr
                knots.forEach { knot ->
                    knot.doStep(prevKnot)
                    prevKnot = knot.pos
                }
            }
            head = curr
        }
    }


    class Knot(var pos: Position, private val tail: Boolean = false, val positions: HashSet<Position> = hashSetOf()) {

        init {
            if (tail) {
                positions.add(pos)
            }
        }

        fun doStep(head: Position) {
            val diff = pos.diff(head)

            val headStillNeighBor = !(diff.x > 1 || diff.x < -1 || diff.y > 1 || diff.y < -1)
            if (headStillNeighBor) {
                return
            }

            val curr = Position(pos.x + diff.x.sign, pos.y + diff.y.sign)

            if (tail) {
                positions.add(curr)
            }

            pos = curr
        }
    }
}