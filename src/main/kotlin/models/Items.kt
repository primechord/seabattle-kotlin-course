package models

interface Action {
    fun touch()
}

abstract class Item(
    open val size: Int = 1,
    open val direction: Direction,
    private var found: Boolean = false,
) : Action {
    override fun touch() {
        found = true
    }
}

data class HelpItem(
    override val direction: Direction = Direction.UNKNOWN,
) : Item(direction = direction)

data class BombItem(
    override val direction: Direction = Direction.UNKNOWN,
) : Item(direction = direction)

data class StaticShip(
    override val size: Int,
    override val direction: Direction = Direction.UNKNOWN,
    val head: Point = Point(Int.MIN_VALUE, Int.MIN_VALUE, Int.MIN_VALUE),
) : Item(direction = direction)

data class DynamicShip(
    override val size: Int,
    override val direction: Direction = Direction.UNKNOWN,
    val head: Point = Point(Int.MIN_VALUE, Int.MIN_VALUE, Int.MIN_VALUE),
) : Item(direction = direction)
