import models.FieldDsl.battleField
import models.SpawnerDelegate.Companion.createItems

fun main() {
    battleField(8) {
        val items = createItems(
            COUNT_OF_SMALL,
            COUNT_OF_MEDIUM,
            COUNT_OF_LARGE,
            COUNT_OF_BOMB_ITEM,
            COUNT_OF_HELP_ITEM
        )

        printToConsole()
        // printToWindow()
    }

    /* TODO между частями любых двух объектов должно быть не менее одной ячейки, в том числе по диагонали

        FIXME Иногда рядом объекты, иногда падает, иногда без координат, последний слой не используется */

    // TODO Создать функции-делегаты для заполнения игрового поля

    // TODO Расположение показывает положение головы корабля (в процессе)
}
