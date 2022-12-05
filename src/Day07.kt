interface WireInput {

    val signal: UShort

    companion object {
        fun parse(definition: String, signalProvider: (String) -> UShort): WireInput {
            val entries = definition.split(' ')
            return when (entries.size) {
                1 -> {
                    val input = entries[0]
                    Wire(input, signalProvider)
                }

                2 -> {
                    val (operator, label) = entries
                    if (operator != "NOT") {
                        throw IllegalArgumentException("`$definition` is not a supported operation")
                    }
                    NotGate(label, signalProvider)
                }

                3 -> {
                    val (a, operator, b) = entries
                    when (operator) {
                        "AND" -> AndGate(a, b, signalProvider)
                        "OR" -> OrGate(a, b, signalProvider)
                        "LSHIFT" -> LeftShiftGate(a, b.toInt(), signalProvider)
                        "RSHIFT" -> RightShiftGate(a, b.toInt(), signalProvider)
                        else -> throw IllegalArgumentException("`$definition` is not a supported operation")
                    }
                }

                else -> throw IllegalArgumentException("`$definition` is not a supported operation")
            }
        }
    }
}

data class Wire(
    val input: String,
    val signalProvider: (String) -> UShort
) : WireInput {

    override val signal: UShort by lazy { signalProvider(input) }
}

data class AndGate(
    val a: String,
    val b: String,
    val signalProvider: (String) -> UShort
) : WireInput {
    override val signal: UShort by lazy {
        signalProvider(a) and signalProvider(b)
    }
}

data class OrGate(
    val a: String,
    val b: String,
    val signalProvider: (String) -> UShort
) : WireInput {
    override val signal: UShort by lazy {
        signalProvider(a) or signalProvider(b)
    }
}

data class LeftShiftGate(
    val input: String,
    val bitCount: Int,
    val signalProvider: (String) -> UShort
) : WireInput {
    override val signal: UShort by lazy {
        (signalProvider(input).toUInt() shl bitCount).toUShort()
    }
}

data class RightShiftGate(
    val input: String,
    val bitCount: Int,
    val signalProvider: (String) -> UShort
) : WireInput {
    override val signal: UShort by lazy {
        (signalProvider(input).toUInt() shr bitCount).toUShort()
    }
}

data class NotGate(
    val input: String,
    val signalProvider: (String) -> UShort
) : WireInput {
    override val signal: UShort by lazy {
        signalProvider(input).inv()
    }
}

fun main() {

    fun part1(input: List<String>): Int {

        val dictionary = mutableMapOf<String, WireInput>()

        val provider = { request: String ->
            if (request.all { it.isDigit() }) {
                request.toUShort()
            } else {
                println("get signal of `$request`")
                dictionary[request]?.signal ?: throw IllegalArgumentException("Unable to find wire `$request`")
            }
        }

        input.asSequence()
            .forEach { line ->
                val (wireDef, label) = line.split(" -> ")
                val wire = WireInput.parse(wireDef, provider)
                dictionary[label] = wire
            }

        return dictionary["a"]!!.signal.toInt()
    }

    fun part2(input: List<String>, override: UShort): Int {
        val dictionary = mutableMapOf<String, WireInput>()

        val provider = { request: String ->
            if (request.all { it.isDigit() }) {
                request.toUShort()
            } else if (request == "b") {
                override
            } else {
                dictionary[request]?.signal ?: throw IllegalArgumentException("Unable to find wire `$request`")
            }
        }

        input.asSequence()
            .forEach { line ->
                val (wireDef, label) = line.split(" -> ")
                val wire = WireInput.parse(wireDef, provider)
                dictionary[label] = wire
            }

        return dictionary["a"]!!.signal.toInt()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 114)
    // check(part2(testInput) == ???)

    val input = readInput("Day07")
    println("part 1 answer: ${part1(input)}")
    println("part 2 answer: ${part2(input, part1(input).toUShort())}")
}
