package domain

data class Board(
    val upperBound: Point,
    val lowerBound: Point,
    val state: MutableMap<Point, Stone> // Should be private
) {

    fun areCaptured(stones: Set<Stone>) = stones.all { isSurrounded(it) }

    fun getStonesConnectedTo(stone: Stone, connectedStones: Set<Stone> = emptySet()): Set<Stone> {
        val neighbours = stone.point.neighbours(lowerBound, upperBound)
        val stones = getStonesIfConnectedFrom(neighbours, stone, connectedStones)
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

    private fun isSurrounded(stone: Stone): Boolean {
        return stone.point.neighbours(lowerBound, upperBound).all {
            state[it]?.color != null
        }
    }

    private fun getStonesIfConnectedFrom(
        neighbours: List<Point>,
        stone: Stone,
        connectedStones: Set<Stone>
    ): List<Stone>{
        return neighbours.filter {
            state.containsKey(it) && state[it]!!.color == stone.color && !connectedStones.contains(stone.copy(point = it))
        }.map {
            state[it]!!
        }
    }
}