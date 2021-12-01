import io.kotest.core.spec.style.FreeSpec
import models.BattleField
import models.BattleFieldDelegate
import models.Item
import org.assertj.core.api.Assertions.assertThat

object SpawnItemTests : FreeSpec({
    "check spawn" - {
        val items = mutableListOf<Item>()
        val battleField by BattleFieldDelegate(items)

        // battleField.spawnInRandomPlace(BombItem()) FIXME починить

        "bomb is created" {
            assertThat(battleField.getFieldState().size).isEqualTo(1)
        }
    }

    "check no spawn" - {
        val battleField = BattleField()

        "bomb is created" {
            assertThat(battleField.getFieldState().size).isEqualTo(0)
        }
    }
})