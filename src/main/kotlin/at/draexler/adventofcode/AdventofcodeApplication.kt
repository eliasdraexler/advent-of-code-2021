package at.draexler.adventofcode

import java.awt.Point
import java.io.File
import java.lang.Integer.max
import java.lang.Integer.min
import java.util.*
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
//    println("Day 9 Puzzle 2 Time: ${measureTimeMillis { day9Puzzle2() }}")
//    println("Day 10 Puzzle 1 Time: ${measureTimeMillis { day10Puzzle1() }}")
//    println("Day 11 Puzzle 1 Time: ${measureTimeMillis { day11Puzzle1() }}")
//    println("Day 12 Puzzle 1 Time: ${measureTimeMillis { day12Puzzle1() }}")
//    println("Day 13 Puzzle 1 Time: ${measureTimeMillis { day13Puzzle1() }}")
//    println("Day 14 Puzzle 1 Time: ${measureTimeMillis { day14Puzzle1() }}")
//    println("Day 14 Puzzle 2 Time: ${measureTimeMillis { day14Puzzle2() }}")
//    println("Day 15 Puzzle 1 Time: ${measureTimeMillis { day15Puzzle1() }}")
//    println("Day 16 Puzzle 1 Time: ${measureTimeMillis { day16Puzzle1() }}")
    println("Day 17 Puzzle 1 Time: ${measureTimeMillis { day17Puzzle1() }}")
}

fun day17Puzzle1() {
    val targetAreaString =
        readFileAsLinesUsingUseLines("inputs/puzzle_17_1.txt")[0].replace("target area: ", "").split(", ")
    val xRangeString = targetAreaString[0].replace("x=", "").split("..")
    val yRangeString = targetAreaString[1].replace("y=", "").split("..")
    val xRange = (xRangeString[0].toInt()..xRangeString[1].toInt())
    val yRange = (yRangeString[0].toInt()..yRangeString[1].toInt())
    val possibleVelocities = mutableListOf<Point>()

    xRange.forEach { x->
        yRange.forEach { y ->

        }
    }

    println("Result ")
}

fun day16Puzzle1() {
    val hexaMap = mapOf(
        '0' to "0000",
        '1' to "0001",
        '2' to "0010",
        '3' to "0011",
        '4' to "0100",
        '5' to "0101",
        '6' to "0110",
        '7' to "0111",
        '8' to "1000",
        '9' to "1001",
        'A' to "1010",
        'B' to "1011",
        'C' to "1100",
        'D' to "1101",
        'E' to "1110",
        'F' to "1111"
    )
    val hexString = readFileAsLinesUsingUseLines("inputs/puzzle_16_0.txt")[0]
    val binaryString = hexString.map { hexaMap.get(it)!! }.joinToString(separator = "") { it }
    val versionSum = parsePacket(binaryString)

    println("Result $versionSum")
}

private fun parsePacket(binaryString: String): Int {
    var version = 0
    if (binaryString.replace("0", "").isEmpty()) {
        return version
    }
    val packetVersion = binaryToInt(binaryString.subSequence(0..2))
    version += packetVersion
    val packetType = binaryToInt(binaryString.subSequence(3..5))
    if (packetType == 4) {
        var lastPackageRead = false
        var count = 0
        val listOfNumbers = mutableListOf<String>()
        while (!lastPackageRead) {
            lastPackageRead = binaryString[6 + count] == '0'
            listOfNumbers.add(binaryString.subSequence(7 + count until 11 + count).toString())
            count += 5
        }
        val decimalNumber = binaryToInt(listOfNumbers.joinToString(separator = "") { it })
        return version + parsePacket(binaryString.subSequence(10 + count until binaryString.length).toString())
        println("Result")
    } else {
        //operator packet
        val lengthTypeId = binaryString[6]
        val subPacketLength = if (lengthTypeId == '0') 15 else 11
        if (subPacketLength == 11) {
            val countOfSubPackets = binaryToInt(binaryString.subSequence((7 until 7 + subPacketLength)))
            var startAt = 7 + subPacketLength
            val subPackets = mutableListOf<String>()
            (1..countOfSubPackets).forEach {
                subPackets.add(binaryString.subSequence(startAt until startAt + subPacketLength).toString())
                startAt += subPacketLength
            }
            subPackets.forEach {
                version += parsePacket(it)
            }
            println("Result")
        } else {
            val totalLengthInBits = binaryToInt(binaryString.subSequence((7..6 + subPacketLength)))
//            return version + parsePacket(binaryString.subSequence(startAt until binaryString.length).toString())
        }
    }
    return version
}


fun hexToBinary(hex: String): String {
    val i = hex.toInt(16)
    return Integer.toBinaryString(i)
}

fun binaryToInt(binary: String): Int {
    return binary.toInt(2)
}

fun binaryToInt(binary: CharSequence): Int {
    return binary.toString().toInt(2)
}

fun day15Puzzle1() {
    val list = readFileAsLinesUsingUseLines("inputs/puzzle_15_1.txt")
    val grid = list.map { it.map { Node15(UUID.randomUUID().toString(), it.digitToInt()) } }.toTypedArray()
    val newGrid = Array(grid.size * 5) { Array(grid.size * 5) { Node15("", 0) } }
    var xGrid = 0
    var yGrid = 0
    (0 until newGrid.size).forEach { y ->
        (0 until newGrid.size).forEach { x ->
            if (grid.size == (y - grid.size * yGrid)) yGrid++
            if (grid.size == (x - grid.size * xGrid)) xGrid++
            val oldY = y - grid.size * yGrid
            val oldX = x - grid.size * xGrid
            val increaseBy = xGrid + yGrid
            val oldNode = grid[oldY][oldX]
            newGrid[y][x] = Node15(UUID.randomUUID().toString(), increaseBy(oldNode.ownRisk, increaseBy))
        }
        xGrid = 0
    }
    newGrid.forEachIndexed { y, line ->
        line.forEachIndexed { x, value ->
            if (x < newGrid[0].size - 1) {
                val destination = newGrid[y][x + 1]
                newGrid[y][x].addDestination(destination, destination.ownRisk)
            }
            if (y < newGrid.size - 1) {
                val destination = newGrid[y + 1][x]
                newGrid[y][x].addDestination(destination, destination.ownRisk)
            }
            if (x > 0) {
                val destination = newGrid[y][x - 1]
                newGrid[y][x].addDestination(destination, destination.ownRisk)
            }
            if (y > 0) {
                val destination = newGrid[y - 1][x]
                newGrid[y][x].addDestination(destination, destination.ownRisk)
            }
        }
    }
    var graph = Graph()
    newGrid.forEach { it.forEach { graph.addNode(it) } }

    graph = calculateShortestPathFromSource(graph, newGrid[0][0])
//    var result = allPathRisks.minOrNull()!! - newGrid[0][0]
    var result = 0
    val endNode = newGrid[newGrid.size - 1][newGrid[0].size - 1]
    endNode.shortestPath.forEach { result += it.ownRisk }
    result -= newGrid[0][0].ownRisk
    result += endNode.ownRisk
    println("Result: ${endNode.distance} ${result}")

    newGrid.forEach {
        it.forEach { print(if (endNode.shortestPath.contains(it) || it == endNode) "X" else "_") }
        println()
    }

}

fun calculateShortestPathFromSource(graph: Graph, source: Node15): Graph {
    source.distance = 0
    val settledNodes: MutableSet<Node15> = HashSet()
    val unsettledNodes: MutableSet<Node15> = HashSet()
    unsettledNodes.add(source)
    while (unsettledNodes.size != 0) {
        val currentNode: Node15 = getLowestDistanceNode(unsettledNodes)!!
        unsettledNodes.remove(currentNode)
        for ((adjacentNode: Node15, edgeWeight: Int) in currentNode.adjacentNodes.entries) {
            if (!settledNodes.contains(adjacentNode)) {
                calculateMinimumDistance(adjacentNode, edgeWeight, currentNode)
                unsettledNodes.add(adjacentNode)
            }
        }
        settledNodes.add(currentNode)
    }
    return graph
}

private fun getLowestDistanceNode(unsettledNodes: Set<Node15>): Node15? {
    var lowestDistanceNode: Node15? = null
    var lowestDistance = Int.MAX_VALUE
    for (node in unsettledNodes) {
        val nodeDistance: Int = node.distance
        if (nodeDistance < lowestDistance) {
            lowestDistance = nodeDistance
            lowestDistanceNode = node
        }
    }
    return lowestDistanceNode
}

private fun calculateMinimumDistance(
    evaluationNode: Node15,
    edgeWeigh: Int, sourceNode: Node15
) {
    val sourceDistance: Int = sourceNode.distance
    if (sourceDistance + edgeWeigh < evaluationNode.distance) {
        evaluationNode.distance = sourceDistance + edgeWeigh
        val shortestPath: LinkedList<Node15> = LinkedList(sourceNode.shortestPath)
        shortestPath.add(sourceNode)
        evaluationNode.shortestPath = shortestPath
    }
}

class Graph {
    private val nodes: MutableSet<Node15> = HashSet()
    fun addNode(nodeA: Node15) {
        nodes.add(nodeA)
    } // getters and setters
}

class Node15(private val name: String, val ownRisk: Int) {
    var shortestPath: List<Node15> = LinkedList()
    var distance = Int.MAX_VALUE
    var adjacentNodes: MutableMap<Node15, Int> = HashMap()
    fun addDestination(destination: Node15, distance: Int) {
        adjacentNodes[destination] = distance
    }
}

fun increaseBy(startValue: Int, increaseBy: Int): Int {
    val newValue = startValue + increaseBy;
    return if (newValue > 9) {
        newValue - 9
    } else {
        newValue
    }
}

fun day14Puzzle2() {
    val list = readFileAsLinesUsingUseLines("inputs/puzzle_14_patrick.txt")
    var polymer: String = list[0]
    var input = (2 until list.size).map { list.get(it) }
        .map { it.split(" -> ") }.map { Pair(it[0], it[1]) }

    val pairs = mutableMapOf<String, Long>()
    (0 until polymer.length).forEach {
        if (it + 1 != polymer.length) {
            val key = "${polymer[it]}${polymer[it + 1]}"
            increment(pairs, key)
        }
    }
    (1..40).forEach {
        val pairsToIncrement = mutableListOf<Pair<String, Long>>()
        val pairsToDecrement = mutableListOf<Pair<String, Long>>()
        input.forEach {
            val pairCount = pairs[it.first]
            if (pairCount != null) {
                val firstPair = it.first[0] + it.second
                val secondPair = it.second + it.first[1]
                pairsToIncrement.add(Pair(firstPair, pairCount))
                pairsToIncrement.add(Pair(secondPair, pairCount))
                pairsToDecrement.add(Pair(it.first, pairCount))
            }
        }
        pairsToIncrement.forEach { increment(pairs, it.first, it.second) }
        pairsToDecrement.forEach { decrement(pairs, it.first, it.second) }
        println("Iteration: $it")
    }
    val result = mutableMapOf<Char, Long>()
    pairs.forEach {
        increment(result, it.key[0], it.value)
        increment(result, it.key[1], it.value)
    }
    val resultValue = (result.values.maxOrNull()!! / 2) - (result.values.minOrNull()!! / 2) + 1
    println("Result $resultValue")
}

fun <K> increment(map: MutableMap<K, Long>, key: K, incrementBy: Long = 1) {
    when (val count = map[key]) {
        null -> map[key] = incrementBy
        else -> map[key] = count + incrementBy
    }
}

fun <K> decrement(map: MutableMap<K, Long>, key: K, decrementBy: Long = 1) {
    when (val count = map[key]) {
        null -> return
        decrementBy -> map.remove(key)
        else -> map[key] = count - decrementBy
    }
}


fun day14Puzzle1() {
    val list = readFileAsLinesUsingUseLines("inputs/puzzle_14_1.txt")
    var polymer: String = list[0]
    var input = (2 until list.size).map { list.get(it) }
        .map { it.split(" -> ") }.map { Pair(it[0], it[1]) }

    (1..40).forEach {
        val newElements = mutableListOf<Pair<Int, String>>()
        input.forEach { pair ->
            var index = polymer.indexOf(pair.first)
            while (index >= 0) {
                val element = Pair(index + 1, pair.second)
                newElements.add(element)
                index = polymer.indexOf(pair.first, startIndex = index + 1)
            }
        }
        val newPolymer = StringBuilder(polymer)
        var indexShift = 0
        val sortedNewElementsMap = newElements.groupBy { it.first to it.second }.toMap()
            .mapValues { it.value.map { it.second }.joinToString { it } }
            .mapKeys { it.key.first }
            .toSortedMap()
        sortedNewElementsMap.forEach {
            newPolymer.insert(indexShift + it.key, it.value)
            indexShift += it.value.length
        }
        polymer = newPolymer.toString()
        println("Iteration $it")
    }
    val result = polymer.groupingBy { it }.eachCount().values.toList().sorted()
    println("Result ${result[result.size - 1] - result[0]}")

}

fun day13Puzzle1() {
    val list = readFileAsLinesUsingUseLines("inputs/puzzle_13_1.txt")
    val foldIndex = list.indexOfFirst { it.contains("fold") }
    val points = (0 until foldIndex - 1).map {
        var pointArray = list.get(it).split(",").map { it.toInt() }
        Point(pointArray[0], pointArray[1])
    }
    val foldInstructions = (foldIndex until list.size).map { list.get(it) }
        .map { it.replace("fold along ", "") } // y=8 x=6 ....
    val maxX = points.maxByOrNull { it.x }!!.x + 1
    val maxY = points.maxByOrNull { it.y }!!.y + 1
    val field = Array<Array<String?>>(maxY) { Array(maxX) { null } }

    points.forEach {
        field[it.y][it.x] = "#"
    }
//    listOf(foldInstructions.first()).map { it.split("=") }.forEach {
    foldInstructions.map { it.split("=") }.forEach {
        val foldBy = it[0]
        val foldLine = it[1].toInt()
        if (foldBy == "y") {
            (foldLine + 1 until field.size).forEach {
                val newIdx = foldLine - (it - foldLine)
                if (newIdx < 0) {
                    return@forEach
                }
                field[it].forEachIndexed { idx, value ->
                    if (field[newIdx][idx] != "#") {
                        field[newIdx][idx] = value
                    }
                    field[it][idx] = null
                }
            }
            field[foldLine].forEachIndexed { idx, value -> field[foldLine][idx] = null }
        }

        if (foldBy == "x") {
            (foldLine + 1 until field[0].size).forEach {
                val newIdx = foldLine - (it - foldLine)
                if (newIdx < 0) {
                    return@forEach
                }
                field.forEachIndexed { idx, value ->
                    if (field[idx][newIdx] != "#") {
                        field[idx][newIdx] = field[idx][it]
                    }
                    field[idx][it] = null
                }
            }
            field[foldLine].forEachIndexed { idx, value -> field[foldLine][idx] = null }
        }
    }
    val writer = File("result_13_2.txt").printWriter()
    field.forEach {
        writer.println(it.joinToString { it ?: "_" })
//        it.forEach {
//            print(it ?: "_")
//        }
//        println()
    }
    val result = field.flatMap { it.toList() }.filterNotNull().count()

    print("Result: $result")

}

fun day12Puzzle1() {
    val smallCaves = ("a".."z")
    val list = readFileAsLinesUsingUseLines("inputs/puzzle_12_1.txt")
    val uniqueNodes: Map<String, Node> = list.flatMap { it.split("-") }.distinct()
        .map { Node(it, smallCaves.contains(it)) }
        .map { it.name to it }.toMap()
    list.map { it.split("-") }.forEach {
        val start = it[0]
        val end = it[1]
        uniqueNodes[start]?.connect(uniqueNodes[end]!!)
    }
    val startNode = uniqueNodes["start"]!!
    val endNode = uniqueNodes["end"]!!
    endNode.smallNode = false
    val paths = mutableListOf<String>()
    uniqueNodes.values.filter { it.smallNode }.filter { it != startNode && it != endNode }.forEach {
        paths.addAll(traverseGraph(startNode, mutableListOf(), it))
    }
    val result = paths.filter { it.contains("end") }
        .distinct()
        .sorted()
    result.forEach {
        println(it)
    }
    println("Found ${result.size} paths")

}

fun traverseGraph(
    startNode: Node,
    lastNodes: List<Node>,
    allowedDuplicate: Node
): List<String> {
    val smallNodesVisited = lastNodes.filter { it.smallNode }.groupingBy { it }.eachCount()
    val availableNodes = startNode.connectedTo.filter {
        val timesVisited = smallNodesVisited[it]
        val canVisit = timesVisited == null || (it == allowedDuplicate && timesVisited == 1)
        canVisit
    }
    if (availableNodes.isEmpty() || startNode.name == "end") {
        return listOf(startNode.name)
    }
    val paths = mutableListOf<String>()
    availableNodes.forEach {
        val newLastNodes = mutableListOf(startNode)
        newLastNodes.addAll(lastNodes)
        paths.addAll(traverseGraph(it, newLastNodes, allowedDuplicate))
    }
    return paths.map { "${startNode.name},$it" }
}

fun traverseGraph(
    startNode: Node,
    uniqueNodes: Collection<Node>,
    parentNode: List<Node>,
    allowedDuplicate: Node
): List<String> {
    startNode.visited = true
    val availableNodes =
        startNode.connectedTo.filter { !(it.smallNode && it.visited) }.filter { it.name != "start" }.toMutableList()
    if (parentNode.count { it == allowedDuplicate } == 1 && startNode.connectedTo.contains(allowedDuplicate)) {
        availableNodes.add(allowedDuplicate)
    }
    if (availableNodes.isEmpty() || startNode.name == "end") {
        return listOf(startNode.name)
    }
    val paths = mutableListOf<String>()
    availableNodes.forEach {
        uniqueNodes.filter { it != startNode && !parentNode.contains(it) }.forEach { it.visited = false }
        startNode.visited = true
        val mutableListOf = mutableListOf(startNode)
        mutableListOf.addAll(parentNode)
        paths.addAll(traverseGraph(it, uniqueNodes, mutableListOf, allowedDuplicate))
    }
    return paths.map { "${startNode.name},$it" }
}

class Node(
    val name: String,
    var smallNode: Boolean,
    var visited: Boolean = false,
    val connectedTo: MutableList<Node> = mutableListOf()
) {
    fun connect(node: Node) {
        if (name == "start") {
            connectedTo.add(node)
        } else if (name == "end") {
            node.connectedTo.add(this)
        } else if (node.name == "start") {
            node.connectedTo.add(this)
        } else if (node.name == "end") {
            connectedTo.add(node)
        } else {
            connectedTo.add(node)
            node.connectedTo.add(this)
        }
    }

    fun resetVisited(node: Node) {
        visited = false
        connectedTo.filter { it != node }.forEach {
            it.resetVisited(this)
        }
    }
}

fun day11Puzzle1() {
    val list = readFileAsLinesUsingUseLines("inputs/puzzle_11_1.txt")
    val grid: List<MutableList<Int>> = list.map { it.toCharArray().map { it.digitToInt() }.toMutableList() }
    val flashGrid = Array(grid.size) { Array(grid[0].size) { false } }
    var countFlashes = 0
    var allFlashed = false
    var roundCounter = 0
    while (!allFlashed) {
        roundCounter++
        grid.forEachIndexed { indexRow, row ->
            row.forEachIndexed { indexColumn, fish ->
                grid[indexRow][indexColumn] += 1
            }
        }
        grid.forEachIndexed { indexRow, row ->
            row.forEachIndexed { indexColumn, fish ->
                if (fish == 10) {
                    grid[indexRow][indexColumn] -= 1 //remove one as method will add it again
                    countFlashes += flashAndIncrease(grid, flashGrid, indexRow, indexColumn)
                }
            }
        }
        val flashesThisRound = flashGrid.flatMap { row -> row.toList() }.count { it }
        countFlashes += flashesThisRound
        allFlashed = flashesThisRound == grid.size * grid[0].size
        grid.forEachIndexed { indexRow, row ->
            row.forEachIndexed { indexColumn, fish ->
                if (grid[indexRow][indexColumn] > 9)
                    grid[indexRow][indexColumn] = 0
                flashGrid[indexRow][indexColumn] = false
            }
        }
        printGrid(grid)
        println("Flashes: $flashesThisRound")
    }
    println("Result $countFlashes")
    println("After $roundCounter all flahsed")
}

fun printGrid(grid: List<MutableList<Int>>) {
    println()
    println("Grid:")
    grid.forEach {
        it.forEach { print(it) }
        println()
    }
}

fun flashAndIncrease(
    grid: List<MutableList<Int>>,
    flashGrid: Array<Array<Boolean>>,
    indexRow: Int,
    indexColumn: Int
): Int {
    var flashes = 0;
    grid[indexRow][indexColumn] += 1 //some neighbour flashed
    if (grid[indexRow][indexColumn] > 9 && !flashGrid[indexRow][indexColumn]) {
        flashGrid[indexRow][indexColumn] = true
        if (indexRow != 0)
            flashAndIncrease(grid, flashGrid, indexRow - 1, indexColumn) else 0 //up
        if (indexRow < grid.size - 1)
            flashAndIncrease(grid, flashGrid, indexRow + 1, indexColumn) else 0 //down
        if (indexColumn != 0)
            flashAndIncrease(grid, flashGrid, indexRow, indexColumn - 1) else 0 //left
        if (indexColumn < grid[indexRow].size - 1)
            flashAndIncrease(grid, flashGrid, indexRow, indexColumn + 1) else 0 //right
        if (upLeft(indexRow, indexColumn))
            flashAndIncrease(grid, flashGrid, indexRow - 1, indexColumn - 1) else 0 //up left
        if (upRight(indexRow, indexColumn, grid))
            flashAndIncrease(grid, flashGrid, indexRow - 1, indexColumn + 1) else 0 //up right
        if (downLeft(indexRow, grid, indexColumn))
            flashAndIncrease(grid, flashGrid, indexRow + 1, indexColumn - 1) else 0 //down left
        if (downRight(indexRow, grid, indexColumn))
            flashAndIncrease(grid, flashGrid, indexRow + 1, indexColumn + 1) else 0 //down left
    }
    return flashes
}

private fun upLeft(indexRow: Int, indexColumn: Int) = indexRow != 0 && indexColumn != 0

private fun upRight(
    indexRow: Int,
    indexColumn: Int,
    grid: List<MutableList<Int>>
) = indexRow != 0 && indexColumn < grid[indexRow].size - 1

private fun downLeft(
    indexRow: Int,
    grid: List<MutableList<Int>>,
    indexColumn: Int
) = indexRow < grid.size - 1 && indexColumn != 0

private fun downRight(
    indexRow: Int,
    grid: List<MutableList<Int>>,
    indexColumn: Int
) = indexRow < grid.size - 1 && indexColumn < grid[indexRow].size - 1

fun day9Puzzle2() {
    val list = readFileAsLinesUsingUseLines("inputs/puzzle_9_1.txt")
    val field: Array<Array<Int>> =
        list.map { it.toCharArray().map { it.digitToInt() }.toTypedArray() }.toTypedArray()
    val lowPoints = mutableListOf<Point>()
    field.forEachIndexed { lineIdx, line ->
        line.forEachIndexed { rowIdx, char ->
            val top = if (lineIdx == 0) Int.MAX_VALUE else field[lineIdx - 1][rowIdx]
            val bottom = if (lineIdx == list.size - 1) Int.MAX_VALUE else field[lineIdx + 1][rowIdx]
            val left = if (rowIdx == 0) Int.MAX_VALUE else field[lineIdx][rowIdx - 1]
            val right = if (rowIdx == list[lineIdx].length - 1) Int.MAX_VALUE else field[lineIdx][rowIdx + 1]
            if (char < top && char < bottom && char < left && char < right) {
                lowPoints.add(Point(lineIdx, rowIdx))
            }
        }
    }
    val basins = mutableListOf<Set<Point>>()
    lowPoints.forEach {
        val basin = mutableSetOf<Point>()
        basin(field, it.x, it.y, basin)
        basins.add(basin)
    }
    basins.sortByDescending { it.size }
    val result1 = basins[0].size
    val result2 = basins[1].size
    val result3 = basins[2].size

    println("Result1: ${result1}")
    println("Result2: ${result2}")
    println("Result3: ${result3}")
    println("Result: ${result1 * result2 * result3}")
}

fun basin(field: Array<Array<Int>>, x: Int, y: Int, values: MutableSet<Point>) {
    if (field[x][y] == 9) {
        return
    }
    values.add(Point(x, y))
    if (x != 0 && field[x - 1][y] > field[x][y]) {
        basin(field, x - 1, y, values) //above
    }
    if (x != 99 && field[x + 1][y] > field[x][y]) {
        basin(field, x + 1, y, values) //below
    }
    if (y != 0 && field[x][y - 1] > field[x][y]) {
        basin(field, x, y - 1, values) //left
    }
    if (y != 99 && field[x][y + 1] > field[x][y]) {
        basin(field, x, y + 1, values) //right
    }
}

fun day10Puzzle1() {
    val list = readFileAsLinesUsingUseLines("inputs/puzzle_10_1.txt")

    val openBrackets = listOf('(', '[', '{', '<')
    val syntaxErrors = mutableListOf<Char>()
    val missingBrackets = mutableListOf<List<Char>>()
    list.forEach { line ->
        var nextToClose: Stack<Char> = Stack()
        var syntaxError = false
        line.forEach {
            if (openBrackets.contains(it)) {
                nextToClose.push(
                    when (it) {
                        '(' -> ')'
                        '{' -> '}'
                        '[' -> ']'
                        '<' -> '>'
                        else -> throw IllegalArgumentException()
                    }
                )
            } else {
                val charToClose = nextToClose.pop()
                if (charToClose != it) {
                    syntaxErrors.add(it)
                    syntaxError = true
                    return@forEach
                }
            }
        }
        if (!syntaxError && nextToClose.size > 0) {
            missingBrackets.add(nextToClose.toList().reversed())
        }
    }
    var result = 0L

    val sortedList = missingBrackets.map {
        var lineResult = 0L
        it.map {
            when (it) {
                ')' -> 1
                ']' -> 2
                '}' -> 3
                '>' -> 4
                else -> throw IllegalStateException()
            }
        }.forEach {
            lineResult = lineResult * 5
            lineResult += it
        }
        lineResult
    }.sorted()
    result = sortedList[sortedList.size / 2]
    println("Result: ${result}")
}

fun findBasilSize(field: Array<Array<Int?>>, startLine: Int, startRow: Int): Pair<IntRange, Int> {
    if (startLine == field.size) return Pair((startRow..startRow), 0)
    if (field[startLine][startRow] == 9 || field[startLine][startRow] == null) return Pair((startRow..startRow), 0)
    val basinStartIdx = (0..startRow).reversed().firstOrNull { field[startLine][it] == 9 }?.plus(1) ?: 0
    val basinEndIdx =
        (startRow until field[startLine].size).firstOrNull { field[startLine][it] == 9 }?.minus(1)
            ?: field[startLine].size - 1
//    println("For ${startLine} $startRow found ${(basinStartIdx..basinEndIdx)}")
    val nextBasin = (basinStartIdx..basinEndIdx).map { findBasilSize(field, startLine + 1, it) }
        .groupBy { it.first }
        .values.map { it.map { it.second }.maxOrNull() ?: 0 }
        .sumOf { it }
    (basinStartIdx..basinEndIdx).forEach { field[startLine][it] = null }
    return Pair((basinStartIdx..basinEndIdx), (basinStartIdx..basinEndIdx).count() + nextBasin)
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

