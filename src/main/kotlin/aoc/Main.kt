package aoc

import aoc.days.Day1
import aoc.days.Day2
import aoc.days.Day3

fun main(args: Array<String>) {

    val days = arrayListOf<BaseDay>()

    // days.add(Day1())
    days.add(Day2())
    days.add(Day3())

    days.forEach{
        it.part1()
        it.part2()
    }
}