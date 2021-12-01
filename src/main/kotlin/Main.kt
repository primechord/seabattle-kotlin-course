import models.BattleFieldDelegate
import models.Item

fun main() {
    val items = mutableListOf<Item>()
    val battleField by BattleFieldDelegate(items)

    battleField.printToConsole()
    // battleField.printToWindow()
}
