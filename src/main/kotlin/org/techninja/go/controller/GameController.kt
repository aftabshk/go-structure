package org.techninja.go.controller

import org.springframework.web.bind.annotation.*
import org.techninja.go.controller.view.GameView
import org.techninja.go.controller.view.Move
import org.techninja.go.domain.Stone
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/games")
class GameController(val gameService: GameService) {

    @PostMapping("/{id}/play")
    fun play(@PathVariable id: String, @RequestBody move: Move): Mono<GameView> {
        return gameService.playMove(id, Stone(move.color, move.point)).map {
            GameView.from(it)
        }
    }
}