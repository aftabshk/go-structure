package domain

import io.kotest.assertions.assertSoftly
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class GameTest {
    private val blackPlayer = Player(Color.BLACK, mutableListOf())
    private val whitePlayer = Player(Color.WHITE, mutableListOf())
    private val nineByNineBoard = Board(Point(1, 1), Point(9, 9), mutableMapOf())

    @Test
    fun `should play a move on the board for a player`() {
        val game = Game(listOf(blackPlayer, whitePlayer), nineByNineBoard)

        game.play(Color.BLACK, Point(3, 3))

        assertSoftly {
            game.board.state shouldBe mutableMapOf(Point(3, 3) to Stone(Color.BLACK, Point(3,3)))
            game.players.find { it.stoneColor == Color.BLACK }!!.moves shouldContain Stone(Color.BLACK, Point(3, 3))
        }
    }

    @Test
    fun `should play moves for different players`() {
        val game = Game(listOf(blackPlayer, whitePlayer), nineByNineBoard)

        game.play(Color.BLACK, Point(3, 3))
        game.play(Color.BLACK, Point(4, 3))
        game.play(Color.WHITE, Point(2, 3))
        game.play(Color.WHITE, Point(1, 3))

        assertSoftly {
            game.board.state shouldBe mutableMapOf(
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
}