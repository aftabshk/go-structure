package domain

data class Player(
    val stoneColor: Color,
    val moves: MutableList<Stone>
)