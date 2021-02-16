package domain

data class Game(
    val players: List<Player>,
    val board: Board
) {

    //TODO: Add more tests for capture
    fun play(color: Color, point: Point) {
        val stone = Stone(color, point)
        board.state[point] = stone
        val currentPlayer = players.find { it.stoneColor == color }!!
        currentPlayer.moves.add(stone)
        val neighbours = point.neighbours(board.lowerBound, board.upperBound)
        val opponentStones = neighbours.filter { board.state[it]?.color != color }.map { board.state[it]!! }
        opponentStones.forEach { opponentStone ->
            board.getStonesConnectedTo(opponentStone).let {
                if (board.areCaptured(stones = it)) {
                    board.capture(it)
                    currentPlayer.increaseCapturedStoneBy(it.size)
                }
            }
        }
    }
}