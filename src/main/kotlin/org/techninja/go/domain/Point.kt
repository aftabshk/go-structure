package org.techninja.go.domain

import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.KeyDeserializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

@JsonDeserialize(keyUsing = PointDeserializer::class)
data class Point(
    val x: Int,
    val y: Int
) {
    fun isWithinBound(lowerBound: Point, upperBound: Point): Boolean{
        return isXWithinBound(lowerBound, upperBound) && isYWithinBound(lowerBound, upperBound)
    }

    private fun isYWithinBound(lowerBound: Point, upperBound: Point) =
        this.y >= lowerBound.y && this.y <= upperBound.y

    private fun isXWithinBound(lowerBound: Point, upperBound: Point) =
        this.x >= lowerBound.x && this.x <= upperBound.x

    fun neighbours(lowerBound: Point, upperBound: Point): List<Point>{
        return mutableListOf<Point>().let {
            it.add(this.copy(x = this.x - 1))
            it.add(this.copy(x = this.x + 1))
            it.add(this.copy(y = this.y - 1))
            it.add(this.copy(y = this.y + 1))
            it
        }.filter {
            it.isWithinBound(lowerBound, upperBound)
        }
    }

    override fun toString(): String {
        return "$x, $y"
    }
}

class PointDeserializer : KeyDeserializer() {

    override fun deserializeKey(key: String, deserializationContext: DeserializationContext): Point {
        val (x, y) = key.split(", ")
        return Point(x.toInt(), y.toInt())
    }
}
