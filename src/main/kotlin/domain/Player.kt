package domain

data class Player(
    val stoneColor: Color,
    val moves: MutableSet<Stone>,
    private var captured: Int = 0
) {

    fun getCaptured(): Int {
        return captured
    }

    fun increaseCapturedStoneBy(count: Int) {
        captured += count
    }
}