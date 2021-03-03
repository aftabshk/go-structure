package org.techninja.go.controller

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import org.techninja.go.domain.Game

@Repository
interface GameRepository: ReactiveCrudRepository<Game, String>
