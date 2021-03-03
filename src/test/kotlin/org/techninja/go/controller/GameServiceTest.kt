package org.techninja.go.controller

import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.techninja.go.domain.Color
import org.techninja.go.domain.Game
import org.techninja.go.domain.Point
import org.techninja.go.domain.Stone
import org.techninja.go.utils.assertNextWith
import reactor.core.publisher.Mono

class GameServiceTest {

    private val gameRepository = mockk<GameRepository>(relaxed = true)
    private val gameService = GameService(gameRepository)

    @BeforeEach
    fun setUp() { clearAllMocks() }

    @AfterEach
    fun tearDown() { clearAllMocks() }

    @Test
    fun `should use game repository to play move`() {
        val game = mockk<Game>(relaxed = true)
        every {
            gameRepository.findById(any<String>())
        } returns Mono.just(game)
        every {
            gameRepository.save(any())
        } returns Mono.just(game)

        val stone = Stone(Color.BLACK, Point(1, 1))
        val actualGame = gameService.playMove("1", stone)

        assertNextWith(actualGame) {
            verify(exactly = 1) {
                gameRepository.findById("1")
                game.play(stone.color, stone.point)
                gameRepository.save(game)
            }
        }
    }

    @Test
    fun `should return the game`() {
        val game = mockk<Game>(relaxed = true)
        every {
            gameRepository.findById(any<String>())
        } returns Mono.just(game)
        every {
            gameRepository.save(any())
        } returns Mono.just(game)

        val stone = Stone(Color.BLACK, Point(1, 1))
        val actualGame = gameService.playMove("1", stone)

        assertNextWith(actualGame) {
            it shouldBe game
        }
    }
}