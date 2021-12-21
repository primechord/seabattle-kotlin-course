import entity.Cubes
import models.*
import org.jetbrains.exposed.sql.SchemaUtils

fun main() {
    createDirectory()
    try {
        val items = mutableListOf<Item>()
        if (IS_NEW_GAME) {
            repeat(COUNT_OF_BOMB_ITEM) { items.add(BombItem()) }
            repeat(COUNT_OF_HELP_ITEM) { items.add(HelpItem()) }
            repeat(COUNT_OF_LARGE) { items.add(StaticShip(size = 4)) }
            repeat(COUNT_OF_MEDIUM) { items.add(StaticShip(size = 2)) }
            repeat(COUNT_OF_SMALL) { items.add(StaticShip(size = 1)) }
        } else {
            restoreUserdata()
        }

        val battleField by BattleFieldDelegate(items)
        battleField.printToConsole()

        backupUserdata()
    } finally {
        DatabaseWrapper.wrap {
            SchemaUtils.drop(Cubes)
        }
    }
}
