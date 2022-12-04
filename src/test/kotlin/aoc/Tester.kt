package aoc

import aoc.days.Day1
import aoc.days.Day2
import aoc.days.Day3
import aoc.days.Day4
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class Tester {

    @ParameterizedTest
    @MethodSource("generator")
    fun testDays(input: BaseDay, part1Result: Int?, part2Result: Int?){
        testParts(input, part1Result, part2Result)
    }

    private fun testParts(day: BaseDay, part1Result: Int?, part2Result: Int?) {
        println(day::class.simpleName)

        print("\tpart1: ")
        part1Result
            ?.let { assertEquals(it, day.part1()) }
            ?: day.part1()

        print("\tpart2: ")
        part2Result
            ?.let { assertEquals(it, day.part2()) }
            ?: day.part2()

        println()
    }

    companion object {
        @JvmStatic
        private fun generator() : Stream<Arguments> {
            return Stream.of(
                //Arguments.of(Day1(), null, null),
//                Arguments.of(Day1(getTestFileName(1)), 24000, 45000),
                //Arguments.of(Day2(), null, null),
//                Arguments.of(Day2(getTestFileName(2)), 15, 12),
//                Arguments.of(Day3(), null, null),
//                Arguments.of(Day3(getTestFileName(3)), 157, 70),
              Arguments.of(Day4(), null, null)
//                Arguments.of(Day4(getTestFileName(4)), 2, 4)
            )
        }

        private fun getTestFileName(num: Int): String {
            return "input_${num}_test.txt"
        }
    }
}