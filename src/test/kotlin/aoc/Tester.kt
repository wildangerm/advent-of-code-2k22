package aoc

import aoc.days.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class Tester {

    @ParameterizedTest
    @MethodSource("generator")
    fun testDays(input: BaseDay, part1Result: Any?, part2Result: Any?){
        testParts(input, part1Result, part2Result)
    }

    private fun testParts(day: BaseDay, part1Result: Any?, part2Result: Any?) {
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
//                Arguments.of(Day4(), null, null)
//                Arguments.of(Day4(getTestFileName(4)), 2, 4)
//                Arguments.of(Day5(), null, null)
//                Arguments.of(Day5(getTestFileName(5)), "CMZ", "MCD")
//                  Arguments.of(Day6(), null, null)
//                  Arguments.of(Day6(getTestFileName(6)), 7, 19)
//                  Arguments.of(Day7(), null, null)
//                  Arguments.of(Day7(getTestFileName(7)), 95437, 24933642)
//                  Arguments.of(Day8(), null, null)
//                  Arguments.of(Day8(getTestFileName(8)), 21, 8)
//                  Arguments.of(Day9(), null, null)
//                  Arguments.of(Day9(getTestFileName(9)), 13, 1)
//                  Arguments.of(Day10(), null, null)
//                  Arguments.of(Day10(getTestFileName(10)), 13140, null)
                      Arguments.of(Day11(), null, null)
//                  Arguments.of(Day11(getTestFileName(11)), 10605L, 2713310158L)

            )

        }

        private fun getTestFileName(num: Int): String {
            return "input_${num}_test.txt"
        }
    }
}