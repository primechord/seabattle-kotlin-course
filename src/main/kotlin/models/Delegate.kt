package models

import DatabaseWrapper
import FIELD_SIZE
import IS_NEW_GAME
import entity.Cube
import generateRandomPosition
import kotlin.reflect.KProperty

class BattleFieldDelegate(private val items: List<Item>) {
    private lateinit var bf: BattleField

    /* FIXME
        Иногда рядом объекты,
        иногда падает,
        иногда не создаются объекты,
        последний слой не используется */
    @Deprecated("Здесь много ошибок", level = DeprecationLevel.WARNING)
    fun spawnInRandomPlace(item: Item) {
        val result = mutableListOf<Point>()

        val sizeUntil = FIELD_SIZE - 1
        while (result.size < item.size) {
            val point = generateRandomPosition(sizeUntil)
            while (bf.isAvailableCube(point)) {
                result.add(point)
                break
            }

            for (i in (point.x + 1)..(point.x + item.size)) {
                if (bf.isAvailablePoint(point) && (point.x + item.size) <= FIELD_SIZE) {
                    result.add(Point(i, point.y, point.z))
                } else {
                    result.clear()
                    break
                }
            }
        }

        result.removeLast()
        result.forEach { p ->
            when (item) {
                is StaticShip -> when (item.size) {
                    1 -> bf[p.x, p.y, p.z] = CubeState.SMALL
                    2 -> bf[p.x, p.y, p.z] = CubeState.MEDIUM
                    4 -> bf[p.x, p.y, p.z] = CubeState.LARGE
                }
                is HelpItem -> bf[p.x, p.y, p.z] = CubeState.HELP
                is BombItem -> bf[p.x, p.y, p.z] = CubeState.BOMB
            }
        }

        // println("$item placed $result")
    }

    fun canCreateShip(ship: Item): Boolean {
        return TODO()
    }

    private fun createStaticShips() {
        items.filterIsInstance<StaticShip>().forEach {
            val ship = it.copy(head = generateRandomPosition())
            spawnInRandomPlace(ship)
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
                // println("BombItem placed $point")
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
                // println("HelpItem placed $point")
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
