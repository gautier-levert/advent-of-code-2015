import java.io.File
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String {
    return MessageDigest.getInstance("MD5")
        .digest(toByteArray(Charsets.UTF_8))
        .joinToString("") { b -> "%02x".format(b) }
}


class Matrix<T : Any?>(
    val rows: Int,
    val columns: Int,
    init: (Int, Int) -> T
) {

    private fun indexToRow(index: Int): Int = index / rows

    private fun rowColumnToIndex(row: Int, column: Int): Int = row * rows + column

    private fun indexToColumn(index: Int): Int = index % rows

    private val values = Array<Any?>(rows * columns) { index -> init(indexToRow(index), indexToColumn(index)) }

    @Suppress("UNCHECKED_CAST")
    operator fun get(row: Int, column: Int): T {
        return values[rowColumnToIndex(row, column)] as T
    }

    operator fun set(row: Int, column: Int, value: T) {
        values[rowColumnToIndex(row, column)] = value
    }

    @Suppress("UNCHECKED_CAST")
    fun count(predicate: (T) -> Boolean): Int {
        return values.count { predicate(it as T) }
    }

    operator fun contains(element: T): Boolean {
        return values.contains(element)
    }

    @Suppress("UNCHECKED_CAST")
    fun random(): T {
        return values.random() as T
    }

    @Suppress("UNCHECKED_CAST")
    fun random(random: kotlin.random.Random): T {
        return values.random(random) as T
    }

    @Suppress("UNCHECKED_CAST")
    fun sumOf(selector: (T) -> Int): Int {
        return values.sumOf { selector(it as T) }
    }
}

fun Matrix<Int>.sum(): Int {
    return sumOf { it }
}

fun <T : Any?> List<T>.permutations(): List<List<T>> {
    if (isEmpty()) {
        return emptyList()
    }

    if (size == 1) {
        return listOf(this)
    }

    return indices.asSequence()
        .flatMap { index ->
            val element = elementAt(index)
            val listWithoutElement = subList(0, index) + subList(index + 1, size)

            listWithoutElement.permutations()
                .asSequence()
                .map { it + element }
        }
        .toList()
}
