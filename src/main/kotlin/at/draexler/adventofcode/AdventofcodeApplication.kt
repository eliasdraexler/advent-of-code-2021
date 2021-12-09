package at.draexler.adventofcode

import java.awt.Point
import java.io.File
import java.lang.Integer.max
import java.lang.Integer.min
import kotlin.system.measureTimeMillis


fun main(args: Array<String>) {
//    println("Sonar Sweep 1 Time: ${measureTimeMillis { sonarSweep1() }}")
//    println("Sonar Sweep 2 Time: ${measureTimeMillis { sonarSweep2() }}")
//    println("Day 2 Puzzle 1 Time: ${measureTimeMillis { day2Puzzle1() }}")
//    println("Day 2 Puzzle 2 Time: ${measureTimeMillis { day2Puzzle2() }}")
//    println("Day 3 Puzzle 1 Time: ${measureTimeMillis { day3Puzzle1() }}")
//    println("Day 3 Puzzle 2 Time: ${measureTimeMillis { day3Puzzle2() }}")
//    println("Day 4 Puzzle 1 Time: ${measureTimeMillis { day4Puzzle1() }}")
//    println("Day 5 Puzzle 2 Time: ${measureTimeMillis { day5Puzzle2() }}")
//    println("Day 6 Puzzle 1 Time: ${measureTimeMillis { day6Puzzle1() }}")
//    println("Day 7 Puzzle 1 Time: ${measureTimeMillis { day7Puzzle1() }}")
//    println("Day 8 Puzzle 1 Time: ${measureTimeMillis { day8Puzzle1() }}")
//    println("Day 9 Puzzle 1 Time: ${measureTimeMillis { day9Puzzle1() }}")
    println("Day 9 Puzzle 2 Time: ${measureTimeMillis { day9Puzzle2() }}")
}

fun day9Puzzle2() {
    val list = readFileAsLinesUsingUseLines("inputs/puzzle_9_0.txt")
    val field = list.map { it.toCharArray().map { it.digitToInt() } }.toTypedArray()
    var result1 = Pair(-1..0, 0)
    var result2 = Pair(-1..0, 0)
    var result3 = Pair(-1..0, 0)
    field.forEachIndexed { lineIdx, line ->
        line.forEachIndexed { rowIdx, char ->
            if(char == 9) return@forEachIndexed
            val basilSize = findBasilSize(field, lineIdx, rowIdx)
            println("Current Basil size ${basilSize}")
            when {
                basilSize.first != result1.first && basilSize.second > result1.second-> {
                    println("Added as result1")
                    result3 = result2
                    result2 = result1
                    result1 = basilSize
                }
                basilSize.first != result2.first && basilSize.first != result1.first && basilSize.second > result2.second -> {
                    println("Added as result2")
                    result3 = result2
                    result2 = basilSize
                }
                basilSize.first != result3.first && basilSize.first != result2.first && basilSize.first != result1.first && basilSize.second > result3.second -> {
                    println("Added as result3")
                    result3 = basilSize
                }
            }

        }
    }

    println("Result1: ${result1}")
    println("Result2: ${result2}")
    println("Result3: ${result3}")
    println("Result: ${result1.second * result2.second * result3.second}")
}

fun findBasilSize(field:Array<List<Int>>, startLine:Int, startRow:Int): Pair<IntRange, Int> {
    if(startLine == field.size) return Pair((startRow..startRow), 0)
    if(field[startLine][startRow] == 9) return Pair((startRow..startRow), 0)
    val basinStartIdx = (0..startRow).reversed().firstOrNull { field[startLine][it] == 9 }?.plus(1) ?: 0
    val basinEndIdx =
        (startRow until field[startLine].size).firstOrNull { field[startLine][it] == 9 }?.minus(1) ?: field[startLine].size - 1
    println("For ${startLine} $startRow found ${(basinStartIdx .. basinEndIdx)}")
    val nextBasin = (basinStartIdx..basinEndIdx).map { findBasilSize(field, startLine + 1, it) }
        .groupBy { it.first }
        .values.map { it.map { it.second }.maxOrNull()?: 0 }
        .sumOf { it }
    return Pair((basinStartIdx .. basinEndIdx),(basinStartIdx .. basinEndIdx).count() + nextBasin)
}


fun day9Puzzle1() {
    val list = readFileAsLinesUsingUseLines("inputs/puzzle_9_1.txt")
    val field = list.map { it.toCharArray().map { it.digitToInt() } }.toTypedArray()
    var result = 0
    field.forEachIndexed { lineIdx, line ->
        line.forEachIndexed { rowIdx, char ->
            val top = if (lineIdx == 0) Int.MAX_VALUE else field[lineIdx - 1][rowIdx]
            val bottom = if (lineIdx == list.size - 1) Int.MAX_VALUE else field[lineIdx + 1][rowIdx]
            val left = if (rowIdx == 0) Int.MAX_VALUE else field[lineIdx][rowIdx - 1]
            val right = if (rowIdx == list[lineIdx].length - 1) Int.MAX_VALUE else field[lineIdx][rowIdx + 1]
            if (char < top && char < bottom && char < left && char < right) {
                result += 1 + char
            }
        }
    }

    println("Result: $result")
}

fun day8Puzzle1() {
    val list = readFileAsLinesUsingUseLines("inputs/puzzle_8_1.txt")
    val uniqueSignalPatterns = list.map { it.split(" | ") }
    var result = 0;
    uniqueSignalPatterns.forEach {
        val signals = it[0].split(" ")
        val output = it[1].split(" ")
        val digit = Digit()
        val signalForOne = signals.find { it.length == 2 }!!
        val signalForSeven = signals.find { it.length == 3 }!!
        val signalForFour = signals.find { it.length == 4 }!!
        digit.top = signalForSeven.toCharArray().filter { !signalForOne.contains(it) }[0]
        val signalForThree = signals.filter { it.length == 5 }.find { signalLengthFive ->
            signalLengthFive.toList().containsAll(signalForSeven.toList())
        }!!
        digit.bottom =
            signalForThree.toList().filter { !signalForSeven.contains(it) }.filter { !signalForFour.contains(it) }[0]
        val signalForFive =
            signals.filter { it.length == 5 && !it.toList().sorted().equals(signalForThree.toList().sorted()) }
                .find { signalForFour.contains(it.toList().find { !signalForThree.contains(it) }!!) }!!
        digit.topLeft = signalForFive.find { !signalForThree.contains(it) }
        digit.middle = signalForFour.filter { it != digit.topLeft }.find { !signalForOne.contains(it) }!!
        val filter =
            signalForFive.filter { it != digit.bottom && it != digit.middle && it != digit.top && it != digit.topLeft }
        digit.bottomRight =
            filter
                .find { signalForOne.contains(it) }!!
        digit.topRight = signalForOne.find { it != digit.bottomRight }!!
        digit.bottomLeft = listOf('a', 'b', 'c', 'd', 'e', 'f', 'g')
            .find {
                !listOf(
                    digit.top,
                    digit.topLeft,
                    digit.topRight,
                    digit.middle,
                    digit.bottomRight,
                    digit.bottom
                ).contains(it)
            }!!

        result += output.map {
            digit.decode(it)
        }.joinToString(separator = "") { it.toString() }.toInt()
    }
    println("Result: $result")
}

data class Digit(
    var top: Char? = null,
    var topLeft: Char? = null,
    var topRight: Char? = null,
    var middle: Char? = null,
    var bottomLeft: Char? = null,
    var bottomRight: Char? = null,
    var bottom: Char? = null
) {
    fun decode(digitString: String): Int {
        return when {
            digitString.length == 2 -> 1
            digitString.length == 4 -> 4
            digitString.length == 3 -> 7
            digitString.length == 7 -> 8
            isZero(digitString) -> 0
            isTwo(digitString) -> 2
            isThree(digitString) -> 3
            isFive(digitString) -> 5
            isSix(digitString) -> 6
            isNine(digitString) -> 9
            else -> throw IllegalStateException()
        }
    }

    private fun isZero(digitString: String) = digitString.length == 6 &&
            digitString.toCollection(mutableListOf())
                .containsAll(listOf(top, topRight, topLeft, bottomRight, bottomLeft, bottom))

    private fun isTwo(digitString: String) = digitString.length == 5 &&
            digitString.toCollection(mutableListOf()).containsAll(listOf(top, topRight, middle, bottomLeft, bottom))

    private fun isThree(digitString: String) = digitString.length == 5 &&
            digitString.toCollection(mutableListOf()).containsAll(listOf(top, topRight, middle, bottomRight, bottom))

    private fun isFive(digitString: String) = digitString.length == 5 &&
            digitString.toCollection(mutableListOf()).containsAll(listOf(top, topLeft, middle, bottomRight, bottom))

    private fun isSix(digitString: String) = digitString.length == 6 &&
            digitString.toCollection(mutableListOf())
                .containsAll(listOf(top, topLeft, middle, bottomLeft, bottomRight, bottom))

    private fun isNine(digitString: String) = digitString.length == 6 &&
            digitString.toCollection(mutableListOf())
                .containsAll(listOf(top, topRight, topLeft, middle, bottomRight, bottom))

}

fun day7Puzzle1() {
    val list: MutableList<Int> = readFileAsLinesUsingUseLines("inputs/puzzle_7_1.txt")
        .flatMap { it.split(",") }
        .map { it.toInt() }
        .toMutableList()

    val min = list.minOrNull()!!
    val max = list.maxOrNull()!!

    var fuelNeeded: Int = Int.MAX_VALUE

    (min..max).forEach { position ->
        var fuel = 0
        list.forEach { crabPosition ->
            val steps = if (position > crabPosition) position - crabPosition else crabPosition - position;
            (1..steps).forEach { fuel += it }
        }
        if (fuelNeeded > fuel) fuelNeeded = fuel
    }
    println("Result: ${fuelNeeded}")
}

fun day6Puzzle1() {
    val list: MutableList<Int> = readFileAsLinesUsingUseLines("inputs/puzzle_6_1.txt")
        .flatMap { it.split(",") }
        .map { it.toInt() }
        .toMutableList()

    val results = Array(9) { 0L }
    list.forEach {
        results[it]++
    }
    (1..256).forEach {
        var readyToBreed = results[0]
        (1 until results.size).forEach {
            results[it - 1] = results[it]
        }
        results[6] += readyToBreed
        results[8] = readyToBreed
        println("Day: $it Result: ${results.sum()}")
    }

    var result = 0L

    results.forEach { result = result.plus(it) }
    println("Result: ${result}")
}

fun run(runsRemaining: Int): Int {
    var runs = runsRemaining
    var firstRun = true
    var result = 1
    while (runs > 0) {
        result += 1
        runs -= if (firstRun) 9 else 6
        firstRun = false
        result += run(runs)
    }
    return result
}

fun day5Puzzle2() {
    val list = readFileAsLinesUsingUseLines("inputs/puzzle_5_1.txt")
    val pointPairs = list.map { it.replace(" ", "") }.map { it.split("->") }
        .map { Pair(getPoint(it[0]), getPoint(it[1])) }
    var maxX = 0
    var maxY = 0
    pointPairs.forEach {
        if (it.first.x > maxX) maxX = it.first.x
        if (it.second.x > maxX) maxX = it.second.x
        if (it.first.y > maxY) maxY = it.first.y
        if (it.second.y > maxY) maxY = it.second.y
    }
    val system = Array(maxY + 1) { Array(maxX + 1) { 0 } }
    pointPairs.forEach {
        val pointsToDraw = getPointsToDraw(it.first, it.second)
        pointsToDraw.forEach { pointToDraw ->
            system[pointToDraw.y][pointToDraw.x] += 1
        }
    }
    var intersectionCount = 0
    system.forEach { row ->
        row.forEach { field ->
            if (field > 1) intersectionCount++
        }
    }
    println("Result: $intersectionCount")
}

fun getPointsToDraw(pointFrom: Point, pointTo: Point): List<Point> {
    return when {
        pointFrom.x == pointTo.x -> {
            val from = min(pointFrom.y, pointTo.y)
            val to = max(pointFrom.y, pointTo.y)
            (from..to).map { Point(pointFrom.x, it) }
        }
        pointFrom.y == pointTo.y -> {
            val from = min(pointFrom.x, pointTo.x)
            val to = max(pointFrom.x, pointTo.x)
            (from..to).map { Point(it, pointFrom.y) }
        }
        else -> {
            calculateDiagonal(pointFrom, pointTo)
        }
    }
}

fun calculateDiagonal(pointFrom: Point, pointTo: Point): List<Point> {
    val lowerPoint = if (pointFrom.y < pointTo.y) pointFrom else pointTo
    val higherPoint = if (pointFrom.y > pointTo.y) pointFrom else pointTo
    val pointLeft = lowerPoint.x > higherPoint.x
    val points = mutableListOf<Point>()
    if (pointLeft) {
        var x = lowerPoint.x
        var y = lowerPoint.y
        while (x >= higherPoint.x && y <= higherPoint.y) {
            points.add(Point(x, y))
            x--
            y++
        }
    } else {
        var x = lowerPoint.x;
        var y = lowerPoint.y
        while (x <= higherPoint.x && y <= higherPoint.y) {
            points.add(Point(x, y))
            x++
            y++
        }
    }
    return points
}

private fun getPoint(xy: String): Point {
    val split = xy.split(",").map { it.toInt() }
    return Point(split[0], split[1])
}

fun day4Puzzle1() {
    println("Day 3 Puzzle 2")
    val list = readFileAsLinesUsingUseLines("inputs/puzzle_4_1.txt")
    val bingoInputs = list[0].split(",").map { it.toInt() }
    val bingoFields = getBingoFields(list)

    val result = playBingo(bingoInputs, bingoFields)

    println("Result: $result")
}

private fun playBingo(
    bingoInputs: List<Int>,
    bingoFields: List<Array<Array<Int?>>>
): Int {
    val boards = (0..bingoFields.size - 1).map { false }.toMutableList()
    bingoInputs.forEach { bingoNumber ->
        var countField = 0
        bingoFields.map { bingoField ->
//            if (boards[countField]) return@map bingoField
            bingoField.forEach { bingoRow ->
                bingoRow.forEachIndexed { idx, value ->
                    if (value == bingoNumber) {
                        bingoRow[idx] = null
                    }
                }
            }
            (0..4).forEach { row ->
                if (bingoField[row].filterNotNull().isEmpty()) {
                    if (boards.count { !it } == 1 && !boards[countField]) {
                        return calculateResult(bingoField, bingoNumber)
                    }
                    boards[countField] = true
                    countField++
                    return@map bingoField
                }
                val colValues = listOf<Int?>().toMutableList()
                (0..4).forEach { col ->
                    colValues.add(bingoField[col][row])
                }
                if (colValues.filterNotNull().isEmpty()) {
                    if (boards.count { !it } == 1 && !boards[countField]) {
                        return calculateResult(bingoField, bingoNumber)
                    }
                    boards[countField] = true
                    countField++
                    return@map bingoField
                }
            }
            countField++
            bingoField
        }
    }
    throw IllegalStateException()
}

fun calculateResult(bingoField: Array<Array<Int?>>, bingoNumber: Int): Int {
    var sum = 0
    bingoField.forEach { rows ->
        sum += rows.filterNotNull().sumOf { it }
    }
    return sum * bingoNumber
}

fun getBingoFields(list: List<String>): List<Array<Array<Int?>>> {
    val filter = list.subList(2, list.size).filter { it.isNotEmpty() }
    return filter.chunked(5) {
        val bingoField = Array<Array<Int?>>(5) { Array(5) { null } }
        it.forEachIndexed { rowIndex, row ->
            row.split(" ").filter { it.isNotBlank() && it.isNotEmpty() }.forEachIndexed { colIndex, col ->
                bingoField[rowIndex][colIndex] = col.toInt()
            }
        }
        bingoField
    }
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
    val gamma =
        Integer.parseInt(counts.map { if (it > list.size / 2) "1" else "0" }.joinToString(separator = "") { it }, 2)
    val epsilon =
        Integer.parseInt(counts.map { if (it > list.size / 2) "0" else "1" }.joinToString(separator = "") { it }, 2)
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

