import cube.Cube

object Debugging {
    @JvmStatic
    fun main(args: Array<String>) {
        val cube = Cube()
        cube.turn("U")
        cube.testTurning()
        println("\n\n\n\n")
        cube.turn("U'")
        cube.testTurning()

        //Working:
        //F, B, R, L, S, M, E, D, U
        //S follows F, M follows L, E follows D
    }
}
