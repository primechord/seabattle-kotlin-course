import io.kotest.core.spec.style.FreeSpec
import models.BattleField
import models.BombItem
import models.SpawnerDelegate.Companion.spawnInRandomPlace
import org.assertj.core.api.Assertions.assertThat

object SpawnItemTests : FreeSpec({
    "check spawn" - {
        val battleField = BattleField()

        battleField.spawnInRandomPlace(BombItem())

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