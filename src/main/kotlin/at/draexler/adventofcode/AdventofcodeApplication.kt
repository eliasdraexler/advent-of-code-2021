package at.draexler.adventofcode

import java.io.File
import kotlin.system.measureTimeMillis


fun main(args: Array<String>) {
//    println("Sonar Sweep 1 Time: ${measureTimeMillis { sonarSweep1() }}")
//    println("Sonar Sweep 2 Time: ${measureTimeMillis { sonarSweep2() }}")
    println("Day 2 Puzzle 1 Time: ${measureTimeMillis { day2Puzzle1() }}")
    println("Day 2 Puzzle 1 Time: ${measureTimeMillis { day2Puzzle2() }}")
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

