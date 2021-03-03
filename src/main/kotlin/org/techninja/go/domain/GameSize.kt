package org.techninja.go.domain

enum class GameSize(val upperBound: Point, val lowerBound: Point) {
    NINE_BY_NINE(Point(9, 9), Point(1, 1)),
    THIRTEEN_BY_THIRTEEN(Point(9, 9), Point(1, 1)),
    NINETEEN_BY_NINETEEN(Point(9, 9), Point(1, 1))
}