import cube.Cube

fun main() {
        while(true) {
                doit()
        }
}
fun doit() {
        var sum = 0.0
        val startTime = System.nanoTime()
//        println("Initializing...")
        val cube = Cube()
        //Scramble it up
//        val scramble = "L R' U2 B2 L2 U2 B2 L D2 R' F2 D B F2 L' D U2 B R' U' L'"
        cube.validate()
        val scramble = randScramble()
        cube.validate()
        cube.display()
        println("Scramble: $scramble\n")
        cube.scramble(scramble)
//        println("Making the sunflower:")
        val sunflower = cube.makeSunflower()
//        println(sunflower.joinToString(" "))
//        println(
//            """
//    Optimized:
//    ${optimize(sunflower).joinToString(" ")}
//
//    """.trimIndent()
//        )
//        cube.displayCube()
//        println("Making the white cross:")
        val whiteCross = cube.makeWhiteCross()
//        println(whiteCross.joinToString(" "))
//        println(
//            """
//    Optimized:
//    ${optimize(whiteCross).joinToString(" ")}
//
//    """.trimIndent()
//        )
//        cube.displayCube()
//        println("Inserting the white corners:")
        val whiteCorners = cube.finishWhiteLayer()
//        println(whiteCorners.joinToString(" "))
//        println(
//            """
//    Optimized:
//    ${optimize(whiteCorners).joinToString(" ")}
//
//    """.trimIndent()
//        )
//        cube.displayCube()
//        println("Finishing second layer:")
        val edges = cube.insertAllEdges()
//        println(edges.joinToString(" "))
//        println(
//            """
//    Optimized:
//    ${optimize(edges).joinToString(" ")}
//
//    """.trimIndent()
//        )
//        cube.displayCube()
//        println("Making the yellow cross:")
        val yellowCross = cube.makeYellowCross()
//        println(yellowCross.joinToString(" "))
//        println(
//            """
//    Optimized:
//    ${optimize(yellowCross).joinToString(" ")}
//
//    """.trimIndent()
//        )
//        cube.displayCube()
//        println("Orienting the last layer:")
        val OLL = cube.orientLastLayer()
//        println(OLL.joinToString(" "))
//        println(
//            """
//    Optimized:
//    ${optimize(OLL).joinToString(" ")}
//
//    """.trimIndent()
//        )
//        cube.displayCube()
//        println("Permuting the last layer:")
        val pllMoves = cube.permuteLastLayer()
//        println(pllMoves.joinToString(" "))
//        println(
//            """
//    Optimized:
//    ${optimize(pllMoves).joinToString(" ")}
//
//    """.trimIndent()
//        )
        cube.display()
        val endTime = System.nanoTime()
        val runtime = endTime - startTime
        sum += runtime.toDouble()
        println(
            """Done in ${sum / 1000000} milliseconds
"""
        )
//        println("Here" + "Hello".substring(5))
    }

