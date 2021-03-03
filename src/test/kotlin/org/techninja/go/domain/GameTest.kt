package org.techninja.go.domain

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.maps.shouldContainExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class GameTest {
    private val blackPlayer = Player(Color.BLACK, mutableSetOf())
    private val whitePlayer = Player(Color.WHITE, mutableSetOf())
    private val nineByNineBoard = Board(
        lowerBound = Point(1, 1),
        upperBound = Point(9, 9),
        state = mutableMapOf()
    )
    private val board = nineByNineBoard.copy(
        state = mutableMapOf(
            Point(1, 1) to Stone(Color.WHITE, Point(1, 1)),
            Point(2, 1) to Stone(Color.WHITE, Point(2, 1)),
            Point(3, 1) to Stone(Color.WHITE, Point(3, 1)),
            Point(4, 2) to Stone(Color.WHITE, Point(4, 2)),
            Point(3, 3) to Stone(Color.WHITE, Point(3, 3)),
            Point(2, 3) to Stone(Color.WHITE, Point(2, 3)),
            Point(1, 3) to Stone(Color.WHITE, Point(1, 3)),
            Point(2, 2) to Stone(Color.BLACK, Point(2, 2)),
            Point(3, 2) to Stone(Color.BLACK, Point(3, 2)),
        )
    )
    private val boardWithSquareTerritory = nineByNineBoard.copy(
        state = mutableMapOf(
            Point(2, 5) to Stone(Color.WHITE, Point(2, 5)),
            Point(3, 5) to Stone(Color.WHITE, Point(3, 5)),
            Point(4, 5) to Stone(Color.WHITE, Point(4, 5)),
            Point(5, 5) to Stone(Color.WHITE, Point(5, 5)),
            Point(2, 4) to Stone(Color.WHITE, Point(2, 4)),
            Point(3, 4) to Stone(Color.BLACK, Point(3, 4)),
            Point(4, 4) to Stone(Color.BLACK, Point(4, 4)),
            Point(5, 4) to Stone(Color.WHITE, Point(5, 4)),
            Point(2, 3) to Stone(Color.WHITE, Point(2, 3)),
            Point(5, 3) to Stone(Color.WHITE, Point(5, 3)),
            Point(2, 2) to Stone(Color.WHITE, Point(2, 2)),
            Point(3, 2) to Stone(Color.WHITE, Point(3, 2)),
            Point(4, 2) to Stone(Color.WHITE, Point(4, 2)),
            Point(5, 2) to Stone(Color.WHITE, Point(5, 2)),
        )
    )

    @Test
    fun `should play a move on the board for a player`() {
        val game = Game(id= "1", listOf(blackPlayer, whitePlayer), nineByNineBoard)

        game.play(Color.BLACK, Point(3, 3))

        assertSoftly {
            game.board.getState() shouldBe mutableMapOf(Point(3, 3) to Stone(Color.BLACK, Point(3, 3)))
            game.players.find { it.stoneColor == Color.BLACK }!!.moves shouldContain Stone(Color.BLACK, Point(3, 3))
        }
    }

    @Test
    fun `should play moves for different players`() {
        val game = Game(id = "1", listOf(blackPlayer, whitePlayer), nineByNineBoard)

        game.play(Color.BLACK, Point(3, 3))
        game.play(Color.BLACK, Point(4, 3))
        game.play(Color.WHITE, Point(2, 3))
        game.play(Color.WHITE, Point(1, 3))

        assertSoftly {
            game.board.getState() shouldBe mutableMapOf(
                Point(3, 3) to Stone(Color.BLACK, Point(3, 3)),
                Point(4, 3) to Stone(Color.BLACK, Point(4, 3)),
                Point(2, 3) to Stone(Color.WHITE, Point(2, 3)),
                Point(1, 3) to Stone(Color.WHITE, Point(1, 3))
            )
            game.players.find { it.stoneColor == Color.BLACK }!!.moves shouldContainExactly listOf(
                Stone(Color.BLACK, Point(3, 3)),
                Stone(Color.BLACK, Point(4, 3))
            )
            game.players.find { it.stoneColor == Color.WHITE }!!.moves shouldContainExactly listOf(
                Stone(Color.WHITE, Point(2, 3)),
                Stone(Color.WHITE, Point(1, 3))
            )
        }
    }

    @Test
    fun `should capture stones`() {
        val game = Game(id = "1", listOf(blackPlayer, whitePlayer), board)

        game.play(Color.WHITE, Point(1, 2))

        assertSoftly {
            game.board.getState() shouldContainExactly mutableMapOf(
                Point(1, 1) to Stone(Color.WHITE, Point(1, 1)),
                Point(2, 1) to Stone(Color.WHITE, Point(2, 1)),
                Point(3, 1) to Stone(Color.WHITE, Point(3, 1)),
                Point(4, 2) to Stone(Color.WHITE, Point(4, 2)),
                Point(3, 3) to Stone(Color.WHITE, Point(3, 3)),
                Point(2, 3) to Stone(Color.WHITE, Point(2, 3)),
                Point(1, 3) to Stone(Color.WHITE, Point(1, 3)),
                Point(1, 2) to Stone(Color.WHITE, Point(1, 2))
            )
            val whitePlayer = game.players.find { it.stoneColor == Color.WHITE }!!
            whitePlayer.moves shouldContain Stone(Color.WHITE, Point(1, 2))
            whitePlayer.getCaptured() shouldBe 2
        }
    }

    @Test
    fun `should not capture stones if opponent stones has a liberty`() {
        val game = Game(id = "1", listOf(blackPlayer, whitePlayer), boardWithSquareTerritory)

        game.play(Color.WHITE, Point(3, 3))

        assertSoftly {
            game.board.getState() shouldContainExactly mutableMapOf(
                Point(2, 5) to Stone(Color.WHITE, Point(2, 5)),
                Point(3, 5) to Stone(Color.WHITE, Point(3, 5)),
                Point(4, 5) to Stone(Color.WHITE, Point(4, 5)),
                Point(5, 5) to Stone(Color.WHITE, Point(5, 5)),
                Point(2, 4) to Stone(Color.WHITE, Point(2, 4)),
                Point(3, 4) to Stone(Color.BLACK, Point(3, 4)),
                Point(4, 4) to Stone(Color.BLACK, Point(4, 4)),
                Point(5, 4) to Stone(Color.WHITE, Point(5, 4)),
                Point(2, 3) to Stone(Color.WHITE, Point(2, 3)),
                Point(3, 3) to Stone(Color.WHITE, Point(3, 3)),
                Point(5, 3) to Stone(Color.WHITE, Point(5, 3)),
                Point(2, 2) to Stone(Color.WHITE, Point(2, 2)),
                Point(3, 2) to Stone(Color.WHITE, Point(3, 2)),
                Point(4, 2) to Stone(Color.WHITE, Point(4, 2)),
                Point(5, 2) to Stone(Color.WHITE, Point(5, 2)),
            )
            val whitePlayer = game.players.find { it.stoneColor == Color.WHITE }!!
            whitePlayer.moves shouldContain Stone(Color.WHITE, Point(3, 3))
            whitePlayer.getCaptured() shouldBe 0
        }
    }
}