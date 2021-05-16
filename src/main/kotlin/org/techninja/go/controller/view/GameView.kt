package org.techninja.go.controller.view

import org.techninja.go.domain.Board
import org.techninja.go.domain.Color
import org.techninja.go.domain.Game
import org.techninja.go.domain.Player

data class GameView(
    val id: String,
    val players: List<Player>,
    val board: Board,
    val currentPlayer: Color
) {
    companion object {
        fun from(game: Game): GameView {
            return GameView(game.gameId, game.players, game.board, currentPlayer = game.currentPlayer)
        }
    }
}
