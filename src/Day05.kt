fun main() {

    fun atLeastThreeVowels(line: String): Boolean {
        return line.toCharArray()
            .count() { it == 'a' || it == 'e' || it == 'i' || it == 'o' || it == 'u' } >= 3
    }

    fun atLeastOneRepeatedLetter(line: String): Boolean {
        return line.toCharArray()
            .asSequence()
            .windowed(2, 1, false)
            .any { it[0] == it[1] }
    }

    fun hasForbiddenContent(line: String): Boolean {
        return line.contains("ab") || line.contains("cd") || line.contains("pq") || line.contains("xy")
    }

    fun part1(input: List<String>): Int {
        return input.count { line ->
            return@count atLeastThreeVowels(line) && atLeastOneRepeatedLetter(line) && !hasForbiddenContent(line)
        }
    }

    fun doublePairOfLetter(line: String): Boolean {
        for (i in 0 until (line.length - 2)) {
            for (j in i + 2 until (line.length - 1)) {
                // letters found again
                if (line[i] == line[j] && line[i + 1] == line[j + 1]) {
                    return true
                }
            }
        }
        return false
    }

    fun atLeastOneRepeatedLetterAroundOther(line: String): Boolean {
        return line.toCharArray()
            .asSequence()
            .windowed(3, 1, false)
            .any { it[0] == it[2] }
    }

    fun part2(input: List<String>): Int {
        return input.count { line ->
            return@count doublePairOfLetter(line) && atLeastOneRepeatedLetterAroundOther(line)
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day05_test1")
    check(part1(testInput1) == 2)
    val testInput2 = readInput("Day05_test2")
    check(part2(testInput2) == 2)

    val input = readInput("Day05")
    println("part 1 answer: ${part1(input)}")
    println("part 2 answer: ${part2(input)}")
}
