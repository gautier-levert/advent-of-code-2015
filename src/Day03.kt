fun main() {

    data class Location(
        val x: Int = 0,
        val y: Int = 0,
    ) {
        fun north() = copy(y = y - 1)
        fun south() = copy(y = y + 1)
        fun east() = copy(x = x + 1)
        fun west() = copy(x = x - 1)
        fun move(c: Char) = when (c) {
            '^' -> north()
            'v' -> south()
            '>' -> east()
            '<' -> west()
            else -> this
        }
    }

    /*
     * Santa is delivering presents to an infinite two-dimensional grid of houses.
     *
     * He begins by delivering a present to the house at his starting location,
     * and then an elf at the North Pole calls him via radio and tells him where
     * to move next. Moves are always exactly one house to the north (^), south (v),
     * east (>), or west (<). After each move, he delivers another present to
     * the house at his new location.
     *
     * However, the elf back at the north pole has had a little too much eggnog,
     * and so his directions are a little off, and Santa ends up visiting some
     * houses more than once. How many houses receive at least one present?
     */
    fun part1(input: List<String>): Int {
        return input.asSequence()
            .map { line -> line.toCharArray() }
            .flatMap { it.asSequence() }
            .scan(Location()) { location, c -> location.move(c) }
            .distinct()
            .count()
    }

    /*
     * The next year, to speed up the process, Santa creates a robot version of
     * himself, Robo-Santa, to deliver presents with him.
     *
     * Santa and Robo-Santa start at the same location (delivering two presents to
     * the same starting house), then take turns moving based on instructions from
     * the elf, who is eggnoggedly reading from the same script as the previous
     * year.
     *
     * This year, how many houses receive at least one present?
     */
    fun part2(input: List<String>): Int {
        val (santa, robosanta) = input.asSequence()
            .map { line -> line.toCharArray() }
            .flatMap { it.asSequence() }
            .withIndex()
            .partition { it.index % 2 == 0 }

        val santaLocations = santa.asSequence()
            .map { it.value }
            .scan(Location()) { location, c -> location.move(c) }

        val roboSantaLocations = robosanta.asSequence()
            .map { it.value }
            .scan(Location()) { location, c -> location.move(c) }

        return (santaLocations + roboSantaLocations)
            .distinct()
            .count()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 11)

    val input = readInput("Day03")
    println("part 1 answer: ${part1(input)}")
    println("part 2 answer: ${part2(input)}")
}
