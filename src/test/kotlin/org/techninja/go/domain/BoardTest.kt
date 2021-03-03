package org.techninja.go.domain

import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.maps.shouldContainExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.techninja.go.domain.Color.BLACK
import org.techninja.go.domain.Color.WHITE

class BoardTest {

    @Test
    fun `should return two stones connected in a straight line`() {
        val board = Board(
            upperBound = Point(8, 8),
            lowerBound = Point(1, 1),
            state = mutableMapOf(
                Point(2, 2) to Stone(WHITE, Point(2, 2)),
                Point(3, 2) to Stone(WHITE, Point(3, 2)),
            )
        )

        val connectedStones = board.getStonesConnectedTo(Stone(WHITE, Point(2, 2)))

        connectedStones shouldContainExactlyInAnyOrder setOf(Stone(WHITE, Point(2, 2)), Stone(WHITE, Point(3, 2)))
    }

    @Test
    fun `should return four stones creating a square`() {
        val board = Board(
            upperBound = Point(8, 8),
            lowerBound = Point(1, 1),
            state = mutableMapOf(
                Point(2, 2) to Stone(WHITE, Point(2, 2)),
                Point(3, 2) to Stone(WHITE, Point(3, 2)),
                Point(2, 3) to Stone(WHITE, Point(2, 3)),
                Point(3, 3) to Stone(WHITE, Point(3, 3)),
            )
        )

        val connectedStones = board.getStonesConnectedTo(Stone(WHITE, Point(2, 2)))

        val expected = setOf(
            Stone(WHITE, Point(3, 2)),
            Stone(WHITE, Point(2, 3)),
            Stone(WHITE, Point(3, 3)),
            Stone(WHITE, Point(2, 2))
        )
        connectedStones shouldContainExactlyInAnyOrder expected
    }

    @Test
    fun `should return stones creating a pyramid`() {
        val board = Board(
            upperBound = Point(8, 8),
            lowerBound = Point(1, 1),
            state = mutableMapOf(
                Point(1, 1) to Stone(WHITE, Point(1, 1)),
                Point(2, 1) to Stone(WHITE, Point(2, 1)),
                Point(3, 1) to Stone(WHITE, Point(3, 1)),
                Point(4, 1) to Stone(WHITE, Point(4, 1)),
                Point(5, 1) to Stone(WHITE, Point(5, 1)),
                Point(2, 2) to Stone(WHITE, Point(2, 2)),
                Point(3, 2) to Stone(WHITE, Point(3, 2)),
                Point(4, 2) to Stone(WHITE, Point(4, 2)),
                Point(3, 3) to Stone(WHITE, Point(3, 3)),
                Point(8, 8) to Stone(WHITE, Point(8, 8))
            )
        )

        val connectedStones = board.getStonesConnectedTo(Stone(WHITE, Point(2, 2)))

        val expected = setOf(
            Stone(WHITE, Point(1, 1)),
            Stone(WHITE, Point(2, 1)),
            Stone(WHITE, Point(3, 1)),
            Stone(WHITE, Point(4, 1)),
            Stone(WHITE, Point(5, 1)),
            Stone(WHITE, Point(2, 2)),
            Stone(WHITE, Point(3, 2)),
            Stone(WHITE, Point(4, 2)),
            Stone(WHITE, Point(3, 3))
        )
        connectedStones shouldContainExactlyInAnyOrder expected
    }

    @Test
    fun `should return stones creating the edge of a square`() {
        val board = Board(
            upperBound = Point(8, 8),
            lowerBound = Point(1, 1),
            state = mutableMapOf(
                Point(6, 4) to Stone(WHITE, Point(6, 4)),
                Point(6, 3) to Stone(WHITE, Point(6, 3)),
                Point(6, 2) to Stone(WHITE, Point(6, 2)),
                Point(7, 2) to Stone(WHITE, Point(7, 2)),
                Point(8, 2) to Stone(WHITE, Point(8, 2)),
                Point(8, 3) to Stone(WHITE, Point(8, 3)),
                Point(8, 4) to Stone(WHITE, Point(8, 4)),
                Point(7, 4) to Stone(WHITE, Point(7, 4))
            )
        )

        val connectedStones = board.getStonesConnectedTo(Stone(WHITE, Point(6, 4)))

        val expected = setOf(
            Stone(WHITE, Point(6, 4)),
            Stone(WHITE, Point(6, 3)),
            Stone(WHITE, Point(6, 2)),
            Stone(WHITE, Point(7, 2)),
            Stone(WHITE, Point(8, 2)),
            Stone(WHITE, Point(8, 3)),
            Stone(WHITE, Point(8, 4)),
            Stone(WHITE, Point(7, 4))
        )
        connectedStones shouldContainExactlyInAnyOrder expected
    }

    @Test
    fun `should return true if two stones are captured`() {
        val board = Board(
            upperBound = Point(8, 8),
            lowerBound = Point(1, 1),
            state = mutableMapOf(
                Point(1, 1) to Stone(WHITE, Point(1, 1)),
                Point(2, 1) to Stone(WHITE, Point(2, 1)),
                Point(3, 1) to Stone(WHITE, Point(3, 1)),
                Point(4, 2) to Stone(WHITE, Point(4, 2)),
                Point(3, 3) to Stone(WHITE, Point(3, 3)),
                Point(2, 3) to Stone(WHITE, Point(2, 3)),
                Point(1, 3) to Stone(WHITE, Point(1, 3)),
                Point(1, 2) to Stone(WHITE, Point(1, 2)),
                Point(2, 2) to Stone(BLACK, Point(2, 2)),
                Point(3, 2) to Stone(BLACK, Point(3, 2)),
            )
        )

        val stones = setOf(Stone(BLACK, Point(2, 2)), Stone(BLACK, Point(3, 2)))

        board.areCaptured(stones) shouldBe true
    }

    @Test
    fun `should return true if 4 stones are captured`() {
        val board = Board(
            upperBound = Point(8, 8),
            lowerBound = Point(1, 1),
            state = mutableMapOf(
                Point(3, 5) to Stone(WHITE, Point(3, 5)),
                Point(3, 6) to Stone(WHITE, Point(3, 6)),
                Point(4, 7) to Stone(WHITE, Point(4, 7)),
                Point(5, 7) to Stone(WHITE, Point(5, 7)),
                Point(6, 6) to Stone(WHITE, Point(6, 6)),
                Point(6, 5) to Stone(WHITE, Point(6, 5)),
                Point(4, 4) to Stone(WHITE, Point(4, 4)),
                Point(5, 4) to Stone(WHITE, Point(5, 4)),
                Point(4, 5) to Stone(BLACK, Point(4, 5)),
                Point(5, 5) to Stone(BLACK, Point(5, 5)),
                Point(4, 6) to Stone(BLACK, Point(4, 6)),
                Point(5, 6) to Stone(BLACK, Point(5, 6)),
            )
        )

        val stones = setOf(
            Stone(BLACK, Point(4, 5)),
            Stone(BLACK, Point(5, 5)),
            Stone(BLACK, Point(4, 6)),
            Stone(BLACK, Point(5, 6)),
        )

        board.areCaptured(stones) shouldBe true
    }

    @Test
    fun `should return true when 4 stones are captured`() {
        val board = Board(
            upperBound = Point(8, 8),
            lowerBound = Point(1, 1),
            state = mutableMapOf(
                Point(4, 4) to Stone(WHITE, Point(4, 4)),
                Point(3, 5) to Stone(WHITE, Point(3, 5)),
                Point(5, 5) to Stone(WHITE, Point(5, 5)),
                Point(2, 6) to Stone(WHITE, Point(2, 6)),
                Point(6, 6) to Stone(WHITE, Point(6, 6)),
                Point(3, 7) to Stone(WHITE, Point(3, 7)),
                Point(5, 7) to Stone(WHITE, Point(5, 7)),
                Point(4, 8) to Stone(WHITE, Point(4, 8)),
                Point(4, 5) to Stone(BLACK, Point(4, 5)),
                Point(3, 6) to Stone(BLACK, Point(3, 6)),
                Point(5, 6) to Stone(BLACK, Point(5, 6)),
                Point(4, 7) to Stone(BLACK, Point(4, 7)),
                Point(4, 6) to Stone(WHITE, Point(4, 6)),
            )
        )

        val stones = setOf(
            Stone(BLACK, Point(4, 5)),
            Stone(BLACK, Point(3, 6)),
            Stone(BLACK, Point(5, 6)),
            Stone(BLACK, Point(4, 7)),
        )

        board.areCaptured(stones) shouldBe true
    }

    @Test
    fun `should return false when there is one empty in between the four stones`() {
        val board = Board(
            upperBound = Point(8, 8),
            lowerBound = Point(1, 1),
            state = mutableMapOf(
                Point(4, 4) to Stone(WHITE, Point(4, 4)),
                Point(3, 5) to Stone(WHITE, Point(3, 5)),
                Point(5, 5) to Stone(WHITE, Point(5, 5)),
                Point(2, 6) to Stone(WHITE, Point(2, 6)),
                Point(6, 6) to Stone(WHITE, Point(6, 6)),
                Point(3, 7) to Stone(WHITE, Point(3, 7)),
                Point(5, 7) to Stone(WHITE, Point(5, 7)),
                Point(4, 8) to Stone(WHITE, Point(4, 8)),
                Point(4, 5) to Stone(BLACK, Point(4, 5)),
                Point(3, 6) to Stone(BLACK, Point(3, 6)),
                Point(5, 6) to Stone(BLACK, Point(5, 6)),
                Point(4, 7) to Stone(BLACK, Point(4, 7)),
            )
        )

        val stones = setOf(
            Stone(BLACK, Point(4, 5)),
            Stone(BLACK, Point(3, 6)),
            Stone(BLACK, Point(5, 6)),
            Stone(BLACK, Point(4, 7)),
        )

        board.areCaptured(stones) shouldBe false
    }

    @Test
    fun `should return true when a corner stone is captured`() {
        val board = Board(
            upperBound = Point(8, 8),
            lowerBound = Point(1, 1),
            state = mutableMapOf(
                Point(1, 2) to Stone(WHITE, Point(1, 2)),
                Point(2, 1) to Stone(WHITE, Point(2, 1)),
                Point(1, 1) to Stone(BLACK, Point(1, 1))
            )
        )

        val stones = setOf(Stone(BLACK, Point(1, 1)))

        board.areCaptured(stones) shouldBe true
    }

    @Test
    fun `should return true when two stones at edge are captured`() {
        val board = Board(
            upperBound = Point(8, 8),
            lowerBound = Point(1, 1),
            state = mutableMapOf(
                Point(3, 1) to Stone(WHITE, Point(3, 1)),
                Point(2, 1) to Stone(WHITE, Point(2, 1)),
                Point(1, 1) to Stone(BLACK, Point(1, 1)),
                Point(2, 2) to Stone(BLACK, Point(2, 2)),
                Point(3, 2) to Stone(BLACK, Point(3, 2)),
                Point(4, 1) to Stone(BLACK, Point(4, 1)),
            )
        )

        val stones = setOf(Stone(WHITE, Point(3, 1)), Stone(WHITE, Point(2, 1)))

        board.areCaptured(stones) shouldBe true
    }

    @Test
    fun `should return false when black has one liberty`() {
        val board = Board(
            upperBound = Point(9, 9),
            lowerBound = Point(1, 1),
            state = mutableMapOf(
                Point(2, 5) to Stone(WHITE, Point(2, 5)),
                Point(3, 5) to Stone(WHITE, Point(3, 5)),
                Point(4, 5) to Stone(WHITE, Point(4, 5)),
                Point(5, 5) to Stone(WHITE, Point(5, 5)),
                Point(2, 4) to Stone(WHITE, Point(2, 4)),
                Point(3, 4) to Stone(BLACK, Point(3, 4)),
                Point(4, 4) to Stone(BLACK, Point(4, 4)),
                Point(5, 4) to Stone(WHITE, Point(5, 4)),
                Point(2, 3) to Stone(WHITE, Point(2, 3)),
                Point(3, 3) to Stone(WHITE, Point(3, 3)),
                Point(5, 3) to Stone(WHITE, Point(5, 3)),
                Point(2, 2) to Stone(WHITE, Point(2, 2)),
                Point(3, 2) to Stone(WHITE, Point(3, 2)),
                Point(4, 2) to Stone(WHITE, Point(4, 2)),
                Point(5, 2) to Stone(WHITE, Point(5, 2)),
            )
        )

        val stones = setOf(
            Stone(BLACK, Point(3, 4)),
            Stone(BLACK, Point(4, 4))
        )

        board.areCaptured(stones) shouldBe false
    }

    @Test
    fun `should capture stones`() {
        val board = Board(
            upperBound = Point(8, 8),
            lowerBound = Point(1, 1),
            state = mutableMapOf(
                Point(3, 1) to Stone(WHITE, Point(3, 1)),
                Point(2, 1) to Stone(WHITE, Point(2, 1)),
                Point(1, 1) to Stone(BLACK, Point(1, 1)),
                Point(2, 2) to Stone(BLACK, Point(2, 2)),
                Point(3, 2) to Stone(BLACK, Point(3, 2)),
                Point(4, 1) to Stone(BLACK, Point(4, 1)),
            )
        )

        val stonesToBeCaptured = setOf(
            Stone(BLACK, Point(1, 1)),
            Stone(BLACK, Point(2, 2)),
            Stone(BLACK, Point(3, 2)),
            Stone(BLACK, Point(4, 1)),
        )
        board.capture(stonesToBeCaptured)

        board.getState() shouldContainExactly mapOf(
            Point(3, 1) to Stone(WHITE, Point(3, 1)),
            Point(2, 1) to Stone(WHITE, Point(2, 1)),
        )
    }
}