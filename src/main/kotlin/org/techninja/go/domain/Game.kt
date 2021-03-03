package org.techninja.go.domain

import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "game")
data class Game(
    @Indexed(unique = true)
    val id: String,
    val players: List<Player>,
    val board: Board
) {

    fun play(color: Color, point: Point) {
        val stone = Stone(color, point)
        board.playStone(point, stone)
        val currentPlayer = players.find { it.stoneColor == color }!!
        currentPlayer.moves.add(stone)
        captureStones(point, color, currentPlayer)
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