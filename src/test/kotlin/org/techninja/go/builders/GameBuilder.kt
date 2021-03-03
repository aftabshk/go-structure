package org.techninja.go.builders

import org.techninja.go.domain.*

data class GameBuilder(
    val id: String = "",
    val players: List<Player> = listOf(),
    val board: Board = BoardBuilder().build()
) {
    fun build(): Game {
        return Game(
            id = id,
            players = players,
            board = board
        )
    }
}

data class BoardBuilder(
    val upperBound: Point = PointBuilder().build(),
    val lowerBound: Point = PointBuilder().build(),
    val state: MutableMap<Point, Stone> = mutableMapOf()
) {
    fun build(): Board {
        return Board(
            upperBound = upperBound,
            lowerBound = lowerBound,
            state = state
        )
    }
}

data class PointBuilder(
    val x: Int = 0,
    val y: Int = 0
) {
    fun build(): Point {
        return Point(
            x = x,
            y = y
        )
    }
}
