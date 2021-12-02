fun main() {

    /*
     * Santa is trying to deliver presents in a large apartment building, but he
     * can't find the right floor - the directions he got are a little confusing.
     * He starts on the ground floor (floor 0) and then follows the instructions
     * one character at a time.
     *
     * An opening parenthesis, (, means he should go up one floor, and a closing
     * parenthesis, ), means he should go down one floor.
     */
    fun part1(input: List<String>): Int {
        return input.asSequence()
            .map { it.toCharArray() }
            .flatMap { it.asSequence() }
            .fold(0) { floor, c ->
                when (c) {
                    '(' -> floor + 1
                    ')' -> floor - 1
                    else -> floor
                }
            }
    }

    /*
     * Now, given the same instructions, find the position of the first character
     * that causes him to enter the basement (floor -1). The first character in
     * the instructions has position 1, the second character has position 2,
     * and so on.
     */
    fun part2(input: List<String>): Int {
        return input.asSequence()
            .map { it.toCharArray() }
            .flatMap { it.asSequence() }
            .scan(0) { floor, value ->
                when (value) {
                    '(' -> floor + 1
                    ')' -> floor - 1
                    else -> floor
                }
            }
            .withIndex()
            .filter { it.value == -1 }
            .map { it.index }
            .first()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == -1)
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    println("part 1 answer: ${part1(input)}")
    println("part 2 answer: ${part2(input)}")
}
