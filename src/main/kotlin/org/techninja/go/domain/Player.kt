package org.techninja.go.domain

data class Player(
    val stoneColor: Color,
    val moves: MutableSet<Stone> = mutableSetOf(),
    private var captured: Int = 0
) {

    fun getCaptured(): Int {
        return captured
    }

    fun increaseCapturedStoneBy(count: Int) {
        captured += count
    }
}