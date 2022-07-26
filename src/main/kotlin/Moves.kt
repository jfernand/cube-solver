/**
 * Optimizes the `moves` input by reducing redundant and unnecessary turns or rotations.
 * For example, "U U'" would be negated; "U U2" is simplified to "U'"; and "U U" is simplified
 * to "U2".
 *
 * @param moves the String of moves to be optimized
 * @return the optimized set of moves
 */
fun oldOptimize(moves: String): String {
    var _moves = moves
    var i = 0
    while (i < _moves.length) {
        val move = _moves.substring(i, i + 1)
        if (move != " " && move != "'" && move != "2") { //Only check if there is a meaningful turn/rotation
            if (i <= _moves.length - 3) {
                when {
                    _moves.substring(i + 1, i + 2).compareTo("2") == 0 -> { //Double turn
                        if (i <= _moves.length - 4 && _moves[i + 3] == _moves[i]) {
                            if (i <= _moves.length - 5) {
                                when {
                                    _moves.substring(i + 4, i + 5).compareTo("2") == 0 -> {
                                        //Ex. "U2 U2" gets negated
                                        _moves = _moves.substring(0, i) + _moves.substring(i + 5)
                                        i--
                                    }
                                    _moves.substring(i + 4, i + 5).compareTo("'") == 0 -> {
                                        //Ex. "U2 U'" --> "U"
                                        _moves = (_moves.substring(0, i) + _moves.substring(i, i + 1)
                                            + _moves.substring(i + 5))
                                        i--
                                    }
                                    else -> {
                                        //Ex. "U2 U" --> "U'"
                                        _moves = (_moves.substring(0, i) + _moves.substring(i, i + 1) + "'"
                                            + _moves.substring(i + 4))
                                        i--
                                    }
                                }
                            } else {
                                //Ex. "U2 U" --> "U'"
                                _moves = (_moves.substring(0, i) + _moves.substring(i, i + 1) + "'"
                                    + _moves.substring(i + 4))
                                i--
                            }
                        }
                    }
                    _moves.substring(i + 1, i + 2).compareTo("'") == 0 -> { //Clockwise turn
                        if (i <= _moves.length - 4 && _moves[i + 3] == _moves[i]) {
                            if (i <= _moves.length - 5) {
                                when {
                                    _moves.substring(i + 4, i + 5).compareTo("2") == 0 -> {
                                        //Ex. "U' U2" --> "U"
                                        _moves = (_moves.substring(0, i) + _moves.substring(i, i + 1)
                                            + _moves.substring(i + 5))
                                        i--
                                    }
                                    _moves.substring(i + 4, i + 5).compareTo("'") == 0 -> {
                                        //Ex. "U' U'" --> "U2"
                                        _moves = (_moves.substring(0, i) + _moves.substring(i, i + 1) + "2"
                                            + _moves.substring(i + 5))
                                        i--
                                    }
                                    else -> {
                                        //Ex. "U' U" gets negated
                                        _moves = _moves.substring(0, i) + _moves.substring(i + 4)
                                        i--
                                    }
                                }
                            } else {
                                //Ex. "U' U" gets negated
                                _moves = _moves.substring(0, i) + _moves.substring(i + 4)
                                i--
                            }
                        }
                    }
                    else -> { //Clockwise turn
                        if (i <= _moves.length - 3 && _moves[i + 2] == _moves[i]) {
                            if (i <= _moves.length - 4) {
                                when {
                                    _moves.substring(i + 3, i + 4).compareTo("2") == 0 -> {
                                        //Ex. "U U2" --> "U' "
                                        _moves = (_moves.substring(0, i) + _moves.substring(i, i + 1) + "'"
                                            + _moves.substring(i + 4))
                                        i--
                                    }
                                    _moves.substring(i + 3, i + 4).compareTo("'") == 0 -> {
                                        //Ex. "U U'" gets negated
                                        _moves = _moves.substring(0, i) + _moves.substring(i + 4)
                                        i--
                                    }
                                    else -> {
                                        //Ex. "U U" --> "U2"
                                        _moves = (
                                            _moves.substring(0, i) + _moves.substring(i, i + 1) + "2"
                                                + _moves.substring(i + 3)
                                            )
                                        i--
                                    }
                                }
                            } else {
                                //Ex. "U U" --> "U2"
                                _moves = (
                                    _moves.substring(0, i) + _moves.substring(i, i + 1) + "2"
                                        + _moves.substring(i + 3)
                                    )
                                i--
                            }
                        }
                    }
                }
            }
        }
        i++
    }
    return _moves
}

/**
 * Generates a random scramble. \n
 * NOTE: the scramble generated is a random move scramble, not a random state scramble. A random state
 * scramble is generated by assembling the cube into a random state, solving the cube, and taking the
 * reverse of the solution to display as a scramble. This method simply assembles a scramble out of
 * the allowed set of face moves, ensuring that no move is the same as the two moves prior to it.
 *
 * @return a random scramble
 */
fun randScramble(): String {
    var scramble = String()
    val possMoves = charArrayOf('U', 'D', 'R', 'L', 'F', 'B') //The allowed set of moves
    var prevMove = possMoves[(Math.random() * 6).toInt()] //Pick random moves as prevMove and secondLastMove for now
    var secondLastMove = possMoves[(Math.random() * 6).toInt()]
    var numMoves = 0
    while (numMoves < 20) {
        val move = possMoves[(Math.random() * 6).toInt()] //Pick a random move
        //Only proceed if the random move is different from the last two
        if (move != prevMove && move != secondLastMove) {
            //Decide whether to add something onto the end of the move
            val rand = (Math.random() * 100).toInt()
            scramble += when {
                rand < 33 -> move.toString() + "2 "
                rand < 67 -> "$move' "
                else -> "$move "
            }
            secondLastMove = prevMove
            prevMove = move
            numMoves++
        }
    }
    return scramble
}

fun optimize(moves: String): String {
//    println("optimize  $moves")
    val ret = optimize(moves.trim().split(" ")).joinToString(" ")
//    println("optimized $moves")
    return ret
}

fun optimize(moves: List<String>): List<String> {
//    println("Optimizing $moves")
    return when {
        moves.size < 2 -> moves
        else -> optimize(moves.component1(), moves.component2(), moves.drop(2))
    }
}

fun optimize(a: String, b: String, rest: List<String>): List<String> {
    assert(a != "")
    assert(b != "")
    return when {
        !(a sameAs b) -> listOf(a).plus(optimize(listOf(b).plus(rest)))
        (a.isDouble() && b.isDouble()) -> optimize(rest)
        a == b -> optimize(listOf(rot2(a)).plus(rest))
        (a.isReverse() && b.isDouble()) || (a.isDouble() && b.isReverse()) -> optimize(listOf(naked(a)).plus(rest))
        (a.isReverse() && !b.isReverse()) || (!a.isReverse() && b.isReverse()) -> optimize(rest)
        (a.isForward() && b.isDouble()) || (a.isDouble() && b.isForward()) -> optimize(listOf(reversed(a)).plus(rest))
        else -> listOf(a).plus(optimize(listOf(b).plus(rest)))
    }
}

private fun rot2(a: String): String = "${a[0]}2"
fun naked(a: String) = "${a[0]}"
fun String.base() = "${this[0]}"

private fun reversed(a: String) = "${a[0]}'"

private fun String.forward() = "${this[0]}"
private fun String.reverse() = "${this[0]}'"

fun String.isReverse(): Boolean = this.length > 1 && this[1] == '\''
fun String.isDouble(): Boolean = this.length > 1 && this[1] == '2'
private fun String.isForward(): Boolean = this.length == 1

private infix fun String.sameAs(a: String): Boolean = naked(a) == naked(this)

fun reverseMoves(moves: String): String {
    var reverseMoves: String = ""
    var i = 0
    while (i < moves.length) {
        if (moves.substring(i, i + 1) != " ") { //Only check if there is a meaningful character
            if (i != moves.length - 1) {
                when {
                    moves.substring(i + 1, i + 2).compareTo("2") == 0 -> {
                        //Turning twice ex. U2
                        reverseMoves += moves.substring(i, i + 1)
                        reverseMoves += " "
                        reverseMoves += moves.substring(i, i + 1)
                        reverseMoves += " "
                        i++ //Skip the "2" for the next iteration
                    }
                    moves.substring(i + 1, i + 2).compareTo("'") == 0 -> {
                        //Making a clockwise turn ex. U
                        reverseMoves += (moves.substring(i, i + 1))
                        reverseMoves += " "
                        i++ //Skip the apostrophe for the next iteration
                    }
                    else -> {
                        //Counterclockwise turning
                        reverseMoves += (moves.substring(i, i + 1) + "'")
                        reverseMoves += " "
                    }
                }
            } else {
                //Nothing is after the turn letter, so perform counterclockwise turn
                reverseMoves += (moves.substring(i, i + 1) + "'")
                reverseMoves += " "
            }
        }
        i++
    }
    return reverseMoves.trim()
}

fun simpleReverseMoves(moves: String): String = moves.split(" ")
    .flatMap {
        when {
            it.isReverse() -> listOf(it.forward())
            it.isForward() -> listOf(it.reverse())
            it.isDouble() -> listOf(it)
            else -> error("You dumbass!")
        }
    }
    .joinToString(" ")
