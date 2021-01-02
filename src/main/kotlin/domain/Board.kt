package domain

data class Board(
    val upperBound: Point,
    val lowerBound: Point,
    val state: MutableMap<Point, Stone>
) {

    fun areCaptured(stones: List<Stone>) = stones.any { !isSurrounded(it) }

    fun isSurrounded(stone: Stone): Boolean {
        return stone.point.neighbours(lowerBound, upperBound).any {
            state[it]?.color == null
        }
    }

    fun getStonesConnectedTo(stone: Stone, connectedStones: Set<Stone> = emptySet()): Set<Stone> {
        val neighbours = stone.point.neighbours(lowerBound, upperBound)
        val stones = neighbours.filter {
            state.containsKey(it) && state[it]!!.color == stone.color && !connectedStones.contains(stone.copy(point = it))
        }.map {
            state[it]
        }
        if (stones.isEmpty()) {
            return connectedStones + stone
        }
        return stones.fold(emptySet<Stone>()) { acc, s ->
            getStonesConnectedTo(s!!, acc + connectedStones + stone)
        }
    }
}