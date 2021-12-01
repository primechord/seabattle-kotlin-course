package models

import COUNT_OF_BOMB_ITEM
import COUNT_OF_HELP_ITEM
import COUNT_OF_LARGE
import COUNT_OF_MEDIUM
import COUNT_OF_SMALL
import FIELD_SIZE
import kotlin.random.Random
import kotlin.reflect.KProperty

interface Spawner {
    fun spawnInRandomPlace(item: Item): List<Point>
}

class BattleFieldDelegate(private val items: MutableList<Item>) : Spawner {
    lateinit var bf: BattleField

    /* FIXME
        Иногда рядом объекты,
        иногда падает,
        иногда не создаются объекты,
        последний слой не используется */
    @Deprecated("Здесь много ошибок", level = DeprecationLevel.WARNING)
    override fun spawnInRandomPlace(item: Item): List<Point> {
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
            items.add(item)
        }

        println("Предмет $item создан $result")
        return result
    }

    private fun spawnNTimes(n: Int, item: Item): List<List<Point>> {
        return List(n) {
            spawnInRandomPlace(item)
        }
    }

    private fun createSmallStaticShips(): List<List<Point>> {
        val direction = Direction.values()[Random.nextInt(Direction.values().size)]
        return spawnNTimes(COUNT_OF_SMALL, StaticShip(1, direction))
    }

    private fun createMediumStaticShips(): List<List<Point>> {
        val direction = Direction.values()[Random.nextInt(Direction.values().size)]
        return spawnNTimes(COUNT_OF_MEDIUM, StaticShip(2, direction))
    }

    private fun createLargeStaticShips(): List<List<Point>> {
        val direction = Direction.values()[Random.nextInt(Direction.values().size)]
        return spawnNTimes(COUNT_OF_LARGE, StaticShip(4, direction)) // FIXME дубликаты
    }

    private fun createBombItems() {
        var count = 0
        while (count < COUNT_OF_BOMB_ITEM) {
            val point = generateRandomPosition()
            val direction = Direction.values()[Random.nextInt(Direction.values().size)]
            if (bf.isAvailableCube(point)) {
                items.add(BombItem(1, direction))
                bf[point.x, point.y, point.z] = CubeState.BOMB
                count++

                println("Предмет создан в точке $point")
            }
        }
    }

    private fun createHelpItems() {
        var count = 0
        while (count < COUNT_OF_HELP_ITEM) {
            val point = generateRandomPosition()
            val direction = Direction.values()[Random.nextInt(Direction.values().size)]
            if (bf.isAvailableCube(point)) {
                items.add(HelpItem(1, direction))
                bf[point.x, point.y, point.z] = CubeState.HELP
                count++

                println("Предмет создан в точке $point")
            }
        }
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): BattleField {
        bf = BattleField()

        createBombItems()
        createHelpItems()

        createLargeStaticShips()
        createMediumStaticShips()
        createSmallStaticShips()

        println(items)
        return bf
    }

    private fun generateRandomPosition(sizeUntil: Int = FIELD_SIZE): Point {
        val x = Random.nextInt(0, sizeUntil)
        val y = Random.nextInt(0, sizeUntil)
        val z = Random.nextInt(0, sizeUntil)
        return Point(x, y, z)
    }
}
