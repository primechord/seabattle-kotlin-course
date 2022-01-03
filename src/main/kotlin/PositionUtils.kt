import models.Direction
import models.Point
import kotlin.random.Random

fun generateRandomPosition(sizeUntil: Int = FIELD_SIZE): Point {
    val x = Random.nextInt(0, sizeUntil)
    val y = Random.nextInt(0, sizeUntil)
    val z = Random.nextInt(0, sizeUntil)
    return Point(x, y, z)
}

fun nextPosition(point: Point, direction: Direction): Point? {
    val (x, y, z) = point
    return when (direction) {
        Direction.LEFT -> if (x > 0) Point(x - 1, y, z) else null
        Direction.RIGHT -> if (x < FIELD_SIZE - 1) Point(x + 1, y, z) else null
        Direction.BACK -> if (y > 0) Point(x, y - 1, z) else null
        Direction.FORWARD -> if (y < FIELD_SIZE - 1) Point(x, y + 1, z) else null
        Direction.UP -> if (z > 0) Point(x, y, z - 1) else null
        Direction.DOWN -> if (z < FIELD_SIZE - 1) Point(x, y, z + 1) else null
        Direction.UNKNOWN -> throw IllegalArgumentException("Unexpected")
    }
}
