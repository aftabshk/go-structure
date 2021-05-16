package org.techninja.go.repository

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import org.techninja.go.domain.Game
import reactor.core.publisher.Mono

@Repository
interface GameRepository: ReactiveCrudRepository<Game, String> {
    fun findByGameId(gameId: String): Mono<Game>
}
