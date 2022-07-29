package es.cr.cube

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class CubieTests {

    @Test
    fun tests() {
        test(Cubie::up, Cubie::upInv, "up")
        test(Cubie::down, Cubie::downInv, "down")
        test(Cubie::left, Cubie::leftInv, "left")
        test(Cubie::right, Cubie::rightInv, "right")
        test(Cubie::front, Cubie::frontInv, "front")
        test(Cubie::back, Cubie::backInv, "back")
    }

    @Test
    fun testsRandomCubies() {
        val cubies = HashSet<Cubie>()
        var i = 0
        while (cubies.size != 2400) {
            cubies.add(Cubie.random())
            i++
        }
        println("Cubies ${cubies.size} after $i attempts and ${Cubie.generated} random cubies")
    }
}

fun test(op: Cubie.() -> Cubie, inverse: Cubie.() -> Cubie, message: String) {
    for (i in 0..100) {
        val cubie = Cubie.random()
        assertEquals(cubie, cubie.op().inverse(), "$message inverse")
        assertEquals(cubie, cubie.op().op().op().op(), "$message cycle")
    }
}
