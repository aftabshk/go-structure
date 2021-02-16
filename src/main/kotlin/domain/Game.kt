package domain

data class Game(
    val players: List<Player>,
    val board: Board
) {
    fun play(color: Color, point: Point) {
        val stone = Stone(color, point)
        board.state[point] = stone
        players.find { it.stoneColor == color }!!.moves.add(stone)
    }
}