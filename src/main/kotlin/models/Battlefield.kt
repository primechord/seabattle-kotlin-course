package models

import DatabaseWrapper
import FIELD_SIZE
import IS_NEW_GAME
import entity.Cube
import entity.Cubes
// import io.qameta.allure.Step
import org.jetbrains.exposed.sql.SchemaUtils

interface Field {
    operator fun get(x: Int, y: Int, z: Int): CubeState
    operator fun set(x: Int, y: Int, z: Int, cubeState: CubeState)
    fun getFieldState(): List<IndexedValue<CubeState>>
    fun getNeighbors(p: Point): MutableList<Point>
}

open class BattleField(private val fieldSize: Int = FIELD_SIZE) : Field {
    private val field = Array(fieldSize * fieldSize * fieldSize) { CubeState.UNKNOWN }

    override operator fun get(x: Int, y: Int, z: Int): CubeState {
        if (checkBoundaries(Point(x, y, z))) {
            return field[calculatePosition(Point(x, y, z))]
        } else throw IllegalArgumentException("get incorrect position: $x,$y,$z")
    }

    // @Step("Устанавливаем значения {x}-{y}-{z}")
    override operator fun set(x: Int, y: Int, z: Int, cubeState: CubeState) {
        if (checkBoundaries(Point(x, y, z)) && isAvailablePoint(Point(x, y, z))) {
            field[calculatePosition(Point(x, y, z))] = cubeState

            if (IS_NEW_GAME) {
                DatabaseWrapper.wrap {
                    SchemaUtils.create(Cubes)
                    Cube.new {
                        this.x = x
                        this.y = y
                        this.z = z
                        this.cubeState = cubeState
                    }
                }
            }
        } else throw IllegalArgumentException("set incorrect position: $x,$y,$z")
    }

    override fun getNeighbors(p: Point): MutableList<Point> {
        val result = mutableListOf<Point>()
        if (isAvailablePoint(p)) {
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
        } else throw IllegalArgumentException("Not available")
        return result
    }

    override fun getFieldState(): List<IndexedValue<CubeState>> {
        return field
            .withIndex()
            .filter { it.value != CubeState.UNKNOWN }
    }

    private fun calculatePosition(p: Point) = ((p.x * fieldSize * fieldSize) + (p.y * fieldSize) + p.z)

    private fun checkBoundaries(p: Point): Boolean {
        return p.x in 0 until fieldSize
                && p.y in 0 until fieldSize
                && p.z in 0 until fieldSize
    }

    fun isAvailablePoint(p: Point): Boolean = get(p.x, p.y, p.z) == CubeState.UNKNOWN

    // @Step("Проверяем доступность ячейки {p}")
    fun isAvailableCube(p: Point): Boolean {
        return try {
            val neighbors = getNeighbors(Point(p.x, p.y, p.z))
            neighbors.add(Point(p.x, p.y, p.z))
            neighbors
                .map { point -> isAvailablePoint(Point(point.x, point.y, point.z)) }
                .all { it }
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}
