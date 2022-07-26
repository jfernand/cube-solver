package es.cr.cube

import es.cr.cube.Color.*

interface CubeOps<B> {
    fun front(): B
    fun frontInv(): B
    fun back(): B
    fun backInv(): B
    fun up(): B
    fun upInv(): B
    fun down(): B
    fun downInv(): B
    fun left(): B
    fun leftInv(): B
    fun right(): B
    fun rightInv(): B
}

sealed class Color(val name: String) {
    object Red : Color("R")
    object Green : Color("G")
    object Blue : Color("B")
    object White : Color("W")
    object Yellow : Color("Y")
    object Orange : Color("O")
    object None : Color(" ")

    override fun toString(): String = name
}

val colors = listOf(Red, Green, Blue, White, Yellow, Orange, None)

data class Cubie(
    val up: Color,
    val down: Color,
    val left: Color,
    val right: Color,
    val front: Color,
    val back: Color,
) : CubeOps<Cubie> {
    fun validate() {
        val counts = mutableMapOf<Color, Int>()
        counts[up] = (counts.getOrPut(up) { 0 }) + 1
        counts[down] = (counts.getOrPut(down) { 0 }) + 1
        counts[left] = (counts.getOrPut(left) { 0 }) + 1
        counts[right] = (counts.getOrPut(right) { 0 }) + 1
        counts[front] = (counts.getOrPut(front) { 0 }) + 1
        counts[back] = (counts.getOrPut(back) { 0 }) + 1
        if (counts.computeIfAbsent(None) { 0 } > 3) error("Too many colored faces $this")
        if (counts.computeIfAbsent(Red) { 0 } > 1) error("Too many Red faces $this")
        if (counts.computeIfAbsent(Green) { 0 } > 1) error("Too many Green faces $this")
        if (counts.computeIfAbsent(Blue) { 0 } > 1) error("Too many Blue faces $this")
        if (counts.computeIfAbsent(White) { 0 } > 1) error("Too many White faces $this")
        if (counts.computeIfAbsent(Yellow) { 0 } > 1) error("Too many Yellow faces $this")
        if (counts.computeIfAbsent(Orange) { 0 } > 1) error("Too many Orange faces $this")
    }

    fun isValid(): Boolean {
        val counts = mutableMapOf<Color, Int>()
        counts[up] = (counts.getOrPut(up) { 0 }) + 1
        counts[down] = (counts.getOrPut(down) { 0 }) + 1
        counts[left] = (counts.getOrPut(left) { 0 }) + 1
        counts[right] = (counts.getOrPut(right) { 0 }) + 1
        counts[front] = (counts.getOrPut(front) { 0 }) + 1
        counts[back] = (counts.getOrPut(back) { 0 }) + 1
//        println(counts)
        if (counts.computeIfAbsent(None) { 0 } > 3) return false
        if (counts.computeIfAbsent(Red) { 0 } > 1) return false
        if (counts.computeIfAbsent(Green) { 0 } > 1) return false
        if (counts.computeIfAbsent(Blue) { 0 } > 1) return false
        if (counts.computeIfAbsent(White) { 0 } > 1) return false
        if (counts.computeIfAbsent(Yellow) { 0 } > 1) return false
        if (counts.computeIfAbsent(Orange) { 0 } > 1) return false
        if (counts[Red]!! + counts[Green]!! + counts[Blue]!! + counts[White]!! + counts[Yellow]!! + counts[Orange]!! > 3)
            return false
        return true
    }

    override fun front(): Cubie =
        Cubie(
            up = right,
            down = left,
            left = up,
            right = down,
            front = front,
            back = back,
        )

    override fun frontInv(): Cubie = back()

    override fun back(): Cubie =
        Cubie(
            up = left,
            down = right,
            left = down,
            right = up,
            front = front,
            back = back,
        )

    override fun backInv(): Cubie = front()

    override fun up(): Cubie =
        Cubie(
            up = up,
            down = down,
            left = back,
            right = front,
            front = left,
            back = right,
        )

    override fun upInv(): Cubie = down()

    override fun down(): Cubie =
        Cubie(
            up = up,
            down = down,
            left = front,
            right = back,
            front = right,
            back = left,
        )

    override fun downInv(): Cubie = up()

    override fun left(): Cubie =
        Cubie(
            up = front,
            down = back,
            left = left,
            right = right,
            front = down,
            back = up,
        )

    override fun leftInv(): Cubie = right()

    override fun right(): Cubie =
        Cubie(
            up = back,
            down = front,
            left = left,
            right = right,
            front = up,
            back = down,
        )

    override fun rightInv(): Cubie = left()

    companion object {
        fun random(): Cubie {
            while (true) {
                val cubie = Cubie(
                    colors.shuffled()[0],
                    colors.shuffled()[0],
                    colors.shuffled()[0],
                    colors.shuffled()[0],
                    colors.shuffled()[0],
                    colors.shuffled()[0],
                )
                if (cubie.isValid()) {
                    println("$cubie is valid")
                    return cubie
                } else {
                    println("$cubie is invalid")
                }
            }
        }
    }
}
