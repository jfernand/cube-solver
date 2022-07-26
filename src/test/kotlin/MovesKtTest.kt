import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class MovesKtTest {

    @Test
    fun optimizeMoves() {
        assertEquals("", oldOptimize("U2 U2"))
        assertEquals("", oldOptimize("U' U"))
        assertEquals("", oldOptimize("U U'"))
        assertEquals("", oldOptimize("U U U U"))
        assertEquals("U", oldOptimize("U2 U'"))
        assertEquals("U", oldOptimize("U' U2"))
        assertEquals("U'", oldOptimize("U2 U"))
        assertEquals("U'", oldOptimize("U U2"))
        assertEquals("U'", oldOptimize("U U U"))
        assertEquals("U2", oldOptimize("U' U'"))
        assertEquals("U2", oldOptimize("U U"))
        assertEquals("U D U2", oldOptimize("U D U U"))
    }

    @Test
    fun optimizeMoves2() {
        assertEquals("", optimize("U' U"))
        assertEquals("", optimize("U2 U2"))
        assertEquals("", optimize("U U'"))
        assertEquals("", optimize("U U U U"))
        assertEquals("U", optimize("U2 U'"))
        assertEquals("U", optimize("U' U2"))
        assertEquals("U'", optimize("U2 U"))
        assertEquals("U'", optimize("U U2"))
        assertEquals("U'", optimize("U U U"))
        assertEquals("U2", optimize("U' U'"))
        assertEquals("U2", optimize("U U"))
        assertEquals("U D U2", optimize("U D U U"))
    }

    @Test
    fun oldReverseMoves() {
        assertEquals("U U U U", reverseMoves("U2 U2"))
        assertEquals("U U'", reverseMoves("U' U"))
        assertEquals("U' U", reverseMoves("U U'"))
        assertEquals("U' U' U' U'", reverseMoves("U U U U"))
        assertEquals("U U U", reverseMoves("U2 U'"))
    }

    @Test
    fun reverseMoves() {
        assertEquals("U2 U2", simpleReverseMoves("U2 U2"))
        assertEquals("U U'", simpleReverseMoves("U' U"))
        assertEquals("U' U", simpleReverseMoves("U U'"))
        assertEquals("U' U' U' U'", simpleReverseMoves("U U U U"))
        assertEquals("U2 U", simpleReverseMoves("U2 U'"))
    }
}
