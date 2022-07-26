package cube

class TerminalBuffer {
    private var screen: Array<CharArray> = Array(17) { CharArray(13) { '.' } }

    fun set(x: Int, y: Int, toChar: Char) {
//        println("$x,$y")
        screen[x][y] = toChar
    }

    fun display() {
        for (column in 0 until screen[0].size) {
            for (row in 0 until screen.size) {
                print(screen[row][column])
            }
            println()
        }
    }
}
