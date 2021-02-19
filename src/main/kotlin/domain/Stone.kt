package domain

data class Stone(
    val color: Color,
    val point: Point
)

enum class Color{
    WHITE {
        override fun opponentColor(): Color {
            return BLACK
        }
    },
    BLACK {
        override fun opponentColor(): Color {
            return WHITE
        }
    };

    abstract fun opponentColor(): Color
}
