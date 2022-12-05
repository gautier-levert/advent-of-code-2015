fun main() {


    fun lookAndSay(input: String): String {
        if (input.isEmpty()) {
            return ""
        }

        var c = input[0]
        var count = 1

        val sb = StringBuilder()
        for (i in 1 until input.length) {
            if (input[i] == c) {
                count++
            } else {
                sb.append(count).append(c)
                c = input[i]
                count = 1
            }
        }

        sb.append(count).append(c)

        return sb.toString()
    }

    fun part1(input: String): Int {
        var res = input
        for (i in 0 until 40) {
            res = lookAndSay(res)
        }
        return res.length
    }

    fun part2(input: String): Int {
        var res = input
        for (i in 0 until 50) {
            res = lookAndSay(res)
        }
        return res.length
    }

    val input = "3113322113"
    println("part 1 answer: ${part1(input)}")
    println("part 2 answer: ${part2(input)}")
}
