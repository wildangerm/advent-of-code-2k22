package aoc

abstract class BaseDay(inputFile: String) {

    protected var inputList = ArrayList<String>()

    abstract fun part1(): Int
    abstract fun part2(): Int

    init {
        readFile(inputFile)
    }

    protected fun getInput(fileName: String): List<String> {
        return javaClass.getResourceAsStream("/$fileName")!!.bufferedReader().readLines()
    }

    protected open fun readFile(inputFile: String) {
        inputList = ArrayList(getInput(inputFile))
    }
}