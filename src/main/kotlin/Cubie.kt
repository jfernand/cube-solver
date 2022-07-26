import cube.CubeColor
import cube.CubieColor
import cube.Dir

object NullCubie :Cubie(0,0,0,arrayOf(),false, false)

/**
 * Copyright 2017, Shoumyo Chakravorti, All rights reserved.
 *
 *
 * Licensed under the MIT License.
 *
 *
 * The Cubie class defines the attributes and actions of individual cubies. By defining individual cubies
 * based on their position, colors, and the directions of their colors, a cube can be constructed in the
 * Cube class.
 *
 * @author Shoumyo Chakravorti
 * @version 2.0
 */
open class Cubie
/**
 * Constructs a Cubie object
 * Sets the location of the cubie
 *
 * @param x     the x position of the cubie
 * @param y     the y position of the cubie
 * @param z     the z position of the cubie
 * @param colors  the colors which the cubie will hold
 * @param isCornerCubie whether the cubie is a corner cubie
 * @param isEdgeCubie   whether the cubie is an edge cubie
 */(
    /**
     * @return x location of cubie
     */
    //Store x, y, and z positions of a cubie
     val x: Int,
    /**
     * @return y location of cubie
     */
     val y: Int,
    /**
     * @return z location of cubie
     */
     val z: Int,
    /**
     * Sets the colors of the cubie to those inputed as an array of CubieColors.
     *
     * @param newColors the colors that will be applied to the cubie
     */
    //Store the set of colors associated with a cubie; accessible to all subclasses
    var colors: Array<CubieColor>,
    /**
     * Returns whether the cubie is a corner cubie
     *
     * @return whether corner cubie
     */
    val isCornerCubie: Boolean,
    /**
     * Returns whether the cubie is an edge cubie
     *
     * @return whether edge cubie
     */
    val isEdgeCubie: Boolean
) {

    fun getDirOfColor(color: CubeColor): Dir {
        for (cubieColor in colors) {
            if (cubieColor.color == color) {
                return cubieColor.dir
            }
        }
        return Dir.A
    }

    /**
     * Finds and returns the color in a particular direction on any type of cubie
     *
     * @param dir The direction for which the color is being found
     * @return the direction of the color on the corresponding cubie ('A' if cubie does not have a color in direction dir)
     */
    fun getColorOfDir(dir: Char): Char {
        for (color in colors) {
            if (color._dir == dir) {
                return color._color
            }
        }
        return 'A'
    }

    fun _getColorOfDir(dir: Dir): CubeColor {
        for (color in colors) {
            if (color.dir == dir) {
                return color.color
            }
        }
        return CubeColor.None
    }

    /**
     * Changes the color in the given direction.
     *
     * @param dir:    direction
     * @param ncolor: new color
     */
    fun setColorOfDir(dir: Char, ncolor: Char) {
        for (color in colors) {
            if (color._dir == dir) {
                color._color = ncolor
            }
        }
    }

    fun _setColorOfDir(dir: Dir, ncolor: CubeColor) {
        for (color in colors) {
            if (color.dir == dir) {
                color.color = ncolor
            }
        }
    }

//    /**
//     * Used to aid formation of the white cross
//     *
//     * @param x the x position of the cubie
//     * @param y the y position of the cubie
//     * @return For any EdgeCubie that is NOT in the E Slice, returns the vertical slice that cubie belongs in
//     */
//    fun verticalFace(x: Int, y: Int): Char {
//        return if (isEdgeCubie) {
//            if (x == 0) {
//                'L'
//            } else if (x == 1) {
//                if (y == 0) {
//                    'F'
//                } else {
//                    'B'
//                }
//            } else {
//                'R'
//            }
//        } else 'A'
//    }

    fun verticalFace(x: Int, y: Int): Dir {
        return if (isEdgeCubie) {
            if (x == 0) {
                Dir.L
            } else if (x == 1) {
                if (y == 0) {
                    Dir.F
                } else {
                    Dir.B
                }
            } else {
                Dir.R
            }
        } else Dir.A
    }

    /**
     * If the cubie is a corner cubie, method returns whether the cubie is a white corner
     * Returns false if cubie is not a corner cubie
     *
     * @return whether corner cubie
     */
    val isWhiteCorner: Boolean
        get() = if (isCornerCubie) {
            colors[0]._color == 'W' || colors[1]._color == 'W' || colors[2]._color == 'W'
        } else false
}
