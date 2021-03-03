package org.techninja.go.controller.view

import org.techninja.go.domain.Board
import org.techninja.go.domain.Game
import org.techninja.go.domain.Player

data class GameView(
    val id: String,
    val players: List<Player>,
    val board: Board
) {
    companion object {
        fun from(game: Game): GameView {
            return GameView(game.id, game.players, game.board)
        }
    }
}
