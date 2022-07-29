package es.cr.cube

import es.cr.cube.Color.*

data class Cube(
    val ulf: Cubie,
    val ul: Cubie,
    val ulb: Cubie,
    val ub: Cubie,
    val urb: Cubie,
    val ur: Cubie,
    val urf: Cubie,
    val uf: Cubie,

    val lf: Cubie,
    val lb: Cubie,
    val rb: Cubie,
    val rf: Cubie,

    var dlf: Cubie,
    var dl: Cubie,
    var dlb: Cubie,
    var db: Cubie,
    val drb: Cubie,
    val dr: Cubie,
    val drf: Cubie,
    val df: Cubie,
) : CubeOps<Cube> {
    override fun toString(): String {
        return "$ulf|$ul|$ulb|$ub|$urb|$ur|$urf|$uf|$lf|$lb|$rb|$rf|$dlf|$dl|$dlb|$db|$drb|$dr|$drf|$df"
    }

    override fun front(): Cube =
        copy(
            urf = ulf.front(),
            drf = urf.front(),
            dlf = drf.front(),
            ulf = dlf.front(),
            df = rf.front(),
            lf = df.front(),
            uf = lf.front(),
            rf = uf.front()
        )

    override fun frontInv(): Cube =
        copy(
            dlf = ulf.frontInv(),
            drf = dlf.frontInv(),
            urf = drf.frontInv(),
            ulf = urf.frontInv(),
            uf = rf.frontInv(),
            lf = uf.frontInv(),
            df = lf.frontInv(),
            rf = df.frontInv()
        )

    override fun standing(): Cube =
        copy(
            ul = ur.standing(),
            dl = ul.standing(),
            dr = dl.standing(),
            ur = dr.standing(),
        )

    override fun standingInv(): Cube =
        copy(
            ul = dl.standingInv(),
            ur = ul.standingInv(),
            dr = ur.standingInv(),
            dl = dr.standingInv(),
        )

    override fun back(): Cube =
        copy(
            dlb = ulb.back(),
            drb = dlb.back(),
            urb = drb.back(),
            ulb = urb.back(),
            ub = rb.back(),
            lb = ub.back(),
            db = lb.back(),
            rb = db.back(),
        )

    override fun backInv(): Cube =
        copy(
            urb = ulb.backInv(),
            drb = urb.backInv(),
            dlb = drb.backInv(),
            ulb = dlb.backInv(),
            rb = ub.backInv(),
            db = rb.backInv(),
            lb = db.backInv(),
            ub = lb.backInv(),
        )

    override fun up(): Cube =
        copy(
            ulb = ulf.up(),
            urb = ulb.up(),
            urf = urb.up(),
            ulf = urf.up(),
            ub = ul.up(),
            ur = ub.up(),
            uf = ur.up(),
            ul = uf.up(),
        )

    override fun upInv(): Cube =
        copy(
            ulf = ulb.upInv(),
            urf = ulf.upInv(),
            urb = urf.upInv(),
            ulb = urb.upInv(),
            ul = ub.upInv(),
            uf = ul.upInv(),
            ur = uf.upInv(),
            ub = ur.upInv(),
        )

    override fun equator(): Cube =
        copy(
            lf = rf.equator(),
            lb = lf.equator(),
            rb = lb.equator(),
            rf = rb.equator()
        )

    override fun equatorInv(): Cube =
        copy(
            rb = rf.equatorInv(),
            lb = rb.equatorInv(),
            lf = lb.equatorInv(),
            rf = lf.equatorInv()
        )

    override fun down(): Cube =
        copy(
            drf = dlf.down(),
            drb = drf.down(),
            dlb = drb.down(),
            dlf = dlb.down(),
            dr = df.down(),
            db = dr.down(),
            dl = db.down(),
            df = dl.down(),
        )

    override fun downInv(): Cube =
        copy(
            dlb = dlf.downInv(),
            drb = dlb.downInv(),
            drf = drb.downInv(),
            dlf = drf.downInv(),
            dl = df.downInv(),
            db = dl.downInv(),
            dr = db.downInv(),
            df = dr.downInv(),
        )

    override fun left(): Cube =
        copy(
            dlf = ulf.left(),
            dlb = dlf.left(),
            ulb = dlb.left(),
            ulf = ulb.left(),
            ul = lb.left(),
            lf = ul.left(),
            dl = lf.left(),
            lb = dl.left(),
        )

    override fun leftInv(): Cube =
        copy(
            ulb = ulf.leftInv(),
            dlb = ulb.leftInv(),
            dlf = dlb.leftInv(),
            ulf = dlf.leftInv(),
            lb = ul.leftInv(),
            dl = lb.leftInv(),
            lf = dl.leftInv(),
            ul = lf.leftInv(),
        )

    override fun middle(): Cube =
        copy(
            ub = uf.middle(),
            db = ub.middle(),
            df = db.middle(),
            uf = df.middle(),
        )

    override fun middleInv(): Cube =
        copy(
            df = uf.middleInv(),
            db = df.middleInv(),
            ub = db.middleInv(),
            uf = ub.middleInv(),
        )

    override fun right(): Cube =
        copy(
            drb = urb.right(),
            drf = drb.right(),
            urf = drf.right(),
            urb = urf.right(),
            ur = rf.right(),
            rb = ur.right(),
            dr = rb.right(),
            rf = dr.right(),
        )

    override fun rightInv(): Cube =
        copy(
            urb = drb.rightInv(),
            urf = urb.rightInv(),
            drf = urf.rightInv(),
            drb = drf.rightInv(),
            dr = rf.rightInv(),
            rb = dr.rightInv(),
            ur = rb.rightInv(),
            rf = ur.rightInv(),
        )
}

fun unscrambled(): Cube =
    Cube(
        ulf = c(up = Up, front = Front, left = Left),
        ul = c(up = Up, left = Left),
        ulb = c(up = Up, left = Left, back = Back),
        ub = c(up = Up, back = Back),
        urb = c(up = Up, back = Back, right = Right),
        ur = c(up = Up, right = Right),
        urf = c(up = Up, right = Right, front = Front),
        uf = c(up = Up, front = Front),
        lf = c(front = Front, left = Left),
        lb = c(back = Back, left = Left),
        rb = c(back = Back, right = Right),
        rf = c(front = Front, right = Right),
        dlf = c(down = Down, front = Front, left = Left),
        dl = c(down = Down, left = Left),
        dlb = c(down = Down, left = Left, back = Back),
        db = c(down = Down, back = Back),
        drb = c(down = Down, back = Back, right = Right),
        dr = c(down = Down, right = Right),
        drf = c(down = Down, right = Right, front = Front),
        df = c(down = Down, front = Front),
    )

fun turn(cube: Cube, turn: String?): Cube = with(cube) {
    when (turn) {
        "B" -> back()
        "B'" -> backInv()
        "D" -> down()
        "D'" -> downInv()
        "E" -> equator()
        "E'" -> equatorInv()
        "F" -> front()
        "F'" -> frontInv()
        "L" -> left()
        "L'" -> leftInv()
        "M" -> middle()
        "M'" -> middleInv()
        "R" -> right()
        "R'" -> rightInv()
        "S" -> standing()
        "S'" -> standingInv()
        "U" -> up()
        "U'" -> upInv()
//        "x" -> performMoves(listOf("R", "M'", "L'"))
//        "x'" -> performMoves("R' M L")
//        "y" -> performMoves("U E' D'")
//        "y'" -> performMoves("U' E D")
//        "z" -> performMoves("F S B'")
//        "z'" -> performMoves("F' S' B")
        else -> error("I can't $turn here")
    }
}
