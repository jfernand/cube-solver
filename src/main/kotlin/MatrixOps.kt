
import cube.Dir
import cube.makeMatrix

private fun adjustDirections(
    matrix: Array<Array<Cubie>>,
    map: Map<Char, Char>,
) {
    for (i in 0..2) {
        for (j in 0..2) {
            matrix[i][j].colors = matrix[i][j].colors.map {
                it._dir = map[it._dir] ?: error("Error mapping Cubie")
                it
            }.toTypedArray()
        }
    }
}

//private fun adjustDirections(
//    rotated: Array<Array<Cubie>>,
//    preChange: CharArray,
//    postChange: CharArray
//) {
//    for (i in 0..2) {
//        for (j in 0..2) {
//            val tempColors = rotated[i][j].colors
//            for (k in tempColors.indices) {
//                var index = 6
//                for (x in preChange.indices) {
//                    if (tempColors[k]._dir == preChange[x]) {
//                        index = x
//                    }
//                }
//                if (index < postChange.size) {
//                    tempColors[k]._dir = postChange[index]
//                }
//            }
//            rotated[i][j].colors = tempColors
//        }
//    }
//}

private fun _adjustDirections(
    rotated: Array<Array<Cubie>>,
    preChange: Array<Dir>,
    postChange: Array<Dir>
) {
    for (i in 0..2) {
        for (j in 0..2) {
            val tempColors = rotated[i][j].colors
            for (k in tempColors.indices) {
                var index = 6
                for (x in preChange.indices) {
                    if (tempColors[k].dir == preChange[x]) {
                        index = x
                    }
                }
                if (index < postChange.size) {
                    tempColors[k].dir = postChange[index]
                }
            }
            rotated[i][j].colors = tempColors
        }
    }
}
private fun reverseRows(rotated: Array<Array<Cubie>>) {
    for (i in 0..2) {
        for (j in 0 until rotated[0].size / 2) {
            val tempCubie = rotated[i][3 - j - 1]
            rotated[i][3 - j - 1] = rotated[i][j]
            rotated[i][j] = tempCubie
        }
    }
}

private fun reverseColumns(rotated: Array<Array<Cubie>>) {
    for (i in 0 until rotated[0].size / 2) {
        for (j in 0..2) {
            val tempCubie = rotated[3 - i - 1][j]
            rotated[3 - i - 1][j] = rotated[i][j]
            rotated[i][j] = tempCubie
        }
    }
}

private fun transpose(orig: Array<Array<Cubie>>): Array<Array<Cubie>> {
    val rotated: Array<Array<Cubie>> = makeMatrix()
    for (i in 0..2) {
        for (j in 0..2) {
            rotated[i][j] = orig[j][i]
        }
    }
    return rotated
}

/**
 * Rotates a given 2D matrix as specified by `degrees`, where `degrees`
 * can either be 90, indicating a clockwise rotation, or -90, indicating a counterclockwise
 * rotation. `postChange` dictates how the direction of a cubie's colors should
 * change after the rotation, the original directions being denoted by `preChange`.
 *
 * @param orig       the original matrix
 * @param degrees    degrees by which to rotate, can be 90 or -90
 * @param preChange  the set of direction prior to rotation
 * @param postChange the corresponding set of direction to change the `preChange` directions to
 * @return the rotated matrix
 */
//private fun rotateMatrix(
//    orig: Array<Array<Cubie>>, degrees: Int, preChange: CharArray,
//    postChange: CharArray
//): Array<Array<Cubie>> {
//    val rotated = transpose(orig)
//    if (degrees == 90) {
//        reverseRows(rotated)
//    } else {
//        reverseColumns(rotated)
//    }
//
//    //Change the direction of all colors appropriately as well before returning the array
//    adjustDirections(rotated, preChange, postChange)
//    return rotated
//}

private fun _rotateMatrix(
    orig: Array<Array<Cubie>>,
    degrees: Int,
    preChange: Array<Dir>,
    postChange: Array<Dir>
): Array<Array<Cubie>> {
    val rotated = transpose(orig)
    if (degrees == 90) {
        reverseRows(rotated)
    } else {
        reverseColumns(rotated)
    }

    //Change the direction of all colors appropriately as well before returning the array
    _adjustDirections(rotated, preChange, postChange)
    return rotated
}

//fun rotateCw(
//    orig: Array<Array<Cubie>>,
//    preChange: CharArray,
//    postChange: CharArray
//) = rotateMatrix(orig, 90, preChange, postChange)

//fun rotateCcw(
//    orig: Array<Array<Cubie>>,
//    preChange: CharArray,
//    postChange: CharArray
//) = rotateMatrix(orig, -90, preChange, postChange)

fun _rotateCw(
    orig: Array<Array<Cubie>>,
    preChange: Array<Dir>,
    postChange: Array<Dir>
) = _rotateMatrix(orig, 90, preChange, postChange)

fun _rotateCcw(
    orig: Array<Array<Cubie>>,
    preChange: Array<Dir>,
    postChange: Array<Dir>
) = _rotateMatrix(orig, -90, preChange, postChange)
