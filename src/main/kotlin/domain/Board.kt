package domain

data class Board(
    val upperBound: Point,
    val lowerBound: Point,
    private val state: MutableMap<Point, Stone>
) {

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

    fun getBoardState(): Map<Point, Stone> {
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