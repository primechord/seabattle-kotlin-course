import models.*

fun main() {
    val items = mutableListOf<Item>()
    repeat(COUNT_OF_BOMB_ITEM) { items.add(BombItem()) }
    repeat(COUNT_OF_HELP_ITEM) { items.add(HelpItem()) }
    repeat(COUNT_OF_LARGE) { items.add(StaticShip(size = 4)) }
    repeat(COUNT_OF_MEDIUM) { items.add(StaticShip(size = 2)) }
    repeat(COUNT_OF_SMALL) { items.add(StaticShip(size = 1)) }

    val battleField by BattleFieldDelegate(items)

    battleField.printToConsole()
    // battleField.printToWindow()

    // TODO Состояние поля сохраняется и восстанавливается при повторном запуске
}
