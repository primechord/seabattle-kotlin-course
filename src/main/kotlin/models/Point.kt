package models

data class Point(val x: Int, val y: Int, val z: Int)

enum class Direction(move: Point) {
    LEFT(Point(-1, 0, 0)),
    RIGHT(Point(1, 0, 0)),
    BACK(Point(0, -1, 0)),
    FORWARD(Point(0, 1, 0)),
    DOWN(Point(0, 0, -1)),
    UP(Point(0, 0, 1))
}
