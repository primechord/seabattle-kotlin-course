package application

import COUNT_OF_BOMB_ITEM
import COUNT_OF_HELP_ITEM
import COUNT_OF_LARGE
import COUNT_OF_MEDIUM
import COUNT_OF_SMALL
import DatabaseWrapper
import IS_NEW_GAME
import backupUserdata
import createDirectory
import entity.Cubes
import models.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import printToConsole
import restoreUserdata

@SpringBootApplication
open class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)

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
