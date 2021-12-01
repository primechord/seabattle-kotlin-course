package models

interface Action {
    fun touch()
}

abstract class Item(
    open val size: Int,
    open val direction: Direction,
    private var found: Boolean = false
) : Action {
    override fun touch() {
        found = true
    }
}

abstract class Ship(
    size: Int,
    direction: Direction
) : Item(size, direction)

data class HelpItem(
    override val size: Int = 1,
    override val direction: Direction
) : Item(size, direction)

data class BombItem(
    override val size: Int = 1,
    override val direction: Direction
) : Item(size, direction)

data class StaticShip(
    override val size: Int = 1,
    override val direction: Direction
) : Item(size, direction)

data class DynamicShip(
    override val size: Int = 1,
    override val direction: Direction
) : Item(size, direction)
