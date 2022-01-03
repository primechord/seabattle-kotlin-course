import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import models.CubeState
import models.Point

object AvailablePositionTests : FreeSpec({
    "busyness" - {
        val battleField = BattleField()

        battleField[1, 1, 1] = CubeState.BOMB
        val available = battleField.isAvailableCube(Point(1, 1, 1))

        "should return False" {
            available shouldBe false
        }
    }

    "availability" - {
        val battleField = BattleField()

        val available = battleField.isAvailableCube(Point(1, 1, 1))

        "should return True" {
            available shouldBe true
        }
    }

    "boundaries" - {
        val battleField = BattleField(1)

        "should return False" {
            battleField.isAvailableCube(Point(2, 2, 2)) shouldBe false
        }
    }

    "neighbors" - {
        val battleField = BattleField()

        battleField[1, 1, 1] = CubeState.BOMB
        val available = battleField.isAvailableCube(Point(1, 1, 2))

        "should return False" {
            available shouldBe false
        }
    }
})
