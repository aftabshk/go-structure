package domain

data class Stone(
    val color: Color,
    val point: Point
)

enum class Color{
    WHITE,
    BLACK
}
