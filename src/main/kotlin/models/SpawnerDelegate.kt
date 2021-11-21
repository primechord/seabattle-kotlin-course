package models

import FIELD_SIZE
import kotlin.random.Random

class SpawnerDelegate {
    companion object : Spawner {
        override fun BattleField.spawnInRandomPlace(item: Item): List<Point> {
            val sizeUntil = FIELD_SIZE - 1
            val result = mutableListOf<Point>()

            while (result.size < item.size) {
                val x = Random.nextInt(0, sizeUntil)
                val y = Random.nextInt(0, sizeUntil)
                val z = Random.nextInt(0, sizeUntil)
                while (this.isAvailableCube(Point(x, y, z))) { // FIXME Вышла лапша
                    result.add(Point(x, y, z))
                    break
                }

                for (i in (x + 1)..(x + item.size)) {
                    if (this.isAvailablePoint(Point(x, y, z)) && (x + item.size) <= FIELD_SIZE) {
                        result.add(Point(i, y, z))
                    } else {
                        result.clear()
                        break
                    }
                }
            }

            result.removeLast()
            result.forEach { p ->
                when (item) {
                    is SmallShip -> this[p.x, p.y, p.z] = CubeState.SMALL
                    is MediumShip -> this[p.x, p.y, p.z] = CubeState.MEDIUM
                    is LargeShip -> this[p.x, p.y, p.z] = CubeState.LARGE
                    is HelpItem -> this[p.x, p.y, p.z] = CubeState.HELP
                    is BombItem -> this[p.x, p.y, p.z] = CubeState.BOMB
                }
            }

            println("Предмет $item создан $result")
            return result
        }

        fun BattleField.createItems(
            countOfS: Int = 0,
            countOfM: Int = 0,
            countOfL: Int = 0,
            countOfB: Int = 0,
            countOfH: Int = 0
        ): MutableList<List<List<Point>>> {
            val result = mutableListOf<List<List<Point>>>()

            val listOfH = this.spawnNTimes(countOfH, HelpItem())
            val listOfB = this.spawnNTimes(countOfB, BombItem())

            val listOfL = this.spawnNTimes(countOfL, LargeShip())
            val listOfM = this.spawnNTimes(countOfM, MediumShip())
            val listOfS = this.spawnNTimes(countOfS, SmallShip())

            result.addAll(listOf(listOfB, listOfH, listOfL, listOfM, listOfS))
            return result
        }

        private fun BattleField.spawnNTimes(n: Int, item: Item): List<List<Point>> {
            val listOfItems = mutableListOf<List<Point>>()
            repeat(n) {
                val createdItem = spawnInRandomPlace(item)
                listOfItems.add(createdItem)
            }
            return listOfItems.toList()
        }
    }
}
