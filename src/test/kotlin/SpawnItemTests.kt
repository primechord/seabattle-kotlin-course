import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import models.BattleField
import models.BattleFieldDelegate
import models.BombItem
import models.Item

object SpawnItemTests : FreeSpec({
    /** Реализовать простой тест для количества заполненных ячеек */
    "check spawn" - {
        val items = mutableListOf<Item>()
        items.add(BombItem())
        val battleField by BattleFieldDelegate(items)

        "item is created" {
            battleField.getFieldState().size shouldBe 1
        }
    }

    "check no spawn" - {
        val battleField = BattleField()

        "item is not created" {
            battleField.getFieldState().size shouldBe 0
        }
    }
})