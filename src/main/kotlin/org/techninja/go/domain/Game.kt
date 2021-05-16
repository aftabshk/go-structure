package org.techninja.go.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "game")
data class Game(
    @Id
    val gameId: String,
    val players: List<Player>,
    var currentPlayer: Color,
    val board: Board
) {

    companion object {
        fun create(gameSize: GameSize): Game {
            return Game(
                gameId = UUID.randomUUID().toString(),
                players = listOf(Player(stoneColor = Color.BLACK), Player(stoneColor = Color.WHITE)),
                board = Board.create(gameSize),
                currentPlayer = Color.BLACK
            )
        }
    }

    fun play(color: Color, point: Point) {
        if (this.currentPlayer == color) {
            val stone = Stone(color, point)
            board.playStone(point, stone)
            val currentPlayer = players.find { it.stoneColor == color }!!
            currentPlayer.moves.add(stone)
            captureStones(point, color, currentPlayer)
            this.currentPlayer = this.currentPlayer.opponentColor()
        }
    }

    private fun captureStones(point: Point, color: Color, currentPlayer: Player) {
        val opponentStones = getOpponentStones(point, color)
        opponentStones.forEach { opponentStone ->
            board.getStonesConnectedTo(opponentStone).let {
                if (board.areCaptured(stones = it)) {
                    board.capture(it)
                    currentPlayer.increaseCapturedStoneBy(it.size)
                }
            }
        }
    }

    private fun getOpponentStones(point: Point, color: Color): List<Stone> {
        val neighbours = point.neighbours(board.lowerBound, board.upperBound)
        return neighbours.filter { isOpponentStone(it, color) }.map { board.getState()[it]!! }
    }

    private fun isOpponentStone(it: Point, color: Color) = board.getState()[it]?.color == color.opponentColor()
}