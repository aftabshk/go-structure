package org.techninja.go.controller

import io.kotest.matchers.equality.shouldBeEqualToIgnoringFields
import io.kotest.matchers.shouldBe
import io.mockk.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.techninja.go.builders.BoardBuilder
import org.techninja.go.builders.GameBuilder
import org.techninja.go.domain.Color.BLACK
import org.techninja.go.domain.Color.WHITE
import org.techninja.go.domain.Game
import org.techninja.go.domain.GameSize.NINETEEN_BY_NINETEEN
import org.techninja.go.domain.GameSize.NINE_BY_NINE
import org.techninja.go.domain.Player
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

        val stone = Stone(BLACK, Point(1, 1))
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

        val stone = Stone(BLACK, Point(1, 1))
        val actualGame = gameService.playMove("1", stone)

        assertNextWith(actualGame) {
            it shouldBe game
        }
    }

    @Test
    fun `should use game repository to create the game`() {
        val game = GameBuilder(
            players = listOf(
                Player(stoneColor = BLACK),
                Player(stoneColor = WHITE)
            ),
            board = BoardBuilder(
                upperBound = Point(9, 9),
                lowerBound = Point(1, 1)
            ).build()
        ).build()
        every {
            gameRepository.save(any())
        } returns Mono.just(game)

        val actualGame = gameService.create(NINE_BY_NINE)
        val gameSlot = slot<Game>()

        assertNextWith(actualGame) {
            verify(exactly = 1) {
                gameRepository.save(capture(gameSlot))
            }
            gameSlot.captured.shouldBeEqualToIgnoringFields(game, Game::id)
        }
    }

    @Test
    fun `should return the created game`() {
        val game = GameBuilder(
            players = listOf(
                Player(stoneColor = BLACK),
                Player(stoneColor = WHITE)
            ),
            board = BoardBuilder(
                upperBound = Point(19, 19),
                lowerBound = Point(1, 1)
            ).build()
        ).build()
        every {
            gameRepository.save(any())
        } returns Mono.just(game)

        val actualGame = gameService.create(NINETEEN_BY_NINETEEN)

        assertNextWith(actualGame) {
            it.shouldBeEqualToIgnoringFields(game, Game::id)
        }
    }
}