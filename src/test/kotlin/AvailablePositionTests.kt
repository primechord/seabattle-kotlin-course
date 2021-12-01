import io.kotest.core.spec.style.FreeSpec
import models.BattleField
import models.CubeState
import models.Point
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException

object AvailablePositionTests : FreeSpec({
    "check busyness" - {
        val battleField = BattleField()

        battleField[1, 1, 1] = CubeState.BOMB
        val available = battleField.isAvailableCube(Point(1, 1, 1))
        "should return isFalse" {
            assertThat(available).isFalse
        }
    }

    "check availability" - {
        val battleField = BattleField()

        val available = battleField.isAvailableCube(Point(1, 1, 1))
        "should return isTrue" {
            assertThat(available).isTrue()
        }
    }

    "check boundaries" - {
        val battleField = BattleField(1)

        "should throw exception" {
            assertThatIllegalArgumentException().isThrownBy {
                battleField.isAvailableCube(Point(2, 2, 2))
            }
        }
    }

    "check neighbors" - {
        val battleField = BattleField()

        battleField[1, 1, 1] = CubeState.BOMB
        val available = battleField.isAvailableCube(Point(1, 1, 2))
        "should return isFalse" {
            assertThat(available).isFalse()
        }
    }
})