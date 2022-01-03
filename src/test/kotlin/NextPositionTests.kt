import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import models.Direction
import models.Point

object NextPositionTests : FreeSpec({
    "left" - {
        val next = nextPosition(Point(1, 1, 1), Direction.LEFT)

        "assert" {
            next shouldBe Point(0, 1, 1)
        }
    }

    "right" - {
        val next = nextPosition(Point(1, 1, 1), Direction.RIGHT)

        "assert" {
            next shouldBe Point(2, 1, 1)
        }
    }

    "back" - {
        val next = nextPosition(Point(2, 2, 2), Direction.BACK)

        "assert" {
            next shouldBe Point(2, 1, 2)
        }
    }

    "forward" - {
        val next = nextPosition(Point(2, 2, 2), Direction.FORWARD)

        "assert" {
            next shouldBe Point(2, 3, 2)
        }
    }

    "up" - {
        val next = nextPosition(Point(3, 3, 3), Direction.UP)

        "assert" {
            next shouldBe Point(3, 3, 2)
        }
    }

    "down" - {
        val next = nextPosition(Point(3, 3, 3), Direction.DOWN)

        "assert" {
            next shouldBe Point(3, 3, 4)
        }
    }
})
