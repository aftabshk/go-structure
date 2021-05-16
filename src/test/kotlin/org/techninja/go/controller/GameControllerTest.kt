package org.techninja.go.controller

import com.ninjasquad.springmockk.MockkBean
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.web.reactive.server.WebTestClient
import org.techninja.go.builders.GameBuilder
import org.techninja.go.controller.view.GameCreationRequest
import org.techninja.go.controller.view.GameView
import org.techninja.go.controller.view.Move
import org.techninja.go.domain.*
import org.techninja.go.domain.Color.BLACK
import org.techninja.go.domain.Color.WHITE
import org.techninja.go.domain.GameSize.NINE_BY_NINE
import org.techninja.go.service.GameService
import reactor.core.publisher.Mono

@WebFluxTest(GameController::class)
class GameControllerTest(
    @Autowired val webTestClient: WebTestClient

) {
    @MockkBean lateinit var gameService: GameService

    @Test
    fun `should call game service to play the move`() {
        val players = listOf(
            Player(BLACK, mutableSetOf(Stone(BLACK, Point(1, 1)))),
            Player(WHITE, mutableSetOf())
        )
        val board = Board(
            upperBound = Point(9, 9),
            lowerBound = Point(1, 1),
            state = mutableMapOf(Point(1, 1) to Stone(BLACK, Point(1, 1)))
        )
        val expectedGame = Game(
            gameId = "1",
            players = players,
            board = board
        )
        val move = Move(BLACK, Point(1, 1))
        every {
            gameService.playMove(any(), any())
        } returns Mono.just(expectedGame)

        webTestClient.post()
            .uri("/games/1/play")
            .bodyValue(move)
            .exchange()
            .expectStatus().isOk
            .expectBody(GameView::class.java)
            .returnResult()
            .responseBody

        verify {
            gameService.playMove("1", Stone(move.color, move.point))
        }
    }

    @Test
    fun `should play the move`() {
        val players = listOf(
            Player(BLACK, mutableSetOf(Stone(BLACK, Point(1, 1)))),
            Player(WHITE, mutableSetOf())
        )
        val board = Board(
            upperBound = Point(9, 9),
            lowerBound = Point(1, 1),
            state = mutableMapOf(Point(1, 1) to Stone(BLACK, Point(1, 1)))
        )
        val expectedGame = Game(
            gameId = "1",
            players = players,
            board = board
        )
        val move = Move(BLACK, Point(1, 1))
        every {
            gameService.playMove(any(), any())
        } returns Mono.just(expectedGame)

        val actualGame = webTestClient.post()
            .uri("/games/1/play")
            .bodyValue(move)
            .exchange()
            .expectStatus().isOk
            .expectBody(GameView::class.java)
            .returnResult()
            .responseBody

        actualGame shouldBe GameView.from(expectedGame)
    }

    @Test
    fun `should use game service to create a game`() {
        val game = GameBuilder().build()

        every {
            gameService.create(any())
        } returns Mono.just(game)

        val actualGame = webTestClient.post()
            .uri("/games/create")
            .bodyValue(GameCreationRequest(NINE_BY_NINE))
            .exchange()
            .expectStatus().isOk
            .expectBody(GameView::class.java)
            .returnResult()
            .responseBody

        verify(exactly = 1) {
            gameService.create(NINE_BY_NINE)
        }
        actualGame shouldBe GameView.from(game)
    }

    @Test
    fun `should use game service to retrieve the game from db`() {
        val game = GameBuilder().build()

        every {
            gameService.getById(any())
        } returns Mono.just(game)

        val actualGame = webTestClient.get()
            .uri("/games/1")
            .exchange()
            .expectStatus().isOk
            .expectBody(GameView::class.java)
            .returnResult()
            .responseBody

        verify(exactly = 1) {
            gameService.getById("1")
        }
        actualGame shouldBe GameView.from(game)
    }
}
