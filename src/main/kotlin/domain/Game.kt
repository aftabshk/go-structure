package domain

data class Game(
    val players: List<Player>,
    val board: Board
) {

    fun play(color: Color, point: Point) {
        val stone = Stone(color, point)
        board.state[point] = stone
        val currentPlayer = players.find { it.stoneColor == color }!!
        currentPlayer.moves.add(stone)
        captureStones(point, color, currentPlayer)
    }

    private fun captureStones(point: Point, color: Color, currentPlayer: Player) {
        val opponentStones = getOpponentStones(point, color)
        opponentStones.forEach { opponentStone ->
            board.getStonesConnectedTo(opponentStone).let {
                if (board.areCaptured(stones = it)) {
                    board.capture(it)
                    currentPlayer.increaseCapturedStoneBy(it.size)
                }
            }
        }
    }

    private fun getOpponentStones(point: Point, color: Color): List<Stone> {
        val neighbours = point.neighbours(board.lowerBound, board.upperBound)
        return neighbours.filter { isOpponentStone(it, color) }.map { board.state[it]!! }
    }

    private fun isOpponentStone(it: Point, color: Color) = board.state[it]?.color == color.opponentColor()
}