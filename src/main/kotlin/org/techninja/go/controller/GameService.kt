package org.techninja.go.controller

import org.springframework.stereotype.Component
import org.techninja.go.domain.Game
import org.techninja.go.domain.Stone
import reactor.core.publisher.Mono

@Component
class GameService(val gameRepository: GameRepository) {
    fun playMove(gameId: String, stone: Stone): Mono<Game> {
        return gameRepository.findById(gameId).map {
            it.play(stone.color, stone.point)
            it
        }.flatMap {
            gameRepository.save(it)
        }
    }
}