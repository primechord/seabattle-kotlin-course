package models

import DatabaseWrapper
import IS_NEW_GAME
import entity.Cube
import generateRandomPosition
import nextPosition
import kotlin.random.Random
import kotlin.reflect.KProperty

class BattleFieldDelegate(private val items: List<Item>) {
    private lateinit var bf: BattleField

    /* FIXME
        Иногда рядом объекты,
        иногда не создаются объекты,
        последний слой не используется */

    private fun createStaticShips() {
        items.filterIsInstance<StaticShip>().forEach {
            while (true) {
                val ship = it.copy(
                    head = generateRandomPosition(),
                    direction = Direction.values()[Random.nextInt(Direction.values().size - 1)])

                if (canPlaceShip(ship)) {
                    placeShip(ship)
                    break
                }
            }
        }
    }

    private fun canPlaceShip(ship: StaticShip): Boolean {
        var currentX = ship.head.x
        var currentY = ship.head.y
        var currentZ = ship.head.z
        for (i in 1..ship.size) {
            if (!bf.isAvailableCube(Point(currentX, currentY, currentZ))) return false
            val next = nextPosition(Point(currentX, currentY, currentZ), ship.direction) ?: return false
            currentX = next.x
            currentY = next.y
            currentZ = next.z
        }
        return true
    }

    private fun placeShip(ship: StaticShip) {
        var currentX = ship.head.x
        var currentY = ship.head.y
        var currentZ = ship.head.z
        for (i in 1..ship.size) {
            bf[currentX, currentY, currentZ] = when (ship.size) {
                1 -> CubeState.SMALL
                2 -> CubeState.MEDIUM
                4 -> CubeState.LARGE
                else -> CubeState.UNKNOWN
            }
            val next = nextPosition(Point(currentX, currentY, currentZ), ship.direction)
            next?.let {
                currentX = next.x
                currentY = next.y
                currentZ = next.z
            }
        }
    }

    private fun createBombItems() {
        val bombItems = items.filterIsInstance<BombItem>()
        var current = 0
        while (current < bombItems.size) {
            val point = generateRandomPosition()
            if (bf.isAvailableCube(point)) {
                bf[point.x, point.y, point.z] = CubeState.BOMB
                current++
            }
        }
    }

    private fun createHelpItems() {
        val helpItems = items.filterIsInstance<HelpItem>()
        var current = 0
        while (current < helpItems.size) {
            val point = generateRandomPosition()
            if (bf.isAvailableCube(point)) {
                bf[point.x, point.y, point.z] = CubeState.HELP
                current++
            }
        }
    }

    private fun loadFromDatabaseIfNeed() {
        if (!IS_NEW_GAME) {
            DatabaseWrapper.wrap {
                Cube.all().forEach {
                    bf[it.x, it.y, it.z] = it.cubeState
                }
            }
        }
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): BattleField {
        bf = BattleField()

        createBombItems()
        createHelpItems()
        createStaticShips()

        loadFromDatabaseIfNeed()

        return bf
    }

}
