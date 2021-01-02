package domain

import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class PointTest {

    @Test
    fun `should return true when point is within the bound`() {
        Point(1, 1).isWithinBound(
            lowerBound = Point(1, 1),
            upperBound = Point(8, 8)
        ) shouldBe true
    }

    @Test
    fun `should return false when point is outside the bound`() {
        Point(0, 0).isWithinBound(
            lowerBound = Point(1, 1),
            upperBound = Point(8, 8)
        ) shouldBe false
    }

    @Test
    fun `should return neighbours`() {
        Point(2, 2).neighbours(
            lowerBound = Point(1, 1),
            upperBound = Point(8, 8)
        ) shouldContainExactlyInAnyOrder listOf(
            Point(1, 2),
            Point(3, 2),
            Point(2, 1),
            Point(2, 3)
        )
    }

    @Test
    fun `should return neighbours within the bound`() {
        Point(1, 1).neighbours(
            lowerBound = Point(1, 1),
            upperBound = Point(8, 8)
        ) shouldContainExactlyInAnyOrder listOf(
            Point(1, 2),
            Point(2, 1)
        )
    }
}