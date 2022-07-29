package es.cr.cube

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class CubeTests {

    @Test
    fun tests() {
        test(Cube::up, Cube::upInv, "up")
        test(Cube::equator, Cube::equatorInv, "equator")
        test(Cube::down, Cube::downInv, "down")
        test(Cube::left, Cube::leftInv, "left")
        test(Cube::middle, Cube::middleInv, "middle")
        test(Cube::right, Cube::rightInv, "right")
        test(Cube::front, Cube::frontInv, "front")
        test(Cube::standing, Cube::standingInv, "standing")
        test(Cube::back, Cube::backInv, "back")
    }
}

fun test(op:Cube.()->Cube, inverse:Cube.()->Cube, message:String) {
        val cube = unscrambled()
    assertEquals(cube, cube.op().op().op().op(), "$message cycle")
    assertEquals(cube, cube.op().inverse(), "$message inverse")
}
