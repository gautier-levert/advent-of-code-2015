fun main() {

    fun part1(input: List<String>): Int {
        val possibleMoves = input.asSequence()
            .map { line ->
                val (origin, destination, distance) = line.split(" to ", " = ")
                return@map (origin to destination) to distance.toInt()
            }
            .toMap()

        val allCities = possibleMoves.keys.asSequence()
            .flatMap { (a, b) -> sequenceOf(a, b) }
            .distinct()
            .toList()

        val allPossiblePath = allCities.permutations()

        return allPossiblePath.map { path ->
            path.asSequence()
                .windowed(2, 1) { (origin, destination) ->
                    possibleMoves[origin to destination]
                        ?: possibleMoves[destination to origin]
                        ?: throw IllegalStateException("Unable to travel from $origin to $destination")
                }
                .sum()
        }
            .min()
    }

    fun part2(input: List<String>): Int {
        val possibleMoves = input.asSequence()
            .map { line ->
                val (origin, destination, distance) = line.split(" to ", " = ")
                return@map (origin to destination) to distance.toInt()
            }
            .toMap()

        val allCities = possibleMoves.keys.asSequence()
            .flatMap { (a, b) -> sequenceOf(a, b) }
            .distinct()
            .toList()

        val allPossiblePath = allCities.permutations()

        return allPossiblePath.map { path ->
            path.asSequence()
                .windowed(2, 1) { (origin, destination) ->
                    possibleMoves[origin to destination]
                        ?: possibleMoves[destination to origin]
                        ?: throw IllegalStateException("Unable to travel from $origin to $destination")
                }
                .sum()
        }
            .max()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 605)
    check(part2(testInput) == 982)

    val input = readInput("Day09")
    println("part 1 answer: ${part1(input)}")
    println("part 2 answer: ${part2(input)}")
}
