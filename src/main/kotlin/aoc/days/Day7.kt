package aoc.days

import aoc.BaseDay

private val cd = """^\$ cd (.*)""".toRegex()
private val ls = """\$ ls""".toRegex()
private val dirRegex = """dir (.*)""".toRegex()
private val fileRegex = """(\d+) (.*)""".toRegex()

class Day7(inputFile: String = "input_7.txt") : BaseDay(inputFile) {

    private val root = Directory("/")

    override fun part1(): Int {
        inputList.removeFirst()

        setUpDirectoryStructure(root)

        val result = root.getSumDirSizesMax100k()
        return println(result)
    }

    override fun part2(): Int {
        val spaceTotal = 70_000_000
        val spaceNeededAtLeast = 30_000_000
        val unUsedSpace = spaceTotal - root.dirSize
        val spaceToFree = spaceNeededAtLeast - unUsedSpace


        val minToDelete = root.getDirectorySizesLargerThan(spaceToFree).min()
        return println(minToDelete)
    }

    private fun setUpDirectoryStructure(root: Directory) {
        var currentDirectory = root

        for (i in 0 until inputList.size) {
            val currentCommand = inputList[i]

            if (currentCommand.startsWith("$")) {
                if (cd.matches(currentCommand)) {
                    val (directoryToChangeTo) = cd.matchEntire(currentCommand)!!.destructured
                    currentDirectory = currentDirectory.cd(directoryToChangeTo)

                } else if (ls.matches(currentCommand)) {
                    val dirContent = inputList.subList(i + 1, inputList.size).takeWhile { !it.startsWith("$") }
                    addFiles(dirContent, currentDirectory)
                    addDirectories(dirContent, currentDirectory)
                }
            }
        }
    }

    private fun addDirectories(dirContent: List<String>, currentDirectory: Directory) {
        dirContent.mapNotNull { dirRegex.matchEntire(it) }.forEach {
            val (name) = it.destructured
            currentDirectory.addDirectory(Directory(name, currentDirectory))
        }
    }

    private fun addFiles(dirContent: List<String>, currentDirectory: Directory) {
        dirContent.mapNotNull { fileRegex.matchEntire(it) }.forEach {
            val (sizeString, name) = it.destructured
            val size = sizeString.toInt()

            currentDirectory.addFile(File(name, size))
            currentDirectory.updateParentsWithSize(size)
        }
    }
}

data class File(val name: String, val size: Int)

data class Directory(
    val name: String,
    val parentDir: Directory? = null,
    var dirSize: Int = 0,
    val files: ArrayList<File> = arrayListOf(),
    val directories: HashMap<String, Directory> = hashMapOf()
) {
    fun addFile(file: File) = files.add(file)

    fun addDirectory(dir: Directory) {
        directories[dir.name] = dir
    }

    fun cd(directoryToChangeTo: String): Directory {
        if (".." == directoryToChangeTo) {
            return parentDir ?: this
        }
        return directories[directoryToChangeTo]!!
    }

    fun updateParentsWithSize(size: Int) {
        dirSize += size

        if (parentDir == null) {
            return
        }
        parentDir.updateParentsWithSize(size)
    }

    fun getSumDirSizesMax100k(): Int {
        var size = directories.values.sumOf { it.getSumDirSizesMax100k() }

        if (dirSize < 100_000L) {
            size += dirSize
        }
        return size
    }

    fun getDirectorySizesLargerThan(spaceToFree: Int): List<Int> {
        val dirSizesLargerThanGiven = arrayListOf<Int>()

        if (dirSize > spaceToFree) {
            dirSizesLargerThanGiven.add(dirSize)
            directories.values.map { dir ->
                dir.getDirectorySizesLargerThan(spaceToFree)
                    .minOrNull()
                    ?.let { dirSizesLargerThanGiven.add(it) }
            }

        }
        return dirSizesLargerThanGiven
    }
}