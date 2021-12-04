import models.Direction
import models.Point
import kotlin.random.Random

fun generateRandomPosition(sizeUntil: Int = FIELD_SIZE): Point {
    val x = Random.nextInt(0, sizeUntil)
    val y = Random.nextInt(0, sizeUntil)
    val z = Random.nextInt(0, sizeUntil)
    return Point(x, y, z)
}

fun nextPosition(point: Point, direction: Direction): Point {
    return when (direction) {
        Direction.UP -> TODO() // доделать
        Direction.DOWN -> point
        Direction.LEFT -> point
        Direction.RIGHT -> point
        Direction.FORWARD -> point
        Direction.BACK -> point
        Direction.UNKNOWN -> throw IllegalArgumentException("Не ждали такого")
    }
}