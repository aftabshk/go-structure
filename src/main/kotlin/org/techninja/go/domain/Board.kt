package org.techninja.go.domain

data class Board(
    val upperBound: Point,
    val lowerBound: Point,
    private val state: MutableMap<Point, Stone> = mutableMapOf()
) {

    companion object {

        fun create(boardSize: GameSize): Board {
            return when (boardSize) {
                GameSize.NINE_BY_NINE -> Board(upperBound = Point(9,9), lowerBound = Point(1,1))
                GameSize.THIRTEEN_BY_THIRTEEN -> Board(upperBound = Point(13,13), lowerBound = Point(1,1))
                GameSize.NINETEEN_BY_NINETEEN -> Board(upperBound = Point(19,19), lowerBound = Point(1,1))
            }
        }
    }

    fun areCaptured(stones: Set<Stone>) = stones.all { isSurrounded(it) }

    fun getStonesConnectedTo(stone: Stone, connectedStones: Set<Stone> = emptySet()): Set<Stone> {
        val neighbours = stone.point.neighbours(lowerBound, upperBound)
        val stones = getStonesIfConnectedFrom(neighbours, stone).minus(connectedStones)
        if (stones.isEmpty()) {
            return connectedStones + stone
        }
        return stones.fold(emptySet()) { acc, s ->
            getStonesConnectedTo(s, acc + connectedStones + stone)
        }
    }

    fun capture(stonesToBeCaptured: Set<Stone>) {
        stonesToBeCaptured.forEach { state.remove(it.point) }
    }

    fun playStone(point: Point, stone: Stone) {
        this.state[point] = stone
    }

    fun getState(): Map<Point, Stone> {
        return this.state.toMap()
    }

    private fun isSurrounded(stone: Stone): Boolean {
        return stone.point.neighbours(lowerBound, upperBound).all {
            state[it]?.color != null
        }
    }

    private fun getStonesIfConnectedFrom(
        neighbours: List<Point>,
        stone: Stone
    ): Set<Stone>{
        return neighbours.filter { state[it]?.color == stone.color }.map { state[it]!! }.toSet()
    }
}