package at.draexler.adventofcode

import java.io.File
import kotlin.system.measureTimeMillis


fun main(args: Array<String>) {
//    println("Sonar Sweep 1 Time: ${measureTimeMillis { sonarSweep1() }}")
//    println("Sonar Sweep 2 Time: ${measureTimeMillis { sonarSweep2() }}")
//    println("Day 2 Puzzle 1 Time: ${measureTimeMillis { day2Puzzle1() }}")
//    println("Day 2 Puzzle 2 Time: ${measureTimeMillis { day2Puzzle2() }}")
//    println("Day 3 Puzzle 1 Time: ${measureTimeMillis { day3Puzzle1() }}")
    println("Day 3 Puzzle 2 Time: ${measureTimeMillis { day3Puzzle2() }}")
}

fun day3Puzzle2() {
    println("Day 3 Puzzle 2")
    val list = readFileAsLinesUsingUseLines("inputs/puzzle_3_2.txt")
    val oxygenGeneratorRating = findNumber(list, 0, true)
    val scrubberRating = findNumber(list, 0, false)

    println("Result: ${oxygenGeneratorRating * scrubberRating}")
}

fun findNumber(list: List<String>, idx: Int, getMostCommon: Boolean): Int {
    if (list.size == 1) {
        return Integer.parseInt(list[0], 2)
    }
    val count = buildCountOnes(list, idx)
    val newNumber = if (getMostCommon) buildMostCommon(count, list.size) else buildLeastCommon(count, list.size)
    return findNumber(list.partition { it[idx] == newNumber }.first, idx + 1, getMostCommon)
}

private fun buildMostCommon(count: Int, size: Int) = if (count * 2 >= size) '0' else '1'

private fun buildLeastCommon(count: Int, size: Int) = if (count * 2 >= size) '1' else '0'

private fun buildCountOnes(list: List<String>, idx: Int): Int = list.count { it[idx] == '1' }


fun day3Puzzle1() {
    println("Day 3 Puzzle 1")
    val list = readFileAsLinesUsingUseLines("inputs/puzzle_3_1.txt")
    val counts = IntArray(12)
    list.forEach { report ->
        report.forEachIndexed { index, char ->
            if (char == '1') counts[index] += 1
        }
    }
    val gamma = Integer.parseInt(counts.map { if (it > list.size / 2) "1" else "0" }.joinToString (separator = "") { it }, 2)
    val epsilon = Integer.parseInt(counts.map { if (it > list.size / 2) "0" else "1" }.joinToString (separator = "") { it }, 2)
    println("Gamma: $gamma")
    println("Epsilon: $epsilon")
    println("Result: ${gamma * epsilon}")
}


private fun sonarSweep1() {
    println("Sonar Sweep 1")
    val list = readFileAsLinesUsingUseLines("inputs/puzzle_1_1.txt")
    var counter = 0;
    var lastValue = list[0].toInt();
    list.map { it.toInt() }.forEach {
        if (it > lastValue) {
            counter++
        }
        lastValue = it
    }
    println(counter)
}

private fun sonarSweep2() {
    println("Sonar Sweep 2")
    val list = readFileAsLinesUsingUseLines("inputs/puzzle_1_2.txt").map { it.toInt() }
    var counter = 0
    var lastValue = (0..2).sumOf { list[it] }

    list.forEachIndexed { index, _ ->
        if (index < list.size - 3) {
            val newValue = (index..index + 2).sumOf { list[it] }
            if (newValue > lastValue) counter++
            lastValue = newValue
        }
    }
    println(counter)
}

fun day2Puzzle1() {
    println("Day 2 Puzzle 1")
    val list = readFileAsLinesUsingUseLines("inputs/puzzle_2_1.txt").map { it.split(" ") }
    var x = 0
    var y = 0
    list.forEach {
        val command = it[0]
        val speed = it[1].toInt()
        when (command) {
            "forward" -> x += speed
            "down" -> y += speed
            "up" -> y -= speed
        }
    }
    println(x * y)
}

fun day2Puzzle2() {
    println("Day 2 Puzzle 2")
    val list = readFileAsLinesUsingUseLines("inputs/puzzle_2_2.txt").map { it.split(" ") }
    var x = 0
    var y = 0
    var aim = 0
    list.forEach {
        val command = it[0]
        val speed = it[1].toInt()
        when (command) {
            "forward" -> {
                x += speed
                y += aim * speed
            }
            "down" -> aim += speed
            "up" -> aim -= speed
        }
    }
    println(x * y)
}

fun readFileAsLinesUsingUseLines(fileName: String): List<String> = File(fileName).useLines { it.toList() }

