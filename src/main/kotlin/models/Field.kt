package models

import FIELD_SIZE

enum class CubeState {
    UNKNOWN,
    HIT,
    SMALL,
    MEDIUM,
    LARGE,
    HELP,
    BOMB
}

interface Field {
    operator fun get(x: Int, y: Int, z: Int): CubeState
    operator fun set(x: Int, y: Int, z: Int, cubeState: CubeState)
    fun getFieldState(): List<Pair<Int, CubeState>>
    fun getNeighbors(p: Point): MutableList<Point>
}

interface Spawner {
    fun BattleField.spawnInRandomPlace(item: Item): List<Point>
}

class BattleField(private val fieldSize: Int = FIELD_SIZE) : Field, Spawner by SpawnerDelegate {
    private val field = Array(fieldSize * fieldSize * fieldSize) { CubeState.UNKNOWN }

    override operator fun get(x: Int, y: Int, z: Int): CubeState {
        if (checkBoundaries(Point(x, y, z))) {
            return field[calculatePosition(Point(x, y, z))]
        } else throw IllegalArgumentException("get incorrect position: $x,$y,$z")
    }

    override operator fun set(x: Int, y: Int, z: Int, cubeState: CubeState) {
        if (checkBoundaries(Point(x, y, z)) && isAvailablePoint(Point(x, y, z))) {
            field[calculatePosition(Point(x, y, z))] = cubeState
        } else throw IllegalArgumentException("set incorrect position: $x,$y,$z")
    }

    override fun getNeighbors(p: Point): MutableList<Point> {
        val result = mutableListOf<Point>()
        listOf(p.x - 1, p.x, p.x + 1).forEach { first ->
            listOf(p.y - 1, p.y, p.y + 1).forEach { second ->
                listOf(p.z - 1, p.z, p.z + 1).forEach { third ->
                    if (first >= 0 && second >= 0 && third >= 0) {
                        result.add(Point(first, second, third))
                    }
                }
            }
        }
        result.remove(Point(p.x, p.y, p.z))
        return result
    }

    override fun getFieldState(): List<Pair<Int, CubeState>> {
        return field
            .mapIndexed { index, cubeState -> index to cubeState }
            .filter { it.second != CubeState.UNKNOWN }
    }

    private fun calculatePosition(p: Point) = ((p.x * fieldSize * fieldSize) + (p.y * fieldSize) + p.z)

    private fun checkBoundaries(p: Point): Boolean {
        return p.x in 0 until fieldSize
                && p.y in 0 until fieldSize
                && p.z in 0 until fieldSize
    }

    fun isAvailablePoint(p: Point): Boolean = get(p.x, p.y, p.z) == CubeState.UNKNOWN

    fun isAvailableCube(p: Point): Boolean {
        val neighbors = getNeighbors(Point(p.x, p.y, p.z))
        neighbors.add(Point(p.x, p.y, p.z))
        return neighbors
            .map { point -> isAvailablePoint(Point(point.x, point.y, point.z)) }
            .all { it }
    }

}
