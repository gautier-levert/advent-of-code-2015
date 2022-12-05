fun main() {

    fun part1(input: List<String>): Int {
        return input.sumOf { line -> line.length } - input.asSequence()
            .map { line ->
                if (!line.startsWith('"') || !line.endsWith('"')) {
                    throw IllegalArgumentException("Invalid line `$line`")
                }
                line.subSequence(1, line.length - 1)
            }
            .map { line ->
                line.replace(Regex("\\\\x[0-9a-fA-F]{2}"), ".")
                    .replace(Regex("\\\\\""), ".")
                    .replace(Regex("\\\\\\\\"), ".")
            }
            .sumOf { it.length }
    }

    fun part2(input: List<String>): Int {
        val originalSize = input.sumOf { it.length }
        val newSize = input.map { line ->
            '"' + line
                .replace(Regex("\\\\"), "\\\\\\\\")
                .replace(Regex("\""), "\\\\\"") + '"'
        }
            .sumOf { it.length }

        return newSize - originalSize
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 12)
    check(part2(testInput) == 19)

    val input = readInput("Day08")
    println("part 1 answer: ${part1(input)}")
    println("part 2 answer: ${part2(input)}")
}
