package models

interface Action {
    fun touch()
}

abstract class Item(
    open val size: Int,
    private var direction: Direction? = null,
    private var found: Boolean = false
) : Action {
    override fun touch() {
        found = true
    }
}

abstract class Ship(size: Int) : Item(size)

data class HelpItem(override val size: Int = 1) : Item(size)

data class BombItem(override val size: Int = 1) : Item(size)

data class SmallShip(override val size: Int = 1) : Ship(size)

data class MediumShip(override val size: Int = 2) : Ship(size)

data class LargeShip(override val size: Int = 4) : Ship(size)
