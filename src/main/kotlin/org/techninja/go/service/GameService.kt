package org.techninja.go.service

import org.springframework.stereotype.Component
import org.techninja.go.domain.Game
import org.techninja.go.domain.GameSize
import org.techninja.go.domain.Stone
import org.techninja.go.repository.GameRepository
import reactor.core.publisher.Mono

@Component
class GameService(val gameRepository: GameRepository) {
    fun playMove(gameId: String, stone: Stone): Mono<Game> {
        return gameRepository.findByGameId(gameId).map {
            it.play(stone.color, stone.point)
            it
        }.flatMap {
            gameRepository.save(it)
        }
    }

    fun create(gameSize: GameSize): Mono<Game> {
        val game = Game.create(gameSize)
        return gameRepository.save(game)
    }

    fun getById(gameId: String): Mono<Game> {
        return gameRepository.findByGameId(gameId)
    }
}