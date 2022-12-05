import java.lang.Integer.max

enum class LightState {
    ON {
        override fun toggle() = OFF
    },
    OFF {
        override fun toggle() = ON
    };

    abstract fun toggle(): LightState
}

enum class LightAction(
    val lineStart: String,
) {
    TURN_ON("turn on "),
    TURN_OFF("turn off "),
    TOGGLE("toggle ");

    companion object {
        fun findFromLine(line: String): LightAction {
            return values().first { line.startsWith(it.lineStart) }
        }
    }
}

data class LightOperation(
    val start: Pair<Int, Int>,
    val end: Pair<Int, Int>,
    val action: LightAction,
) {
    companion object {
        fun parse(line: String): LightOperation {
            val action = LightAction.findFromLine(line)
            val (start, _, end) = line.substring(action.lineStart.length).split(' ')
            val (startRow, startColumn) = start.split(',')
            val (endRow, endColumn) = end.split(',')
            return LightOperation(
                startRow.toInt() to startColumn.toInt(),
                endRow.toInt() to endColumn.toInt(),
                action
            )
        }
    }
}

fun main() {

    fun part1(input: List<String>): Int {
        val lights = Matrix(1000, 1000) { _, _ -> LightState.OFF }

        input.map { LightOperation.parse(it) }
            .forEach { operation ->

                for (row in operation.start.first..operation.end.first) {
                    for (column in operation.start.second..operation.end.second) {
                        lights[row, column] = when (operation.action) {
                            LightAction.TURN_ON -> LightState.ON
                            LightAction.TURN_OFF -> LightState.OFF
                            LightAction.TOGGLE -> lights[row, column].toggle()
                        }
                    }
                }
            }

        return lights.count { it == LightState.ON }
    }

    fun part2(input: List<String>): Int {
        val lights = Matrix(1000, 1000) { _, _ -> 0 }

        input.map { LightOperation.parse(it) }
            .forEach { operation ->

                for (row in operation.start.first..operation.end.first) {
                    for (column in operation.start.second..operation.end.second) {
                        lights[row, column] = when (operation.action) {
                            LightAction.TURN_ON -> lights[row, column] + 1
                            LightAction.TURN_OFF -> max(lights[row, column] - 1, 0)
                            LightAction.TOGGLE -> lights[row, column] + 2
                        }
                    }
                }
            }

        return lights.sum()
    }

    val input = readInput("Day06")
    println("part 1 answer: ${part1(input)}")
    println("part 2 answer: ${part2(input)}")
}
