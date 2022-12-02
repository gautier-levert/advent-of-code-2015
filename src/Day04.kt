fun main() {

    fun part1(input: List<String>): Int {
        val prefix = input[0]
        return generateSequence(1) { it + 1 }
            .first { index ->
                "$prefix$index".md5().startsWith("00000")
            }
    }

    fun part2(input: List<String>): Int {
        val prefix = input[0]
        return generateSequence(1) { it + 1 }
            .first { index ->
                "$prefix$index".md5().startsWith("000000")
            }
    }

    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day04_test1")
    check(part1(testInput1) == 609043)
    val testInput2 = readInput("Day04_test2")
    check(part1(testInput2) == 1048970)

    val input = readInput("Day04")
    println("part 1 answer: ${part1(input)}")
    println("part 2 answer: ${part2(input)}")
}
