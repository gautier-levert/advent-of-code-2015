fun main() {

    data class Present(
        val length: Int,
        val width: Int,
        val height: Int
    ) {
        private val sides = intArrayOf(length * width, width * height, length * height)

        val surface = sides.sumOf { 2 * it }

        val minSide = sides.minOrNull()!!

        private val perimeters = intArrayOf(length + width, width + height, length + height).map { it * 2 }

        val minPerimeter = perimeters.minOrNull()!!

        val volume = length * width * height
    }

    /*
     * The elves are running low on wrapping paper, and so they need to submit an
     * order for more. They have a list of the dimensions (length l, width w, and
     * height h) of each present, and only want to order exactly as much as they
     * need.
     *
     * Fortunately, every present is a box (a perfect right rectangular prism),
     * which makes calculating the required wrapping paper for each gift a little
     * easier: find the surface area of the box, which is 2*l*w + 2*w*h + 2*h*l.
     * The elves also need a little extra paper for each present: the area of the
     * smallest side.
     */
    fun part1(input: List<String>): Int {
        return input.asSequence()
            .map { line ->
                val (l, w, h) = line.split("x").map { it.toInt() }
                return@map Present(l, w, h)
            }
            .sumOf {
                it.surface + it.minSide
            }
    }

    /*
     * The elves are also running low on ribbon. Ribbon is all the same width, so
     * they only have to worry about the length they need to order, which they
     * would again like to be exact.
     *
     * The ribbon required to wrap a present is the shortest distance around its
     * sides, or the smallest perimeter of any one face. Each present also
     * requires a bow made out of ribbon as well; the feet of ribbon required for
     * the perfect bow is equal to the cubic feet of volume of the present. Don't
     * ask how they tie the bow, though; they'll never tell.
     */
    fun part2(input: List<String>): Int {
        return input.asSequence()
            .map { line ->
                val (l, w, h) = line.split("x").map { it.toInt() }
                return@map Present(l, w, h)
            }
            .sumOf { it.minPerimeter + it.volume }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == (58 + 43))
    check(part2(testInput) == (34 + 14))

    val input = readInput("Day02")
    println("part 1 answer: ${part1(input)}")
    println("part 2 answer: ${part2(input)}")
}
