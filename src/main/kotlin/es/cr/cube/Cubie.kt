package es.cr.cube

import es.cr.cube.Color.*

interface CubeOps<B> {
    fun front(): B
    fun frontInv(): B
    fun standing(): B
    fun standingInv(): B
    fun back(): B
    fun backInv(): B
    fun up(): B
    fun upInv(): B
    fun equator(): B
    fun equatorInv(): B
    fun down(): B
    fun downInv(): B
    fun left(): B
    fun leftInv(): B
    fun middle(): B
    fun middleInv(): B
    fun right(): B
    fun rightInv(): B
}

sealed class Color(val name: String) {
    object Left : Color("R")
    object Front : Color("G")
    object Back : Color("B")
    object Down : Color("W")
    object Up : Color("Y")
    object Right : Color("O")
    object None : Color("_")

    override fun toString(): String = name
}

val colors = listOf(Left, Front, Back, Down, Up, Right, None, None, None, None, None, None, None)

data class Cubie(
    val up: Color,
    val down: Color,
    val left: Color,
    val right: Color,
    val front: Color,
    val back: Color,
) : CubeOps<Cubie> {
    override fun toString() : String {
        return "$up$down$left$right$front$back"
    }
    fun validate() {
        val counts = mutableMapOf<Color, Int>()
        counts[up] = (counts.getOrPut(up) { 0 }) + 1
        counts[down] = (counts.getOrPut(down) { 0 }) + 1
        counts[left] = (counts.getOrPut(left) { 0 }) + 1
        counts[right] = (counts.getOrPut(right) { 0 }) + 1
        counts[front] = (counts.getOrPut(front) { 0 }) + 1
        counts[back] = (counts.getOrPut(back) { 0 }) + 1
        if (counts.computeIfAbsent(None) { 0 } > 3) error("Too many colored faces $this")
        if (counts.computeIfAbsent(Left) { 0 } > 1) error("Too many Red faces $this")
        if (counts.computeIfAbsent(Front) { 0 } > 1) error("Too many Green faces $this")
        if (counts.computeIfAbsent(Back) { 0 } > 1) error("Too many Blue faces $this")
        if (counts.computeIfAbsent(Down) { 0 } > 1) error("Too many White faces $this")
        if (counts.computeIfAbsent(Up) { 0 } > 1) error("Too many Yellow faces $this")
        if (counts.computeIfAbsent(Right) { 0 } > 1) error("Too many Orange faces $this")
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
        if (counts.computeIfAbsent(Left) { 0 } > 1) return false
        if (counts.computeIfAbsent(Front) { 0 } > 1) return false
        if (counts.computeIfAbsent(Back) { 0 } > 1) return false
        if (counts.computeIfAbsent(Down) { 0 } > 1) return false
        if (counts.computeIfAbsent(Up) { 0 } > 1) return false
        if (counts.computeIfAbsent(Right) { 0 } > 1) return false
        if (counts[Left]!! + counts[Front]!! + counts[Back]!! + counts[Down]!! + counts[Up]!! + counts[Right]!! > 3)
            return false
        return true
    }

    override fun front(): Cubie =
        Cubie(
            up = left,
            down = right,
            left = down,
            right = up,
            front = front,
            back = back,
        )
    override fun back(): Cubie =
        Cubie(
            up = right,
            down = left,
            left = up,
            right = down,
            front = front,
            back = back,
        )
    override fun frontInv(): Cubie = back()
    override fun backInv(): Cubie = front()
    override fun standing() = front()
    override fun standingInv() = back()
    override fun up(): Cubie =
        Cubie(
            up = up,
            down = down,
            left = front,
            back = left,
            right = back,
            front = right,
        )
    override fun down(): Cubie =
        Cubie(
            up = up,
            down = down,
            left = back,
            front = left,
            right = front,
            back = right,
        )
    override fun upInv(): Cubie = down()
    override fun downInv(): Cubie = up()
    override fun equator(): Cubie = up()
    override fun equatorInv(): Cubie = down()
    override fun right(): Cubie =
        Cubie(
            up = front,
            down = back,
            left = left,
            right = right,
            front = down,
            back = up,
        )
    override fun left(): Cubie =
        Cubie(
            up = back,
            down = front,
            left = left,
            right = right,
            front = up,
            back = down,
        )
    override fun rightInv(): Cubie = left()
    override fun leftInv(): Cubie = right()
    override fun middle(): Cubie = right()
    override fun middleInv(): Cubie = left()

    companion object {
        var generated = 0
        fun random(): Cubie {
            while (true) {
                val shuffled = colors.shuffled()
                val cubie = Cubie(
                    colors.random(),
                    colors.random(),
                    colors.random(),
                    colors.random(),
                    colors.random(),
                    colors.random(),
                )
                generated++
                if (cubie.isValid()) {
//                    println("$cubie is valid")
                    return cubie
                } else {
//                    println("$cubie is invalid")
                }
            }
        }
    }
}

fun blank() =
    Cubie(None, None, None, None, None, None)

fun c(
    up: Color = None,
    down: Color = None,
    left: Color = None,
    right: Color = None,
    front: Color = None,
    back: Color = None,
) = Cubie(up, down, left, right, front, back)
