package cube

class CubieColor( var color: CubeColor, var dir: Dir,) {
    constructor(col: Char, dir: Char) :this(col.toColor(), Dir.valueOf(dir.toString()))

    var _dir: Char
        get() = dir.name[0]
        set(value) {
            dir = Dir.valueOf(value.toString())
        }
    var _color:Char
        get() = color.name[0]
        set(value) {
            color = value.toColor()
        }
}

enum class Dir {
    U,D,
    L,R,
    F,B,
    A
}

enum class CubeColor {
    White,
    Red,
    Orange,
    Blue,
    Yellow,
    Green,
    None;

    fun toChar(): Char {
        return when(this) {
             White -> 'W'
            Red -> 'R'
            Orange -> 'O'
            Blue -> 'B'
            Yellow -> 'Y'
            Green -> 'G'
            None -> 'A'
        }
    }
}

fun Char.toColor(): CubeColor {
    return when(this) {
        'W' -> CubeColor.White
        'R' -> CubeColor.Red
        'O' -> CubeColor.Orange
        'B' -> CubeColor.Blue
        'Y' -> CubeColor.Yellow
        'G' -> CubeColor.Green
        'A' -> CubeColor.None
        else -> error("")
    }
}
