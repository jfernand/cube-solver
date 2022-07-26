package cube

import CubePainter
import Cubie
import NullCubie
import _rotateCcw
import _rotateCw
import base
import cube.CubeColor.*
import cube.Dir.*
import isDouble
import optimize
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import kotlin.math.abs

fun makeMatrix(): Array<Array<Cubie>> =
    Array(3) { arrayOf(NullCubie, NullCubie, NullCubie) }

val FELIKS = "U2 F L2 U2 R2 F L2 F2 L' D' B2 R D2 R' B' U' L' B'"
val SEXY = listOf("R", "U", "R'", "U'")
val SUNE_ALGORITHM = listOf("R", "U", "R'", "U", "R", "U2", "R'")

class Cube {
    //Stores the state of the cube as an object of 26 cubies
     val cubiePos = Array(3) {
        Array(3) {
            Array<Cubie>(3) {
                Cubie(
                    0, 0, 0, arrayOf(CubieColor(Yellow, U), CubieColor(Red, L), CubieColor(Green, F)),
                    isCornerCubie = true,
                    isEdgeCubie = false
                )
            }
        }
    }

    private var bMatrix: Array<Array<Cubie>>
        get() {
            val matrix: Array<Array<Cubie>> = makeMatrix()
            var i = 0
            while (i < 3) {
                var j = 0
                while (j < 3) {
                    matrix[i][j] = cubiePos[abs(j - 2)][2][i]
                    j++
                }
                i++
            }
            return matrix
        }
        set(value) {
            var i = 0
            while (i < 3) {
                var j = 0
                while (j < 3) {
                    cubiePos[abs(j - 2)][2][i] = value[i][j]
                    j++
                }
                i++
            }
        }
    private var dMatrix: Array<Array<Cubie>>
        get() {
            val matrix: Array<Array<Cubie>> = makeMatrix()
            var i = 0
            while (i < 3) {
                var j = 0
                while (j < 3) {
                    matrix[i][j] = cubiePos[j][i][2]
                    j++
                }
                i++
            }
            return matrix
        }
        set(value) {
            var i = 0
            while (i < 3) {
                var j = 0
                while (j < 3) {
                    cubiePos[j][i][2] = value[i][j]
                    j++
                }
                i++
            }
        }

    private var eMatrix: Array<Array<Cubie>>
        get() {
            val matrix: Array<Array<Cubie>> = makeMatrix()
            var i = 0
            while (i < 3) {
                var j = 0
                while (j < 3) {
                    matrix[i][j] = cubiePos[j][i][1]
                    j++
                }
                i++
            }
            return matrix
        }
        set(value) {
            var i = 0
            while (i < 3) {
                var j = 0
                while (j < 3) {
                    cubiePos[j][i][1] = value[i][j]
                    j++
                }
                i++
            }
        }

    private var fMatrix: Array<Array<Cubie>>
        get() {
            val matrix: Array<Array<Cubie>> = makeMatrix()
            var i = 0
            while (i < 3) {
                var j = 0
                while (j < 3) {
                    matrix[i][j] = cubiePos[j][0][i]
                    j++
                }
                i++
            }
            return matrix
        }
        set(value) {
            var i = 0
            while (i < 3) {
                var j = 0
                while (j < 3) {
                    cubiePos[j][0][i] = value[i][j]
                    j++
                }
                i++
            }
        }

    private var lMatrix: Array<Array<Cubie>>
        get() {
            val matrix: Array<Array<Cubie>> = makeMatrix()
            var i = 0
            while (i < 3) {
                var j = 0
                while (j < 3) {
                    matrix[i][j] = cubiePos[0][abs(j - 2)][i]
                    j++
                }
                i++
            }
            return matrix
        }
        set(value) {
            var i = 0
            while (i < 3) {
                var j = 0
                while (j < 3) {
                    cubiePos[0][abs(j - 2)][i] = value[i][j]
                    j++
                }
                i++
            }
        }
    private var mMatrix: Array<Array<Cubie>>
        get() {
            val matrix: Array<Array<Cubie>> = makeMatrix()
            var i = 0
            while (i < 3) {
                var j = 0
                while (j < 3) {
                    matrix[i][j] = cubiePos[1][abs(j - 2)][i]
                    j++
                }
                i++
            }
            return matrix
        }
        set(value) {
            var i = 0
            while (i < 3) {
                var j = 0
                while (j < 3) {
                    cubiePos[1][abs(j - 2)][i] = value[i][j]
                    j++
                }
                i++
            }
        }
    private var rMatrix: Array<Array<Cubie>>
        get() {
            val matrix: Array<Array<Cubie>> = makeMatrix()
            var i = 0
            while (i < 3) {
                var j = 0
                while (j < 3) {
                    matrix[i][j] = cubiePos[2][j][i]
                    j++
                }
                i++
            }
            return matrix
        }
        set(value) {
            var i = 0
            while (i < 3) {
                var j = 0
                while (j < 3) {
                    cubiePos[2][j][i] = value[i][j]
                    j++
                }
                i++
            }
        }
    private var sMatrix: Array<Array<Cubie>>
        get() {
            val matrix: Array<Array<Cubie>> = makeMatrix()
            var i = 0
            while (i < 3) {
                var j = 0
                while (j < 3) {
                    matrix[i][j] = cubiePos[j][1][i]
                    j++
                }
                i++
            }
            return matrix
        }
        set(value) {
            var i = 0
            while (i < 3) {
                var j = 0
                while (j < 3) {
                    cubiePos[j][1][i] = value[i][j]
                    j++
                }
                i++
            }
        }
    private var uMatrix: Array<Array<Cubie>>
        get() {
            val matrix: Array<Array<Cubie>> = makeMatrix()
            var i = 0
            while (i < 3) {
                var j = 0
                while (j < 3) {
                    matrix[i][j] = cubiePos[j][abs(i - 2)][0]
                    j++
                }
                i++
            }
            return matrix
        }
        set(value) {
            var i = 0
            while (i < 3) {
                var j = 0
                while (j < 3) {
                    cubiePos[j][abs(i - 2)][0] = value[i][j]
                    j++
                }
                i++
            }
        }

    /**
     * Takes a String value of a turn or rotation in standard Rubik's Cube notation and applies the turn or rotation to
     * the cube. Valid turns currently include any turn in the following planes: U, D, F, B, L, R, M, E, S
     * Valid rotations are x, y, and z rotations.
     *
     * @param turn the turn to be performed
     */
    fun turn(turn: String?) {
        when (turn) {
            "B" -> B()
            "B'" -> `B'`()
            "D" -> D()
            "D'" -> `D'`()
            "E" -> E()
            "E'" -> `E'`()
            "F" -> F()
            "F'" -> `F'`()
            "L" -> L()
            "L'" -> `L'`()
            "M" -> M()
            "M'" -> `M'`()
            "R" -> R()
            "R'" -> `R'`()
            "S" -> S()
            "S'" -> `S'`()
            "U" -> U()
            "U'" -> `U'`()
            "x" -> performMoves(listOf("R", "M'", "L'"))
            "x'" -> performMoves("R' M L")
            "y" -> performMoves("U E' D'")
            "y'" -> performMoves("U' E D")
            "z" -> performMoves("F S B'")
            "z'" -> performMoves("F' S' B")
            else -> error("I can't $turn here")
        }
    }

    private fun expand(moves: List<String>): List<String> {
        return moves.flatMap {
            when (it) {
                "x" -> listOf("R", "M'", "L'")
                "x'" -> listOf("R'", "M", "L")
                "x2" -> expand(listOf("x", "x"))
                "y" -> listOf("U", "E'", "D'")
                "y'" -> listOf("U'", "E", "D")
                "y2" -> expand(listOf("y", "y"))
                "z" -> listOf("F", "S", "B'")
                "z`" -> listOf("F'", "S'", "B")
                "z2" -> expand(listOf("z", "z"))
                else -> listOf(it)
            }
        }
    }

    private fun `U'`() {
        uMatrix = _rotateCcw(
            uMatrix,
            arrayOf(U, L, B, R, F),
            arrayOf(U, F, L, B, R)
        )
    }

    private fun U() {
        uMatrix = _rotateCw(
            uMatrix,
            arrayOf(U, F, L, B, R),
            arrayOf(U, L, B, R, F)
        )
    }

    private fun `S'`() {
        sMatrix = _rotateCcw(
            sMatrix,
            arrayOf(U, R, D, L),
            arrayOf(L, U, R, D)
        )
    }

    private fun S() {
        sMatrix = _rotateCw(
            sMatrix,
            arrayOf(U, R, D, L),
            arrayOf(R, D, L, U)
        )
    }

    private fun `R'`() {
        rMatrix = _rotateCcw(
            rMatrix,
            arrayOf(R, B, D, F, U),
            arrayOf(R, U, B, D, F)
        )
    }

    private fun R() {
        rMatrix = _rotateCw(
            rMatrix,
            arrayOf(R, U, B, D, F),
            arrayOf(R, B, D, F, U)
        )
    }

    private fun `M'`() {
        mMatrix = _rotateCcw(
            mMatrix,
            arrayOf(U, B, D, F),
            arrayOf(B, D, F, U)
        )
    }

    private fun M() {
        mMatrix = _rotateCw(
            mMatrix,
            arrayOf(B, D, F, U),
            arrayOf(U, B, D, F)
        )
    }

    private fun `L'`() {
        lMatrix = _rotateCcw(
            lMatrix,
            arrayOf(L, U, B, D, F),
            arrayOf(L, B, D, F, U)
        )
    }

    private fun L() {
        lMatrix = _rotateCw(
            lMatrix,
            arrayOf(L, B, D, F, U),
            arrayOf(L, U, B, D, F)
        )
    }

    private fun `F'`() {
        fMatrix = _rotateCcw(
            fMatrix,
            arrayOf(F, U, R, D, L),
            arrayOf(F, L, U, R, D)
        )
    }

    private fun F() {
        fMatrix = _rotateCw(
            fMatrix,
            arrayOf(F, U, R, D, L),
            arrayOf(F, R, D, L, U)
        )
    }

    private fun B() {
        bMatrix = _rotateCw(
            bMatrix,
            arrayOf(B, U, R, D, L),
            arrayOf(B, L, U, R, D)
        )
    }

    private fun `B'`() {
        bMatrix = _rotateCcw(
            bMatrix,
            arrayOf(B, U, R, D, L),
            arrayOf(B, R, D, L, U)
        )
    }

    private fun D() {
        dMatrix = _rotateCw(
            dMatrix,
            arrayOf(D, L, B, R, F),
            arrayOf(D, F, L, B, R)
        )
    }

    private fun `D'`() {
        dMatrix = _rotateCcw(
            dMatrix,
            arrayOf(D, F, L, B, R),
            arrayOf(D, L, B, R, F)
        )
    }

    private fun `E'`() {
        eMatrix = _rotateCcw(
            eMatrix,
            arrayOf(F, L, B, R),
            arrayOf(L, B, R, F)
        )
    }

    private fun E() {
        eMatrix =
            _rotateCw(
                eMatrix,
                arrayOf(L, B, R, F),
                arrayOf(F, L, B, R)
            )
    }

    public fun performMove(move: String) = performMoves(listOf(move.trim()))
    private fun performMoves(moves: String): List<String> = performMoves(moves.trim().split(" ").map { it.trim() })
    private fun performMoves(moves: List<String>): List<String> {
        for (move in expand(moves)) {
            when {
                move.isDouble() -> {
                    turn(move.base())
                    turn(move.base())
                }
                else -> turn(move)
            }
        }
        validate()
        return moves
    }

    /**
     * Performs the inverse of the moves input as parameters. For example, if the parameter is "U' ",
     * the moves "U" will be applied upon the cube to negate the "U' ". Helper method for use in the
     * CubePainter class to rewind moves.
     *
     * @param moves the moves to be reversed
     */
    fun reverseMoves(moves: String) {
        var i = 0
        while (i < moves.length) {
            if (moves.substring(i, i + 1) !== " ") { //Only check if there is a meaningful character
                if (i != moves.length - 1) {
                    when {
                        moves.substring(i + 1, i + 2).compareTo("2") == 0 -> {
                            //Turning twice ex. U2
                            turn(moves.substring(i, i + 1))
                            turn(moves.substring(i, i + 1))
                            i++ //Skip the "2" for the next iteration
                        }
                        moves.substring(i + 1, i + 2).compareTo("'") == 0 -> {
                            //Making a clockwise turn ex. U
                            turn(moves.substring(i, i + 1))
                            i++ //Skip the apostrophe for the next iteration
                        }
                        else -> {
                            //Counterclockwise turning
                            turn(moves.substring(i, i + 1) + "'")
                        }
                    }
                } else {
                    //Nothing is after the turn letter, so perform counterclockwise turn
                    turn(moves.substring(i, i + 1) + "'")
                }
            }
            i++
        }
    }

    /**
     * Scrambles a cube according to WCA rules (White on top, Green in front).
     * After scrambling, returns the cube to the original position (Yellow on top, Green in front) form
     * which a solution can be generated.
     *
     * @param scramble the scramble to be performed
     */
    fun scramble(scramble: String): Cube {
        //Rotate the cube to get white on top, then return cube to original position at end of scramble
        val split = scramble.trim().split(" ")
        performMoves((listOf("z2") + split + listOf("z2")).filterNot { it.isBlank() })
        return this
    }

    /**
     * Once the sunflower is made, this method matches white edges to their respective faces and turns them down
     * one at a time, creating the white cross.
     *
     * @return the moves used to create the white cross
     */
    fun makeWhiteCross(): List<String> {
        println("make white cross")
        val moves = mutableListOf<String>()
        while (numWhiteEdgesOriented() != 0) { //Turn sunflower into cross until no white edges remain in the U layer
            for (i in 0..2) {
                for (j in 0..2) {
                    if (cubiePos[i][j][0].isEdgeCubie) {
                        val tempColors = cubiePos[i][j][0].colors
                        if (tempColors[0].color == White || tempColors[1].color == White) {
                            for (k in 0..1) {
                                //Check for when the white edge is matched up with the respective face and turn it down
                                if (tempColors[k].color == Red && tempColors[k].dir == L ||
                                    tempColors[k].color == Green && tempColors[k].dir == F ||
                                    tempColors[k].color == Orange && tempColors[k].dir == R ||
                                    tempColors[k].color == Blue && tempColors[k].dir == B) {
                                    val turnToMake = cubiePos[i][j][0].verticalFace(i, j)
                                    moves += performMoves(listOf("${turnToMake}2"))
                                }
                            }
                        }
                    }
                }
            }
            //Turn U to try lining up edges that have not been turned down yet
            moves += performMoves("U")
        }
        display()
        return moves
    }

    /**
     * Makes the sunflower (yellow center in the  middle with 4 white edges surrounding it).
     * The sunflower can then be used by makeCross() to make the white cross
     *
     * @return moves used to make sunflower
     */
    fun makeSunflower(): List<String> {
        val moves = mutableListOf<String>()

        //Brings up white edges in D Layer with white facing down
        moves += bringUpDownWhiteEdges()

        //Orients white edges in D Layer with white NOT facing down
        orientWhiteEdges()

        //Brings up white edges in E Layer
        //This one is filled with many if blocks because there are eight different possible orientations for
        //white edges in the E Layer, with none sharing a common move to bring it into the U layer.
        moves += bringUpWhiteEdgesInELayer()

        //Fix any edges that are incorrectly oriented in the U Layer
        //For the sake of reducing move count, I assigned a set of moves for each position,
        //but a solver may simply make U turns to bring the edge in front and perform "F U' R"
        if (numWhiteEdgesOriented() < 5) {
            for (i in 0..2) {
                for (j in 0..2) {
                    if (cubiePos[i][j][0].isEdgeCubie
                        && cubiePos[i][j][0].getDirOfColor(White) != A
                        && cubiePos[i][j][0].getDirOfColor(White) != U) {
                        moves += when (val face = cubiePos[i][j][0].verticalFace(i, j)) {
                            F -> performMoves("F U' R")
                            R -> performMoves("R U' B")
                            B -> performMoves("B U' L")
                            L -> performMoves("L U' F")
                            else -> error("I don't understand $face")
                        }
                    }
                }
            }
        }

        //If fewer than 4 white edges reached the top layer by the end of this, some white edge was missed
        //(This might happen, say, if bringing an edge up from the E Layer unintentionally brings down an incorrectly
        // oriented edge in the U Layer)
        //Recurse to oriented remaining white edges
        if (numWhiteEdgesOriented() < 4) {
            moves += makeSunflower()
        }
        return moves
    }

    //Brings up white edges in E Layer
    //This one is filled with many if blocks because there are eight different possible orientations for
    //white edges in the E Layer, with none sharing a common move to bring it into the U layer.
    private fun bringUpWhiteEdgesInELayer(): MutableList<String> {
        val moves = mutableListOf<String>()
        if (numWhiteEdgesOriented() < 5) {
            for (i in 0..2) {
                for (j in 0..2) {
                    if (cubiePos[i][j][1].isEdgeCubie) {
                        assert(i != 1)
                        assert(i != 1)
                        val tempColors = cubiePos[i][j][1].colors
                        for (k in 0..1) {
                            if (tempColors[k].color == White) {
                                /* Depending on the position of the edge, one of the vertical planes it lies
                                 * in must be cleared of white edges before bringing it up */
                                moves += if (i == 0 && j == 0) {
                                    bringUpEdgeInL(tempColors, k)
                                } else if (i == 2 && j == 0) {
                                    if (tempColors[k].dir == F) {
                                        prepareSlot(2, 1, 0, White) + performMove("R")
                                    } else {
                                        prepareSlot(1, 0, 0, White) + performMove("F'")
                                    }
                                } else if (i == 2 && j == 2) {
                                    if (tempColors[k].dir == B) {
                                        prepareSlot(2, 1, 0, White) + performMove("R'")
                                    } else {
                                        prepareSlot(1, 2, 0, White) + performMove("B")
                                    }
                                } else {
                                    if (tempColors[k].dir == B) {
                                        prepareSlot(0, 1, 0, White) + performMove("L")
                                    } else {
                                        prepareSlot(1, 2, 0, White) + performMove("B'")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return moves
    }

    private fun bringUpEdgeInL(
        tempColors: Array<CubieColor>,
        k: Int
    ): List<String> {
        return if (tempColors[k].dir == L) {
            prepareSlot(1, 0, 0, White) + performMove("F")
        } else {
            assert(tempColors[k].dir == F)
            prepareSlot(0, 1, 0, White) + performMove("L'")
        }
    }

    private fun orientWhiteEdges(): MutableList<String> {
        val moves = mutableListOf<String>()
        if (numWhiteEdgesOriented() < 5) {
            for (i in 0..2) {
                for (j in 0..2) {
                    if (cubiePos[i][j][2].isEdgeCubie && cubiePos[i][j][2].getDirOfColor(White) != A && cubiePos[i][j][2]
                            .getDirOfColor(White) != D) {
                        val vert = cubiePos[i][j][2].verticalFace(i, j)
                        moves += prepareSlot(i, j, 0, White)
                        moves += when (vert) {
                            F -> performMoves(listOf("F'", "U'", "R"))
                            R -> performMoves(listOf("R'", "U'", "B"))
                            B -> performMoves(listOf("B'", "U'", "L"))
                            L -> performMoves(listOf("L'", "U'", "F"))
                            else -> error("Waaaah?")
                        }
                    }
                }
            }
        }
        return moves
    }

    private fun bringUpDownWhiteEdges(): MutableList<String> {
        val moves = mutableListOf<String>()
        if (numWhiteEdgesOriented() < 5) {
            for (i in 0..2) {
                for (j in 0..2) {
                    if (cubiePos[i][j][2].isEdgeCubie && cubiePos[i][j][2].getDirOfColor(White) == D) {
                        moves += prepareSlot(i, j, 0, White)
                        //Get the vertical plane in which the cubie lies
                        val turnToMake = cubiePos[i][j][2].verticalFace(i, j)
                        moves += performMoves(listOf("${turnToMake}2"))
                    }
                }
            }
        }
        return moves
    }

    private fun prepareSlot(x: Int, y: Int, z: Int, color: CubeColor): List<String> {
        var numUTurns = 0
        var tempColor = cubiePos[x][y][z].colors
        while ((tempColor[0].color == color || tempColor[1].color == color) && numUTurns < 5) {
            //Keep turning U until the position (x, y, z) is not occupied by a white edge
            performMoves(listOf("U"))
            tempColor = cubiePos[x][y][z].colors
            numUTurns++
        }

        //Return appropriate amount of U turns
        return when (numUTurns) {
            0, 4 -> listOf()
            1 -> listOf("U")
            2 -> listOf("U2")
            else -> listOf("U'")
        }
    }

    /**
     * Utility method for makeSunflower()
     *
     * @return the number of white edges that are currently in the U layer
     */
    private fun numWhiteEdgesOriented(): Int {
        var numOriented = 0
        for (i in 0..2) {
            for (j in 0..2) {
                if (cubiePos[i][j][0].isEdgeCubie && cubiePos[i][j][0].getDirOfColor(White) == U) {
                    numOriented++
                }
            }
        }
        if (numOriented > 4) error("Impossible")
        return numOriented
    }

    /**
     * Completes the white layer by inserting any white corners in the U layer and fixing misoriented
     * white corners until there are no more white corners in the U layer.
     *
     * @return the moves used to complete the white layer
     */
    fun finishWhiteLayer(): List<String> {
        println("Finish White Layer")
        val moves = mutableListOf<String>()
        //At least check once for corners to be inserted/fixed, and repeat as necessary
        moves += insertCornersInU() + "(corners1)"
        moves += insertMisorientedCorners() + "[miso corners 1]"
        while (whiteCornerinU()) {
            moves += insertCornersInU() + "(corners2)"
            moves += insertMisorientedCorners() + "[miso corners 1]"
        }
        return optimize(moves)
    }

    private fun printMatrix(dir:Dir, matrix: Array<Array<Cubie>>) {
        for(row in matrix) {
            for (cubie in row) {
                print(cubie._getColorOfDir(dir).toChar())
            }
            println()
        }
    }

    /**
     * Utility method for insertCornersinU()
     *
     * @return if there are any white corners in the U layer
     */
    private fun whiteCornerinU(): Boolean {
        for (i in 0..2) {
            for (j in 0..2) {
                if (cubiePos[i][j][0].isCornerCubie && cubiePos[i][j][0].getDirOfColor(White) != A) {
                    return true
                }
            }
        }
        println(false)
        return false
    }

    /**
     * Inserts any white corners that are in the U layer. First positions them to the position (2, 0, 0), then
     * makes U turns and y rotations until the white corner is above its respective slot, and finally inserts
     * the corner by repetitively executing R U R' U'. This is repeated of all white corners in the U layer.
     *
     * @return moves used to insert white corners that are in the U layer
     */
    private fun insertCornersInU(): MutableList<String> {
        println("insert corners in U")
        val moves = mutableListOf<String>()
        var y = 0
        while (y < 3) {
            var x = 0
            while (x < 3) {
                if (cubiePos[x][y][0].isCornerCubie && cubiePos[x][y][0].isWhiteCorner) {
                    //Make U turns until cubie is at (2, 0, 0)
                    moves += `move to (2,0,0)`(x, y) + "(move to (2,0,0))"
                    //Set x and y = 0 for the next loop to avoid using while loop
                    y = 0
                    x = 0

                    moves += prepareWhiteCorner() + "(prepareWhiteCorner)"
                    moves += insertCubie()
                }
                x++
            }
            y++
        }
        return moves
    }

    private fun insertCubie(): MutableList<String> {
        //Insert the cubie
        var numSexyMoves = 0
        //Don't worry, the algorithm "R U R' U'" is commonly referred to as the sexy move in the cubing community
        while (`corner at (2,0,2) is not solved`()) {
            performMoves(SEXY)
            numSexyMoves++
        }
        val moves = mutableListOf<String>()
        if (numSexyMoves == 5) { //5 sexy moves can be condensed into "U R U' R'"
            moves += listOf("U", "R", "U'", "R'")
        } else {
            val sexies = repeat(numSexyMoves, SEXY)
            moves += sexies
        }
        return moves
    }

    private fun repeat(n: Int, moves: List<String>): MutableList<String> {
        val sexies = mutableListOf<String>()
        for (k in 0 until n) {
            sexies += moves
        }
        return sexies
    }

    private fun prepareWhiteCorner(): MutableList<String> {
        println("prepare white corner")
        //Get cubie above respective slot in first layer
        var numUTurns = 0
        var yRotations = 0
        while (!whiteCornerPrepared()) {
            performMoves("U y'")
            numUTurns++
            yRotations++
        }
        val moves = mutableListOf<String>()
        moves += when (numUTurns) {
            0 -> listOf()
            1 -> listOf("U")
            2 -> listOf("U2")
            3 -> listOf("U'")
            else -> error("Too many U turns $numUTurns")
        }
        moves += when (yRotations) {
            0 -> listOf()
            1 -> listOf("y'")
            2 -> listOf("y2")
            3 -> listOf("y")
            else -> error("Too many y rotations turns $yRotations")
        }
        return moves
    }

    private fun `move to (2,0,0)`(x: Int, y: Int): List<String> {
        println("move to 2,0,0 $x, $y")
        return if (x == 0) {
            if (y == 0) {
                performMove("U'")
            } else {
                performMove("U2")
            }
        } else {
            if (y == 2) {
                performMove("U")
            } else {
                listOf()
            }
        }
    }

    /**
     * Properly inserts white corners that are in the first layer but not oriented correctly
     *
     * @return moves used to properly orient misoriented white corners
     */
    private fun insertMisorientedCorners(): MutableList<String> {
        println("insert misoriented corners")
        val moves = mutableListOf<String>()
        for (i in 0..3) {
            moves += performMoves("y")
            if (`corner at (2,0,2) is not solved`() && cubiePos[2][0][2].isWhiteCorner) {
                //Use R U R' U' to get corner to U layer, then insert it in appropriate slot
                moves += performMoves(SEXY)
                moves += insertCornersInU()
            }
        }
        return moves
    }

    /**
     * Utility method for insertCornersInU().
     * Checks for whether the corner cubie at (2, 0, 0) belongs in (2, 0, 2).
     *
     * @return true if cubie at (2, 0, 0) belongs in (2, 0, 2), else false
     */
    private fun whiteCornerPrepared(): Boolean {
        var whiteUp = false

        //Figure out whether the corner cubie is even a white corner
        if (cubiePos[2][0][0].isCornerCubie && cubiePos[2][0][0].getDirOfColor(White) == A) {
            return false
        }

        //If the cubie is a white corner, figure out whether the white sticker is facing up
        if (cubiePos[2][0][0].getDirOfColor(White) == U) {
            whiteUp = true
        }

        //Based on whether white is up, check accordingly if the corner is above the appropriate slot
        return if (whiteUp) {
            cubiePos[2][0][0]._getColorOfDir(R) == cubiePos[1][0][1].colors[0].color &&
                cubiePos[2][0][0]._getColorOfDir(F) == cubiePos[2][1][1].colors[0].color
        } else {
            /*Either the color on the right of the cubie matches its respective center piece OR
             *the color on the front of the cubie matches its respective center piece
             *It is not possible for both to match because if white is not facing up, it will either be facing front or right
             */
            cubiePos[2][0][0]._getColorOfDir(R) == cubiePos[2][1][1].colors[0].color ||
                cubiePos[2][0][0]._getColorOfDir(F) == cubiePos[1][0][1].colors[0].color
        }
    }

    private fun `corner at (2,0,2) is not solved`(): Boolean {
        return if (!cubiePos[2][0][2].isCornerCubie) false
        else {
            display()
            val downColor = cubiePos[2][0][2]._getColorOfDir(D)
            val downFaceColor = cubiePos[1][1][2].colors[0].color
            val downColorCorrect = downColor == downFaceColor
            val frontColor = cubiePos[2][0][2]._getColorOfDir(F)
            val frontFaceColor = cubiePos[1][0][1].colors[0].color
            val frontColorCorrect = frontColor == frontFaceColor
            val rightColor = cubiePos[2][0][2]._getColorOfDir(R)
            val rightFaceColor = cubiePos[2][1][1].colors[0].color
            val rightColorCorrect = rightColor == rightFaceColor
            !(downColorCorrect && frontColorCorrect && rightColorCorrect)
        }
    }

    /**
     * Utilizes the methods insertEdgesInU() and insertMisorientedEdges() to complete the second layer
     *
     * @return A String for the moves used to complete the second layer
     */
    fun insertAllEdges(): List<String> {
        println("Insert All edges")
        val moves = mutableListOf<String>()
        //At least check once for edges to be inserted/fixed, and repeat as necessary
        moves += insertEdgesInU()
        moves += insertMisorientedEdges()
        while (nonYellowEdgesInU()) {
            moves += insertEdgesInU()
            moves += insertMisorientedEdges()
        }
        display()
        return moves
    }

    /**
     * Checks whether any non-yellow edges remain in the U layer.
     * (Any such edges need to be inserted into their respective slot in the second layer)
     *
     * @return whether there is/are non-yellow edges in the U layer
     */
    private fun nonYellowEdgesInU(): Boolean {
        for (i in 0..2) {
            for (j in 0..2) {
                if (cubiePos[i][j][0].isEdgeCubie && cubiePos[i][j][0].getDirOfColor(Yellow) == A) {
                    return true
                }
            }
        }
        return false
    }

    /**
     * Inserts all non-yellow edges in the U layer into their respective slots in the
     * second layer using one of two algorithms
     *
     * @return moves used to insert non-yellow edges in the U layer
     */
    private fun insertEdgesInU(): MutableList<String> {
        println("Insert Edges in U")
        val moves = mutableListOf<String>()
        var i = 0
        while (i < 3) {
            var j = 0
            while (j < 3) {
                if (cubiePos[i][j][0].isEdgeCubie && cubiePos[i][j][0].getDirOfColor(Yellow) == A) {
                    println("Move $i, $j to 1,0,0")
                    //Make U turns to get cubie to (1, 0, 0)
                    val movesTo100 = `move to (1,0,0)`(j, i)
                    println("Moves to 1,0,0 $movesTo100")
                    moves += movesTo100
                    //Insert the cubie in appropriate direction
                    if (cubiePos[1][0][0]._getColorOfDir(U) == cubiePos[0][1][1].colors[0].color) {
                        moves += performMoves("U' L' U L U F U' F'")
                    } else if (cubiePos[1][0][0]._getColorOfDir(U) == cubiePos[2][1][1].colors[0].color) {
                        moves += performMoves("U R U' R' U' F' U F")
                    }
                    //Reset i and j to continue checking for edges to be inserted (foregoes use of while loop)
                    i = 0
                    j = 0
                }
                j++
            }
            i++
        }
        return moves
    }

    private fun `move to (1,0,0)`(j: Int, i: Int): MutableList<String> {
        val moves: MutableList<String> = mutableListOf()
        moves += when (j) {
            1 -> {
                if (i == 0) {
                    performMove("U'")
                } else {
                    performMove("U")
                }
            }
            2 -> {
                performMove("U2")
            }
            else -> listOf()
        }

        //Get cubie above respective slot in second layer
        var numUTurns = 0
        var yRotations = 0
        while (cubiePos[1][0][0]._getColorOfDir(F) != cubiePos[1][0][1].colors[0].color) {
            performMoves("U y'")
            numUTurns++
            yRotations++
        }
        //Add appropriate amount of U turns to moves
        moves += when (numUTurns) {
            1 -> listOf("U")
            2 -> listOf("U2")
            3 -> listOf("U'")
            else -> listOf()
        }
        moves += when (yRotations) {
            1 -> listOf("y'")
            2 -> listOf("y2")
            3 -> listOf("y")
            else -> listOf()
        }
        return moves
    }

    /**
     * If there are any edges in the second layer that were inserted in the incorrect
     * orientation, this method re-inserts them in the correct orientation
     *
     * @return moves used to fix edge orientations in second layer
     */
    private fun insertMisorientedEdges(): MutableList<String> {
        println("Insert Misoriented Edges")
        val moves = mutableListOf<String>()
        for (i in 0..3) {
            moves += performMove("y")
            if (cubiePos[2][0][1].getDirOfColor(Yellow) == A &&
                cubiePos[2][0][1]._getColorOfDir(F) != cubiePos[1][0][1].colors[0].color) {
                //If the edge is the the correct slot but oriented incorrectly, perform an algorithm for this special case
                if (cubiePos[2][0][1]._getColorOfDir(F) == cubiePos[2][1][1].colors[0].color &&
                    cubiePos[2][0][1]._getColorOfDir(R) == cubiePos[1][0][1].colors[0].color) {
                    moves += performMoves("R U R' U2 R U2 R' U F' U' F")
                } else {
                    //If there is an edge that does not belong in the slot at all, take it out and insert in correct slot
                    moves += performMoves("R U R' U' F' U' F")
                    moves += insertEdgesInU()
                }
            }
        }
        return moves
    }

    /**
     * Utility method for yellowEdgeOrientation() and makeYellowCross()
     *
     * @return the number of yellow edges that are already oriented in the U layer
     */
    private fun numYellowEdgesOriented(): Int {
        var numOriented = 0
        for (i in 0..2) {
            for (j in 0..2) {
                if (cubiePos[i][j][0].isEdgeCubie && yellowIsUp(i, j)) {
                    numOriented++
                }
            }
        }
        return numOriented
    }

    /**
     * Utility method for orientLastLayer()
     *
     * @return the number of yellow corners that are already oriented in the U layer
     */
    private fun numYellowCornersOriented(): Int {
        var numOriented = 0
        for (i in 0..2) {
            for (j in 0..2) {
                if (cubiePos[i][j][0].isCornerCubie && yellowIsUp(i, j)) {
                    numOriented++
                }
            }
        }
        return numOriented
    }

    /**
     * Utility method for makeYellowCross(). Determines the shape that the oriented
     * yellow edges make.
     *
     * @return Dot, L, Bar, or Cross
     */
    private fun yellowEdgeOrientation(): String {
        var status = String()
        //The cross has already been made
        //No edges are oriented
        when (numYellowEdgesOriented()) {
            4 -> status = "Cross"
            0 -> status = "Dot"
            2 -> {
                //If two edges are oriented, they either form an L-shape or a Bar
                val xValues = IntArray(2)
                var index = 0
                for (i in 0..2) {
                    for (j in 0..2) {
                        if (cubiePos[i][j][0].isEdgeCubie && yellowIsUp(i, j)) {
                            xValues[index] = i
                            index++
                        }
                    }
                }
                status = if (abs(xValues[0] - xValues[1]) % 2 == 0) {
                    "Bar"
                } else {
                    "L"
                }
            }
        }
        return status
    }

    private fun yellowIsUp(i: Int, j: Int) = cubiePos[i][j][0].getDirOfColor(Yellow) == U

    /**
     * Orients all yellow edges in the U layer based on their current state.
     *
     * @return moves used to make the yellow cross
     */
    fun makeYellowCross(): List<String> {
        println("Make Yellow Cross")
        val moves = mutableListOf<String>()
        val status = yellowEdgeOrientation()
        when {
            status.compareTo("Dot") == 0 -> {
                //Make an L and then subsequently use the algorithm to orient the edges
                moves += performMoves("F R U R' U' F' U2 F U R U' R' F'")
            }
            status.compareTo("L") == 0 -> {
                //Position the L appropriately first
                while (cubiePos[0][1][0].getDirOfColor(Yellow) != U || cubiePos[1][2][0].getDirOfColor(Yellow) != U) {
                    moves += performMove("U")
                }
                moves += performMoves("F U R U' R' F'")
            }
            status.compareTo("Bar") == 0 -> {
                //Position the Bar appropriately first
                while (cubiePos[0][1][0].getDirOfColor(Yellow) != U || cubiePos[2][1][0].getDirOfColor(Yellow) != U) {
                    moves += performMove("U ")
                }
                moves += performMoves("F R U R' U' F'")
            }
        }
        return moves
    }

    /**
     * Finishes the step of orienting the last layer by orienting all yellow corners using
     * a beginner's method algorithm. (This has been left separate from makeYellowCross()
     * to help beginners easily follow the steps to orient the last layer completely.)
     *
     * @return moves used to orient last layer pieces
     */
    fun orientLastLayer(): List<String> {
        println("Orient Last Layer")
        val moves = mutableListOf<String>()
        var numOriented = numYellowCornersOriented()
        //Used while loop since Antisune case requires Sune algorithm to be perform twice for proper orientation
        while (numOriented != 4) {
            assert(numOriented != 3) {println("Oops, 3 corners oriented")}
            when (numOriented) {
                0 -> {
                    print(0)
                    //Turn until there is a yellow sticker on the left of the ULF piece
                    while (cubiePos[0][0][0].getDirOfColor(Yellow) != L) {
                        moves += performMove("U")
                    }
                    //Perform Sune algorithm to orient one corner
                    moves += performMoves(SUNE_ALGORITHM)
                }
                1 -> {
                    print(1)
                    //Sune case
                    while (cubiePos[0][0][0].getDirOfColor(Yellow) != U) {
                        print("U")
                        moves += performMove("U")
                    }
                    moves += performMoves(SUNE_ALGORITHM)
                }
                2 -> {
                    print(2)
                    //Turn until there is a yellow sticker on the front of the ULF piece
                    while (cubiePos[0][0][0].getDirOfColor(Yellow) != F) {
                        print("U")
                        moves += performMove("U")
                    }
                    //Perform Sune algorithm to orient one corner
                    moves += performMoves(SUNE_ALGORITHM)
                }
            }
            //Re-check the number of corners oriented
            numOriented = numYellowCornersOriented()
        }
        println()
        return moves
    }

    /**
     * Permutes the last layer such that all oriented pieces are in the correct positions
     * relative to each other. First permutes the corners, then the edges.
     *
     * @return the moves used to permute the last layer
     */
    fun permuteLastLayer(): List<String> {
        println("Permute Last Layer")
        val moves = mutableListOf<String>()
        //Check the number of "headlights" that exist, i.e. adjacent corners with the same color facing one direction
        //If there are 4 headlights, the corners are already permuted
        var numHeadlights = 0
        for (i in 0..3) {
            performMove("y") //Since we are rotating 4 times, the cube is unaffected in the end
            if (cubiePos[0][0][0]._getColorOfDir(F) == cubiePos[2][0][0]._getColorOfDir(F)) {
                numHeadlights++
            }
        }
        println("$numHeadlights headlights")

        //Permute the corners
        if (numHeadlights == 0) { //If no headlights, create headlights first
            moves += performMoves("R' F R' B2 R F' R' B2 R2")
            numHeadlights = 1
        }
        if (numHeadlights == 1) {
            while (cubiePos[0][2][0]._getColorOfDir(B) != cubiePos[2][2][0]._getColorOfDir(B)) {
                moves += performMove("U")
            }
            moves += performMoves("R' F R' B2 R F' R' B2 R2")
        }

        //Now permute the edges after finding out how many edges are already solved
        var numSolved = 0
        for (i in 0..3) {
            performMove("y")
            if (cubiePos[0][0][0]._getColorOfDir(F) == cubiePos[1][0][0]._getColorOfDir(F)) {
                numSolved++
            }
        }
        if (numSolved == 0) { //If no edges are solved, this will solve one edge
            println("Solve one edge")
            moves += performMoves("R2 U R U R' U' R' U' R' U R'")
            numSolved = 1
        }
        if (numSolved == 1) {
            println("One edge solved")
            display()
            //Use either the clockwise or counterclockwise edge rotation algorithm to solve all corners
            while (cubiePos[0][2][0]._getColorOfDir(B) != cubiePos[1][2][0]._getColorOfDir(B)) {
                print(U)
                moves += performMove("U")
            }
            moves += if (cubiePos[1][0][0]._getColorOfDir(F) == cubiePos[0][0][0]._getColorOfDir(L)) {
                performMoves("R2 U R U R' U' R' U' R' U R'")
            } else {
                performMoves("R U' R U R U R U' R' U' R2")
            }
        }

        //Adjust the U layer to finish the cube!
        while (cubiePos[0][0][0]._getColorOfDir(F) != cubiePos[1][0][1].colors[0].color) {
            print("U")
            moves += performMove("U")
        }
        println("exiting")
        return moves
    }//All 2D arrays are row-major

    //NOTE: the logic following may seem confusing because we need to store the colors as *they will be displayed*.
    //This means, for example, that the left side of the cube will be rotated 90 degrees clockwise such that
    //when displayed, it looks as if it is directly "connected" to the yellow (U) face.

    /**
     * Outputs the position, colors, and respective directions of colors of every cubie making up the cube.
     * Used for debugging purposes prior to GUI development.
     * Outputs in the format: x, y, z, color1, dir1, color2, dir2, color3, dir3 (number of colors and directions dependent on cubie type)
     */
    fun testTurning() {
        for (i in cubiePos.indices) {
            for (j in cubiePos[0].indices) {
                for (k in cubiePos[0][0].indices) {
                    val tempColor = cubiePos[i][j][k].colors
                    print("$i, $j, $k, ")
                    for (l in tempColor.indices) {
                        print(tempColor[l]._color.toString() + ", " + tempColor[l]._dir + ", ")
                    }
                    println()
                }
            }
        }
    }

    /**
     * Sets all the colors of the cube to the colors input by the user during color selection mode.
     * Invoked from the CubePainter class when user decides to proceed to solution after inputing colors.
     * The colors input as the colors[][][] parameter are in a slightly different state than the colors
     * produced by the getColors() method. If the side is not the yellow or white side, then the user
     * input the colors when yellow is above and white is below the desired face. If the face is the yellow
     * face, the user input as if blue was above and green was below the yellow face, the blue and green
     * being in the opposite orientation for when inputing colors on the white face.
     *
     * @param colors all colors to be put into the cube
     */
    fun setAllColors(colors: Array<Array<CharArray>>) {
        //Set Left colors
        setLeftColors(colors)
        //Set Up colors
        setUpColors(colors)
        //Set Front colors
        setFrontColors(colors)
        //Set Back colors
        setBackColors(colors)
        //Set Right colors
        setRightColors(colors)
        //Set Down colors
        setDownColors(colors)
    }

    fun _setAllColors(colors: Array<Array<Array<CubeColor>>>) {
        //Set Left colors
        _setColors(L, colors)
        //Set Up colors
        _setColors(U, colors)
        //Set Front colors
        _setColors(F, colors)
        //Set Back colors
        _setColors(B, colors)
        //Set Right colors
        _setColors(R, colors)
        //Set Down colors
        _setColors(D, colors)
    }

    private fun setDownColors(colors: Array<Array<CharArray>>) {
        for (i in 0..2) {
            for (j in 0..2) {
                cubiePos[j][i][2].setColorOfDir('D', colors[5][i][j])
            }
        }
    }

    private fun _setDownColors(colors: Array<Array<Array<CubeColor>>>) {
        for (i in 0..2) {
            for (j in 0..2) {
                cubiePos[j][i][2]._setColorOfDir(D, colors[5][i][j])
            }
        }
    }

    private fun setRightColors(colors: Array<Array<CharArray>>) {
        for (i in 0..2) {
            for (j in 0..2) {
                cubiePos[2][j][i].setColorOfDir('R', colors[4][i][j])
                colors[4][i][j] = cubiePos[2][j][i].getColorOfDir('R')
            }
        }
    }

    private fun setBackColors(colors: Array<Array<CharArray>>) {
        for (i in 0..2) {
            for (j in 0..2) {
                cubiePos[abs(j - 2)][2][i].setColorOfDir('B', colors[3][i][j])
            }
        }
    }

    private fun setFrontColors(colors: Array<Array<CharArray>>) {
        for (i in 0..2) {
            for (j in 0..2) {
                cubiePos[j][0][i].setColorOfDir('F', colors[2][i][j])
            }
        }
    }

    private fun _setColors(dir: Dir, colors: Array<Array<Array<CubeColor>>>) {
        for (i in 0..2) {
            for (j in 0..2) {
                cubiePos[j][0][i]._setColorOfDir(dir, colors[2][i][j])
            }
        }
    }

    private fun setUpColors(colors: Array<Array<CharArray>>) {
        for (i in 0..2) {
            for (j in 0..2) {
                cubiePos[j][abs(i - 2)][0].setColorOfDir('U', colors[1][i][j])
            }
        }
    }

    @Deprecated("")
    private fun setLeftColors(colors: Array<Array<CharArray>>) {
        for (i in 0..2) {
            for (j in 0..2) {
                cubiePos[0][abs(j - 2)][i].setColorOfDir('L', colors[0][i][j])
            }
        }
    }

    fun display() {
        //NOTE: the logic following may seem confusing because we need to store the colors as *they will be displayed*.
        //This means, for example, that the left side of the cube will be rotated 90 degrees clockwise such that
        //when displayed, it looks as if it is directly "connected" to the yellow (U) face.
        val screen = TerminalBuffer()

        var xVal = 1
        var yVal = 4
        //Populate left colors, constant x
        for (y in 2 downTo 0) {
            for (z in 2 downTo 0) {
                val xPos = xVal + abs(z - 2)
                val yPos = yVal + abs(y - 2)
                val char = cubiePos[0][y][z]._getColorOfDir(L).toChar()
                screen.set(xPos, yPos, char)
            }
        }

        //Up colors, constant z
        xVal += 4
        for (x in 0..2) {
            for (y in 2 downTo 0) {
                val xPos = xVal + x
                val yPos = yVal + abs(y - 2)
                val char = cubiePos[x][y][0]._getColorOfDir(U).toChar()
                screen.set(xPos, yPos, char)
            }
        }
        //Front colors, constant y
        yVal += 4
        for (z in 0..2) {
            for (x in 0..2) {
                val xPos = xVal + x
                val yPos = yVal + z
                var char = cubiePos[x][0][z]._getColorOfDir(F).toChar()
                if (x ==2 && z == 0) char = char.toLowerCase()
                screen.set(xPos, yPos, char)
            }
        }
        //Back colors, constant y
        yVal -= 8
        for (x in 0..2) {
            for (z in 2 downTo 0) {
                val xPos = xVal + x
                val yPos = yVal + abs(z - 2)
                val char = cubiePos[x][2][z]._getColorOfDir(B).toChar()
                screen.set(xPos, yPos, char)
            }
        }
        //Right colors, constant x
        xVal += 4
        yVal += 4
        for (y in 2 downTo 0) {
            for (z in 0..2) {
                val xPos = xVal + z
                val yPos = yVal + abs(y - 2)
                var char = cubiePos[2][y][z]._getColorOfDir(R).toChar()
                if( y == 0 &&z == 2) char = char.toLowerCase()
                screen.set(xPos, yPos, char)
            }
        }
        //Down colors, constant z
        xVal += 4
        for (x in 2 downTo 0) {
            for (y in 2 downTo 0) {
                val xPos = xVal + abs(x - 2)
                val yPos = yVal + abs(y - 2)
                var char = cubiePos[x][y][2]._getColorOfDir(D).toChar()
                if( x == 2 &&y == 0) char = char.toLowerCase()
                screen.set(xPos, yPos, char)
            }
        }
        screen.display()
    }

    fun paintComponent(g: Graphics) {
        //NOTE: the logic following may seem confusing because we need to store the colors as *they will be displayed*.
        //This means, for example, that the left side of the cube will be rotated 90 degrees clockwise such that
        //when displayed, it looks as if it is directly "connected" to the yellow (U) face.
        var xVal = 50
        var yVal = 300
        val size: Int = CubePainter.CUBIE_SIZE
        //Populate left colors, constant x
        for (y in 2 downTo 0) {
            for (z in 2 downTo 0) {
                g.color = getColor(cubiePos[0][y][z]._getColorOfDir(L))
                g.fillRect(xVal + abs(z - 2) * size, yVal + abs(y - 2) * size, size, size)
                //left[Math.abs(y-2)][Math.abs(z-2)] = cubiePos[0][y][z]._getColorOfDir(L);
            }
        }

        //Up colors, constant z
        xVal += size * 3
        for (x in 0..2) {
            for (y in 2 downTo 0) {
                g.color = getColor(cubiePos[x][y][0]._getColorOfDir(U))
                g.fillRect(xVal + x * size, yVal + abs(y - 2) * size, size, size)
                //up[Math.abs(y-2)][x] = cubiePos[x][y][0]._getColorOfDir(U);
            }
        }

        //Front colors, constant y
        yVal += size * 3
        for (z in 0..2) {
            for (x in 0..2) {
                g.color = getColor(cubiePos[x][0][z]._getColorOfDir(F))
                g.fillRect(xVal + x * size, yVal + z * size, size, size)
                //front[z][x] = cubiePos[x][0][z]._getColorOfDir(F);
            }
        }

        //Back colors, constant y
        yVal -= size * 6
        for (x in 0..2) {
            for (z in 2 downTo 0) {
                g.color = getColor(cubiePos[x][2][z]._getColorOfDir(B))
                g.fillRect(xVal + x * size, yVal + abs(z - 2) * size, size, size)
                //back[Math.abs(z-2)][x] = cubiePos[x][2][z]._getColorOfDir(B);
            }
        }

        //Right colors, constant x
        xVal += size * 3
        yVal += size * 3
        for (y in 2 downTo 0) {
            for (z in 0..2) {
                g.color = getColor(cubiePos[2][y][z]._getColorOfDir(R))
                g.fillRect(xVal + z * size, yVal + abs(y - 2) * size, size, size)
                //right[Math.abs(y-2)][z] = cubiePos[2][y][z]._getColorOfDir(R);
            }
        }

        //Down colors, constant z
        xVal += size * 3
        for (x in 2 downTo 0) {
            for (y in 2 downTo 0) {
                g.color = getColor(cubiePos[x][y][2]._getColorOfDir(D))
                g.fillRect(xVal + abs(x - 2) * size, yVal + abs(y - 2) * size, size, size)
                //down[Math.abs(y-2)][Math.abs(x-2)] = cubiePos[x][y][2]._getColorOfDir(D);
            }
        }
        (g as Graphics2D).stroke = CubePainter.s
        g.setColor(Color.BLACK)
        for (k in 0..5) {
            when (k) {
                0 -> {
                    xVal = 50
                    yVal = 300
                }
                1 -> xVal += size * 3
                2 -> yVal += size * 3
                3 -> yVal -= size * 6
                4 -> {
                    xVal += size * 3
                    yVal += size * 3
                }
                5 -> xVal += size * 3
            }
            for (i in 0..2) {
                for (j in 0..2) {
                    g.drawRect(xVal + j * size, yVal + i * size, size, size)
                }
            }
        }
    }

    private fun getColor(color: CubeColor): Color = when (color) {
        White -> Color.WHITE
        Yellow -> Color.YELLOW
        Blue -> Color.BLUE
        Green -> Color.GREEN
        Red -> Color.RED
        Orange -> Color.ORANGE
        else -> Color.BLACK
    }

    /**
     * Constructs the Cube object by instantiating a Cubie for each position in three-dimensional space
     * When the cube is held with Yellow facing up and Green facing front, x increases going from left to right,
     * y increases going from the front to the back, and z increases going from the top to the bottom.
     * x, y, and z are zero-indexed.
     * The core of the cube is not an actual cubie, but is instantiated as one to prevent runtime error
     */
    init {

        //Up, Front Row
        cubiePos[0][0][0] = Cubie(
            0, 0, 0, arrayOf(CubieColor(Yellow, U), CubieColor(Red, L), CubieColor(Green, F)),
            isCornerCubie = true,
            isEdgeCubie = false
        )
        cubiePos[1][0][0] = Cubie(
            1, 0, 0, arrayOf(CubieColor(Yellow, U), CubieColor(Green, F)), isCornerCubie = false, isEdgeCubie = true
        )
        cubiePos[2][0][0] = Cubie(
            2, 0, 0, arrayOf(CubieColor(Yellow, U), CubieColor(Green, F), CubieColor(Orange, R)),
            isCornerCubie = true,
            isEdgeCubie = false
        )

        //Front, E Row
        cubiePos[0][0][1] = Cubie(
            0, 0, 1, arrayOf(CubieColor(Red, L), CubieColor(Green, F)), isCornerCubie = false, isEdgeCubie = true
        )
        cubiePos[1][0][1] = Cubie(
            1, 0, 1, arrayOf(CubieColor(Green, F)), isCornerCubie = false, isEdgeCubie = false
        )
        cubiePos[2][0][1] = Cubie(
            2, 0, 1, arrayOf(CubieColor(Green, F), CubieColor(Orange, R)), isCornerCubie = false, isEdgeCubie = true
        )

        //Down, Front Row
        cubiePos[0][0][2] = Cubie(
            0, 0, 2, arrayOf(CubieColor(White, D), CubieColor(Red, L), CubieColor(Green, F)),
            isCornerCubie = true,
            isEdgeCubie = false
        )
        cubiePos[1][0][2] = Cubie(
            1, 0, 2, arrayOf(CubieColor(White, D), CubieColor(Green, F)), isCornerCubie = false, isEdgeCubie = true
        )
        cubiePos[2][0][2] = Cubie(
            2, 0, 2, arrayOf(CubieColor(White, D), CubieColor(Green, F), CubieColor(Orange, R)),
            isCornerCubie = true,
            isEdgeCubie = false
        )

        //Up, S Row
        cubiePos[0][1][0] = Cubie(
            0, 1, 0, arrayOf(CubieColor(Red, L), CubieColor(Yellow, U)), isCornerCubie = false, isEdgeCubie = true
        )
        cubiePos[1][1][0] = Cubie(
            1, 1, 0, arrayOf(CubieColor(Yellow, U)), isCornerCubie = false, isEdgeCubie = false
        )
        cubiePos[2][1][0] = Cubie(
            2, 1, 0, arrayOf(CubieColor(Yellow, U), CubieColor(Orange, R)), isCornerCubie = false, isEdgeCubie = true
        )

        //E, S Row
        cubiePos[0][1][1] = Cubie(
            0, 1, 1, arrayOf(CubieColor(Red, L)), isCornerCubie = false, isEdgeCubie = false
        )
        cubiePos[1][1][1] = Cubie(
            1,
            1,
            1, arrayOf(CubieColor(None, A)),  //Just giving random, non-legitimate values for color and direction
            isCornerCubie = false,
            isEdgeCubie = false
        )
        cubiePos[2][1][1] = Cubie(
            2, 1, 1, arrayOf(CubieColor(Orange, R)), isCornerCubie = false, isEdgeCubie = false
        )

        //Down, S Row
        cubiePos[0][1][2] = Cubie(
            0, 1, 2, arrayOf(CubieColor(Red, L), CubieColor(White, D)), isCornerCubie = false, isEdgeCubie = true
        )
        cubiePos[1][1][2] = Cubie(
            1, 1, 2, arrayOf(CubieColor(White, D)), isCornerCubie = false, isEdgeCubie = false
        )
        cubiePos[2][1][2] = Cubie(
            2, 1, 2, arrayOf(CubieColor(White, D), CubieColor(Orange, R)), isCornerCubie = false, isEdgeCubie = true
        )

        //Up, Back Row
        cubiePos[0][2][0] = Cubie(
            0, 2, 0, arrayOf(CubieColor(Yellow, U), CubieColor(Red, L), CubieColor(Blue, B)),
            isCornerCubie = true,
            isEdgeCubie = false
        )
        cubiePos[1][2][0] = Cubie(
            1, 2, 0, arrayOf(CubieColor(Yellow, U), CubieColor(Blue, B)), isCornerCubie = false, isEdgeCubie = true
        )
        cubiePos[2][2][0] = Cubie(
            2, 2, 0, arrayOf(CubieColor(Yellow, U), CubieColor(Blue, B), CubieColor(Orange, R)),
            isCornerCubie = true,
            isEdgeCubie = false
        )

        //E, Back Row
        cubiePos[0][2][1] = Cubie(
            0, 2, 1, arrayOf(CubieColor(Red, L), CubieColor(Blue, B)), isCornerCubie = false, isEdgeCubie = true
        )
        cubiePos[1][2][1] = Cubie(
            1, 2, 1, arrayOf(CubieColor(Blue, B)), isCornerCubie = false, isEdgeCubie = false
        )
        cubiePos[2][2][1] = Cubie(
            2, 2, 1, arrayOf(CubieColor(Blue, B), CubieColor(Orange, R)), isCornerCubie = false, isEdgeCubie = true
        )

        //Down, Back Row
        cubiePos[0][2][2] = Cubie(
            0, 2, 2, arrayOf(CubieColor(White, D), CubieColor(Red, L), CubieColor(Blue, B)),
            isCornerCubie = true,
            isEdgeCubie = false
        )
        cubiePos[1][2][2] = Cubie(
            1, 2, 2, arrayOf(CubieColor(White, D), CubieColor(Blue, B)), isCornerCubie = false, isEdgeCubie = true
        )
        cubiePos[2][2][2] = Cubie(
            2, 2, 2, arrayOf(CubieColor(White, D), CubieColor(Blue, B), CubieColor(Orange, R)),
            isCornerCubie = true,
            isEdgeCubie = false
        )
    }

    fun validate() {
        var corners = 0
        var edges = 0
        val colors = mutableMapOf<CubeColor, Int>()
        CubeColor.values().forEach { colors[it] = 0 }
        for (x in cubiePos)
            for (y in x)
                for (cubie in y) {
                    corners += if (cubie.isCornerCubie) 1 else 0
                    edges += if (cubie.isEdgeCubie) 1 else 0
                    cubie.colors.forEach {
                        colors[it.color] = colors.getOrDefault(it.color, 0) + 1
                    }
                }
        assert(colors[Red] == 9)
        assert(colors[Green] == 9)
        assert(colors[Blue] == 9)
        assert(colors[Yellow] == 9)
        assert(colors[White] == 9)
        assert(colors[Orange] == 9)
        assert(colors[None] == 1)
    }
}


