import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import models.BombItem
import models.Item

object SpawnItemTests : FreeSpec({
    "spawn" - {
        val items = mutableListOf<Item>()
        items.add(BombItem())
        val battleField by Delegate(items)

        "item is created" {
            battleField.getFieldState().size shouldBe 1
        }
    }

    "no spawn" - {
        val battleField = BattleField()

        "item is not created" {
            battleField.getFieldState().size shouldBe 0
        }
    }
})
