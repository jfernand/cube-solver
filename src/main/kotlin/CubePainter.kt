
import cube.Cube
import cube.CubeColor
import cube.CubeColor.*
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Image
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.image.BufferedImage
import java.util.*
import javax.imageio.ImageIO
import javax.swing.BorderFactory
import javax.swing.ImageIcon
import javax.swing.JButton
import javax.swing.JComboBox
import javax.swing.JPanel
import javax.swing.JSlider
import javax.swing.JTextField
import javax.swing.Timer
import javax.swing.event.ChangeEvent
import javax.swing.event.ChangeListener

class CubePainter : JPanel(), ActionListener, ChangeListener, MouseListener {
    //Default scramble
    private val DEFAULT_SCRAMBLE = "F2 D' B U' D L2 B2 R B L' B2 L2 B2 D' R2 F2 D' R2 U' "

    private var scramble: String = (DEFAULT_SCRAMBLE)

    //Allows for toggling between modes when updateMode() is invoked
    private var mode = String()
    private var colorSelected //The color selected while in color input mode
        : Char
    private var sideChosen //The side for which the user is entering colors
        : Char
    private var instructions //Colors for instructions to display during color input mode
        : Array<String?>

    //Whether a solution is currently being displayed
    private var inSolution: Boolean
    private var cube = Cube()
    private var sunflower: List<String> = listOf()
    private var whiteCross: List<String> = listOf()
    private var whiteCorners: List<String> = listOf()
    private var secondLayer: List<String> = listOf()
    private var yellowCross: List<String> = listOf()
    private var OLL: List<String> = listOf()
    private var PLL: List<String> = listOf()
    private var movesToPerform: List<Pair<String, String>> = mutableListOf()
    private var currentMove = 0
    private var movesPerformed = String()

    //Buttons to start and stop animation; to reset the scramble based on text field
    private val start = makeStartButton()
    private val stop = makeStopButton()
    private val applyScramble = makeScrambleButton()

    private val randomize = makeRandomizeButton()

    private val skip = makeSkipButton()

    private var rewind = makeRewindButton()

    /*
     * Respective stages of the solution w.r.t the phase variable
     * 0 = sunflower
     * 1 = whiteCross
     * 2 = whiteCorners
     * 3 = secondLayer
     * 4 = yellowCross
     * 5 = OLL
     * 6 = PLL
     * The phase is updated in updatePhase() to reflect the stage at which the solution is
     */
    private var phase = 0
    private var phaseString: String

    //Helps keep track of moves to perform, and allows for painting of moves
    private var movesIndex = 0

    //Buttons used during the color input phase to either reset the colors or proceed with the input colors
    //to the solution
    private val resetCubeInputs = makeResetInputs()

    private val setInputs = makeSetInputsButton()

    //Slider to control animation speed
    private val animSpeed = makeAnimationSlider()

    //Allows User to choose which side's colors to enter during color input mode
    private val sideChooser = makeSideChooser()

    //Text field to allow user to input a custom scramble different from the default scramble
    private val inputScramble = makeInputScrambleButton(DEFAULT_SCRAMBLE)

    //Timer to control delay between animation of moves
    private val frameTimer: Timer

    /*
     * colorsInput[0] = left colors
     * colorsInput[1] = up colors
     * colorsInput[2] = front colors
     * colorsInput[3] = back colors
     * colorsInput[4] = right colors
     * colorsInput[5] = down colors
     */
    private val colorsInput //Holds all input colors
        : Array<Array<CharArray>>
    private val colors: Array<Array<Array<CubeColor>>>

    /**
     * Returns the preferred dimensions of the CubePainter as a Dimension object.
     *
     * @return default dimensions of CubePainter
     */
    override fun getPreferredSize(): Dimension {
        return Dimension(700, 770)
    }

    /**
     * Resets the colors inputed in color selection mode to the colors of a cube in its solved state.
     */
    private fun resetCubeInputs() {
        for (i in 0..2) {
            Arrays.fill(colorsInput[0][i], 'R')
            Arrays.fill(colorsInput[1][i], 'Y')
            Arrays.fill(colorsInput[2][i], 'G')
            Arrays.fill(colorsInput[3][i], 'B')
            Arrays.fill(colorsInput[4][i], 'O')
            Arrays.fill(colorsInput[5][i], 'W')
        }
    }

    private fun _resetCubeInputs() {
        for (i in 0..2) {
            Arrays.fill(colors[0][i], Red)
            Arrays.fill(colors[1][i], Yellow)
            Arrays.fill(colors[2][i], Green)
            Arrays.fill(colors[3][i], Blue)
            Arrays.fill(colors[4][i], Orange)
            Arrays.fill(colors[5][i], White)
        }
    }

    /**
     * Initializes all the buttons, sliders, combo boxes, and text fields for the user to interact with.
     * Helper methods for constructor.
     */
    private fun initializeComponents() {
        add(start)
        add(stop)
        add(skip)
        add(rewind)
        add(animSpeed)
        add(inputScramble)
        add(applyScramble)
        add(randomize)
        add(sideChooser)
        add(resetCubeInputs)
        add(setInputs)
    }

    private fun makeSetInputsButton(): JButton {
        val button = JButton("PROCEED")
        button.font = Font("Courier", Font.PLAIN, 15)
        button.setLocation(300, 650)
        button.setSize(100, 30)
        button.addActionListener(this)
        button.isVisible = false
        button.isEnabled = false
        button.addActionListener {
            frameTimer.stop()
            //While the cube is being scrambled, screen will show nonsensical colors, such as black, so set as invisible
            isVisible = false
            cube.setAllColors(colorsInput)
            resetScrambleByColorInputs()
            inSolution = true
            updateElements()
            repaint()
            isVisible = true
        }
        return button
    }

    private fun makeResetInputs(): JButton {
        val button = JButton("RESET")
        button.font = Font("Courier", Font.PLAIN, 15)
        button.setLocation(200, 650)
        button.setSize(100, 30)
        button.addActionListener(this)
        button.isVisible = false
        button.isEnabled = false
        button.addActionListener {
            resetCubeInputs()
            _resetCubeInputs()
            repaint()
        }
        return button
    }

    private fun makeSideChooser(): JComboBox<String> {
        val sideChooser = JComboBox(arrayOf("Left", "Up", "Back", "Front", "Right", "Down"))
        sideChooser.setLocation(270, 50)
        sideChooser.setSize(100, 30)
        sideChooser.addActionListener {
            sideChosen = (sideChooser.selectedItem as String)[0]
            instructions = getInstructions()
            repaint()
        }
        sideChooser.isVisible = false
        sideChooser.isEnabled = false
        return sideChooser
    }

    private fun makeRandomizeButton(): JButton {
        val button = JButton("RANDOM")
        button.font = Font("Courier", Font.PLAIN, 15)
        button.setLocation(590, 70)
        button.setSize(100, 20)
        button.addActionListener {
            cube = Cube()
            val scramble = randScramble()
            cube.scramble(scramble)
            inputScramble.text = scramble
            isVisible = false
            resetScramble(inputScramble.text)
            inSolution = true
            updateElements()
            repaint()
            isVisible = true
        }
        return button
    }

    private fun makeRewindButton(): JButton {
        var icon2 = ImageIcon()
        try {
            var img2: Image = ImageIO.read(javaClass.getResource("resources/Rewind.png"))
            img2 = img2.getScaledInstance(25, 25, Image.SCALE_SMOOTH)
            icon2 = ImageIcon(img2)
        } catch (ex: Exception) {
            println(ex)
        }
        val rewind = JButton(icon2)
        rewind.setLocation(210, 8)
        rewind.setSize(icon2.iconWidth, icon2.iconHeight)
        rewind.background = background
        rewind.border = null
        rewind.addActionListener {
//            var flag = false
//            val prevIndex = movesIndex
//            while (movesIndex > 1 && !flag) {
//                movesIndex--
//                if (movesToPerform.substring(movesIndex - 1, movesIndex) == " ") {
//                    flag = !flag
//                }
//                println(movesIndex)
//            }
//            if (movesIndex == 1) {
//                movesIndex = 0
//            }
//            movesPerformed = movesToPerform.substring(0, movesIndex)
//            if (movesPerformed.length >= 35) {
//                movesPerformed = movesPerformed.substring(movesPerformed.length - 33)
//            }
//            cube.reverseMoves(movesToPerform.substring(movesIndex, prevIndex))
//            repaint()
        }
        return rewind
    }

    private fun makeAnimationSlider(): JSlider {
        val animSpeed = JSlider(1, 10)
        animSpeed.value = 1 //Slider values range from 1 to 10
        animSpeed.minorTickSpacing = 1
        animSpeed.paintTicks = true
        animSpeed.snapToTicks = true
        animSpeed.setLocation(500, 0)
        animSpeed.setSize(200, 40)
        animSpeed.addChangeListener {
            frameTimer.delay = DELAY * (11 - animSpeed.value)
        }
        return animSpeed
    }

    private fun makeStopButton(): JButton {
        val button = JButton("Stop")
        button.font = Font("Courier", Font.PLAIN, 15)
        button.setLocation(130, 10)
        button.setSize(60, 20)
        button.addActionListener {
            frameTimer.stop()
        }
        return button
    }

    private fun makeSkipButton(): JButton {
        val icon1 = try {
            var img1: Image = ImageIO.read(javaClass.getResource("resources/Skip.png"))
            img1 = img1.getScaledInstance(25, 25, Image.SCALE_SMOOTH)
            ImageIcon(img1)
        } catch (ex: Exception) {
            println(ex)
            ImageIcon()
        }
        val skip = JButton(icon1)
        skip.setLocation(240, 8)
        skip.setSize(icon1.iconWidth, icon1.iconHeight)
        skip.background = background
        skip.border = null
        skip.addActionListener {
            movesToPerform = performNextMove(movesToPerform)
            repaint()
        }
        return skip
    }

    private fun makeInputScrambleButton(scramble: String): JTextField {
        val field = JTextField(scramble)
        field.setLocation(170, 40)
        field.setSize(400, 40)
        field.isFocusable = true
        field.border = BorderFactory.createLineBorder(Color.black)
        field.font = Font("Courier", Font.PLAIN, 15)
        return field
    }

    private fun makeStartButton(): JButton {
        val button = JButton("Start")
        button.font = Font("Courier", Font.PLAIN, 15)
        button.setLocation(50, 10)
        button.setSize(60, 20)
        button.addActionListener {
            frameTimer.start()
        }
        return button
    }

    private fun makeScrambleButton(): JButton {
        val button = JButton("APPLY")
        button.font = Font("Courier", Font.PLAIN, 15)
        button.setLocation(590, 40)
        button.setSize(100, 20)
        button.addActionListener {
            frameTimer.stop()
            //While the cube is being scrambled, screen will show nonsensical colors, such as black, so set as invisible
            isVisible = false
            resetScramble(inputScramble.text)
            inSolution = true
            updateElements()
            repaint()
            isVisible = true
        }
        return button
    }

    /**
     * Resets the scramble that is to be applied on the cube based on the input.
     * Determines the moves to be performed to solve the cube as well.
     *
     * @param s: the scramble to be applied
     */
    private fun resetScramble(s: String) {
        scramble = s
        cube = Cube()
        cube.scramble(scramble)
        sunflower = cube.makeSunflower()
        whiteCross = cube.makeWhiteCross()
        whiteCorners = cube.finishWhiteLayer()
        secondLayer = cube.insertAllEdges()
        yellowCross = cube.makeYellowCross()
        OLL = cube.orientLastLayer()
        PLL = cube.permuteLastLayer()
        movesToPerform = prepareMoves()
        movesPerformed = String()
        cube = Cube()
        cube.scramble(scramble)
        //If the cube is being scrambled newly after initializing is complete and animation has begun,
        //be sure to reset all reference indexes
        movesIndex = 0
        phase = 0
        phaseString = "Sunflower"
        repaint()
    }

    private fun prepareMoves() =
        (sunflower.map { "Sunflower" to it }
            + mapPhase(whiteCross,"White Cross")
            + mapPhase(whiteCorners,"White Corners")
            + mapPhase(secondLayer,"Second Layer")
            + mapPhase(yellowCross, "Yellow Cross")
            + mapPhase(OLL,"OLL")
            + mapPhase(PLL,"OLL")
            ).toMutableList()

    private fun mapPhase(list:List<String>, phase:String) = list.map { phase to it }

    private fun performNextMove(movesToPerform: List<Pair<String, String>>): List<Pair<String, String>> {
        if (movesToPerform.isEmpty()) {
            phaseString = "Solved"
            frameTimer.stop()
            return listOf()
        }
        val (phase, move) = movesToPerform[0]
        phaseString = phase
        when {
            move.isDouble() -> {
                cube.turn(naked(move))
                cube.turn(naked(move))
            }
            else -> cube.turn(move)
        }
        return movesToPerform.drop(1)
    }

    private fun updatePhase(phase: Int) {
        when (phase) {
            1 -> {
                phaseString = "White Cross"
            }
            2 -> {
                phaseString = "White Corners"
            }
            3 -> {
                phaseString = "Second Layer"
            }
            4 -> {
                phaseString = "Yellow Cross"
            }
            5 -> {
                phaseString = "OLL"
            }
            6 -> {
                phaseString = "PLL"
            }
            7 -> {
                phaseString = "Solved"
                frameTimer.stop()
            }
        }
    }

    /**
     * After updating the phase (if necessary), performs the next move in the String movesToPerform
     * and updates movesPerformed.
     */
//    private fun performNextMove() {
//        updatePhase()
//
//        //Get to a character that is not a space
//        while (movesIndex < movesToPerform.length - 1 && movesToPerform.substring(movesIndex, movesIndex + 1)
//                .compareTo(" ") == 0) {
//            movesIndex++
//        }
//        //Same logic as in Cube class's performMoves() method
//        if (movesToPerform.isNotEmpty() && movesToPerform.substring(movesIndex, movesIndex + 1) !== " ") {
//            if (movesIndex != movesToPerform.length - 1) {
//                when {
//                    movesToPerform.substring(movesIndex + 1, movesIndex + 2).compareTo("2") == 0 -> {
//                        //Turning twice ex. U2
//                        cube.turn(movesToPerform.substring(movesIndex, movesIndex + 1))
//                        cube.turn(movesToPerform.substring(movesIndex, movesIndex + 1))
//                        movesIndex++
//                    }
//                    movesToPerform.substring(movesIndex + 1, movesIndex + 2).compareTo("'") == 0 -> {
//                        //Making a counterclockwise turn ex. U'
//                        cube.turn(movesToPerform.substring(movesIndex, movesIndex + 2))
//                        movesIndex++
//                    }
//                    else -> {
//                        //Clockwise turn
//                        cube.turn(movesToPerform.substring(movesIndex, movesIndex + 1))
//                    }
//                }
//            } else {
//                //Clockwise turn
//                cube.turn(movesToPerform.substring(movesIndex, movesIndex + 1))
//            }
//        }
//        movesIndex++
//        //Append the moves performed onto the end of movesPerformed
//        if (movesToPerform.isNotEmpty()) {
//            movesPerformed = movesToPerform.substring(0, movesIndex)
//        }
//        //Ensure that movesPerformed does not overflow out of the graphical interface
//        if (movesPerformed.length >= 35) {
//            movesPerformed = movesPerformed.substring(movesPerformed.length - 33)
//        }
//    }
    /**
     * Paints the JPanel. Upon initialization, paints the buttons, sliders, and text field which
     * the user can interact with. When repaint() is called, the main changes that will be visible
     * are changes to the cube, moves to be performed, and moves already performed. For painting the cube, this method
     * invokes the getColors() method from Cube to retrieve all colors, and after painting those colors,
     * paints an outline around the cubies.
     */
    /**
     * Updates the current phase of the solution as necessary
     * Respective stages of the solution w.r.t the phase variable
     * 0 = sunflower		 	1 = whiteCross		2 = whiteCorners		3 = secondLayer
     * 4 = yellowCross		5 = OLL				6 = PLL
     */
//    private fun updatePhase() {
//        if (movesIndex >= movesToPerform.length) {
//            when (phase) {
//                0 -> {
//                    movesToPerform = whiteCross
//                    phaseString = "White Cross"
//                }
//                1 -> {
//                    movesToPerform = whiteCorners
//                    phaseString = "White Corners"
//                }
//                2 -> {
//                    movesToPerform = secondLayer
//                    phaseString = "Second Layer"
//                }
//                3 -> {
//                    movesToPerform = yellowCross
//                    phaseString = "Yellow Cross"
//                }
//                4 -> {
//                    movesToPerform = OLL
//                    phaseString = "OLL"
//                }
//                5 -> {
//                    movesToPerform = PLL
//                    phaseString = "PLL"
//                }
//                6 -> {
//                    movesToPerform = " "
//                    phaseString = "Solved"
//                    phase--
//                    frameTimer.stop()
//                }
//            }
//            phase++
//            movesIndex = 0
//        }
//    }

//    fun prepareMoves(): MutableList<Pair<String, String>> {
//        val moves = mutableListOf<Pair<Int, String>>()
//        moves.addAll(whiteCross.trim().split(" ").map { 0 to it })
//        moves.addAll(whiteCorners.trim().split(" ").map { 1 to it })
//        moves.addAll(secondLayer.trim().split(" ").map { 2 to it })
//        moves.addAll(yellowCross.trim().split(" ").map { 3 to it })
//        moves.addAll(OLL.trim().split(" ").map { 4 to it })
//        moves.addAll(PLL.trim().split(" ").map { 5 to it })
//        return moves
//    }

    //    /**
//     * Takes actions performed on the buttons to cause changes in the animations or resetting the cube.
//     */
    override fun actionPerformed(e: ActionEvent) {
//        when {
//            e.source === start -> {
//                frameTimer.start()
//            }
//            e.source === stop -> {
//                frameTimer.stop()
//            }
//            e.source === skip -> {
//                performNextMove()
//                repaint()
//            }
//            e.source === rewind -> {
//                var flag = false
//                val prevIndex = movesIndex
//                while (movesIndex > 1 && !flag) {
//                    movesIndex--
//                    if (movesToPerform.substring(movesIndex - 1, movesIndex) == " ") {
//                        flag = !flag
//                    }
//                    println(movesIndex)
//                }
//                if (movesIndex == 1) {
//                    movesIndex = 0
//                }
//                movesPerformed = movesToPerform.substring(0, movesIndex)
//                if (movesPerformed.length >= 35) {
//                    movesPerformed = movesPerformed.substring(movesPerformed.length - 33)
//                }
//                cube.reverseMoves(movesToPerform.substring(movesIndex, prevIndex))
//                repaint()
//            }
//            e.source === sideChooser -> {
//                sideChosen = (sideChooser!!.selectedItem as String)[0]
//                instructions = getInstructions()
//                repaint()
//            }
//            e.source === applyScramble -> {
//                frameTimer.stop()
//                //While the cube is being scrambled, screen will show nonsensical colors, such as black, so set as invisible
//                isVisible = false
//                resetScramble(inputScramble!!.text)
//                inSolution = true
//                updateElements()
//                repaint()
//                isVisible = true
//            }
//            e.source === randomize -> {
//                cube = Cube()
//                val scramble = randScramble()
//                cube.scramble(scramble)
//                inputScramble.text = scramble
//                isVisible = false
//                resetScramble(inputScramble.text)
//                inSolution = true
//                updateElements()
//                repaint()
//                isVisible = true
//            }
//            e.source === resetCubeInputs -> {
//                resetCubeInputs()
//                repaint()
//            }
//            e.source === setInputs -> {
//                frameTimer.stop()
//                //While the cube is being scrambled, screen will show nonsensical colors, such as black, so set as invisible
//                isVisible = false
//                cube.setAllColors(colorsInput)
//                resetScrambleByColorInputs()
//                inSolution = true
//                updateElements()
//                repaint()
//                isVisible = true
//            }
//        }
    }

    /**
     * Retrieves the colors of the faces to be printed in the instructions in the paintComponent() method.
     * If String[] colors = getInstructions(), color[0] is the color to hold on top, colors[1] is the color
     * to hold in the back, and colors[2] is the color to hold in front.
     *
     * @return
     */
    private fun getInstructions(): Array<String?> {
        val colors = arrayOfNulls<String>(3)
        when (sideChosen) {
            'L' -> {
                colors[0] = "Red"
                colors[1] = "Yellow"
                colors[2] = "White"
            }
            'U' -> {
                colors[0] = "Yellow"
                colors[1] = "Blue"
                colors[2] = "Green"
            }
            'F' -> {
                colors[0] = "Green"
                colors[1] = "Yellow"
                colors[2] = "White"
            }
            'B' -> {
                colors[0] = "Blue"
                colors[1] = "Yellow"
                colors[2] = "White"
            }
            'R' -> {
                colors[0] = "Orange"
                colors[1] = "Yellow"
                colors[2] = "White"
            }
            'D' -> {
                colors[0] = "White"
                colors[1] = "Green"
                colors[2] = "Blue"
            }
        }
        return colors
    }

    /**
     * Updates the UI elements that the user can interact with depending on the current mode and whether
     * a solution is being played.
     */
    private fun updateElements() {
        if (mode == TEXT_SCRAMBLE) {
            start.isEnabled = true
            start.isVisible = true
            stop.isEnabled = true
            stop.isVisible = true
            animSpeed.isEnabled = true
            animSpeed.isVisible = true
            inputScramble.isEnabled = true
            inputScramble.isVisible = true
            applyScramble.isEnabled = true
            applyScramble.isVisible = true
            skip.isEnabled = true
            skip.isVisible = true
            rewind.isEnabled = true
            rewind.isVisible = true
            randomize.isEnabled = true
            randomize.isVisible = true

            //Disable all components specific to color selection mode
            sideChooser.isVisible = false
            sideChooser.isEnabled = false
            resetCubeInputs.isVisible = false
            resetCubeInputs.isEnabled = false
            setInputs.isVisible = false
            setInputs.isEnabled = false
        } else if (mode == COLOR_SELECTION) {
            if (inSolution) {
                start.isEnabled = true
                start.isVisible = true
                stop.isEnabled = true
                stop.isVisible = true
                animSpeed.isEnabled = true
                animSpeed.isVisible = true
                skip.isEnabled = true
                skip.isVisible = true
                rewind.isEnabled = true
                rewind.isVisible = true
                randomize.isEnabled = false
                randomize.isVisible = false
                sideChooser.isVisible = false
                sideChooser.isEnabled = false
                resetCubeInputs.isVisible = false
                resetCubeInputs.isEnabled = false
                setInputs.isVisible = false
                setInputs.isEnabled = false
            } else if (!inSolution) {
                start.isEnabled = false
                start.isVisible = false
                stop.isEnabled = false
                stop.isVisible = false
                animSpeed.isEnabled = false
                animSpeed.isVisible = false
                randomize.isEnabled = false
                randomize.isVisible = false
                skip.isEnabled = false
                skip.isVisible = false
                rewind.isEnabled = false
                rewind.isVisible = false
                sideChooser.isVisible = true
                sideChooser.isEnabled = true
                resetCubeInputs.isVisible = true
                resetCubeInputs.isEnabled = true
                setInputs.isVisible = true
                setInputs.isEnabled = true
            }
            //Disable all components specific to text scramble mode
            inputScramble.isEnabled = false
            inputScramble.isVisible = false
            applyScramble.isEnabled = false
            applyScramble.isVisible = false
        }
    }

    /**
     * After the user inputs their desired colors in color selection mode, pressing the setInputs button
     * will invoke this method, acquiring the required moves necessary to solve the cube. The cube is restored back to
     * the scrambled state after the solution moves are acquired.
     */
    private fun resetScrambleByColorInputs() {
        cube.setAllColors(colorsInput)
        sunflower = cube.makeSunflower()
        whiteCross = cube.makeWhiteCross()
        whiteCorners = cube.finishWhiteLayer ()
        secondLayer = cube.insertAllEdges()
        yellowCross = cube.makeYellowCross()
        OLL = cube.orientLastLayer()
        PLL = cube.permuteLastLayer()
        movesToPerform = prepareMoves()
        movesPerformed = String()
        movesIndex = 0
        phase = 0
        phaseString = "Sunflower"
        cube.setAllColors(colorsInput) //Reset the cube to scrambled state
        repaint()
    }

    /**
     * Takes a change of input from the slider to adjust the frame rate accordingly.
     */
    override fun stateChanged(e: ChangeEvent) {
        if (e.source === animSpeed) {
            frameTimer.delay = DELAY * (11 - animSpeed.value)
        }
    }

    /**
     * Paints the JPanel. Upon initialization, paints the buttons, sliders, and text field which
     * the user can interact with. When repaint() is called, the main changes that will be visible
     * are changes to the cube, moves to be performed, and moves already performed. For painting the cube, this method
     * invokes the paintComponent() method from Cube to retrieve all colors, and after painting those colors,
     * paints an outline around the cubies.
     */
    public override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        if (mode == TEXT_SCRAMBLE) {
            g.font = Font("Courier", Font.PLAIN, 25)
            g.drawString("Scramble: ", 30, 70)
        }
        if (!inSolution) {
            paintCubeEditor(g)
        } else if (inSolution) {
            paintSolutionPhase(g)
        }
    }

//    private fun paintSolutionPhase(g: Graphics) {
//        //Display the phase of a solution
//        g.font = Font("Courier", Font.PLAIN, 25)
//        g.drawString("Phase: $phaseString", 30, 120)
//        g.font = Companion.font
//        g.color = Color.RED
//        g.drawString(movesPerformed, 50, 700) //Draw the moves that have already been performed
//        //Draw the moves that are yet to be performed
//        g.color = Color.BLACK
//        if (movesIndex <= movesToPerform.length - 1) { //Avoid index out of bounds error
//            if (movesToPerform.substring(movesIndex).length >= 33) {
//                g.drawString(movesToPerform.substring(movesIndex, movesIndex + 33), 40, 650)
//            } else {
//                g.drawString(movesToPerform.substring(movesIndex), 40, 650)
//            }
//        }
//
//        //Paint the cube itself now
//        (g as Graphics2D).stroke = s
//        cube.paintComponent(g)
//    }

    private fun paintSolutionPhase(g: Graphics) {
        //Display the phase of a solution
        g.font = Font("Courier", Font.PLAIN, 25)
        g.drawString("Phase: $phaseString", 30, 120)
        g.font = Companion.font
        g.color = Color.RED
        g.drawString(movesPerformed, 50, 700) //Draw the moves that have already been performed
        //Draw the moves that are yet to be performed
        g.color = Color.BLACK

        g.drawString(movesToPerform.take(33).map{it.second}.joinToString(" "), 40, 650)

        //Paint the cube itself now
        (g as Graphics2D).stroke = s
        cube.paintComponent(g)
    }

    private fun paintCubeEditor(g: Graphics) {
        //Paint the color selection boxes
        (g as Graphics2D).stroke = s
        var xVal = 100
        var yVal = 450
        for (i in 0..5) {
            when (i) {
                0 -> g.setColor(Color.RED)
                1 -> g.setColor(Color.GREEN)
                2 -> g.setColor(Color.BLUE)
                3 -> g.setColor(Color.YELLOW)
                4 -> g.setColor(Color.ORANGE)
                5 -> g.setColor(Color.WHITE)
            }
            g.fillRect(xVal, yVal, CUBIE_SIZE, CUBIE_SIZE)
            g.setColor(Color.BLACK)
            g.drawRect(xVal, yVal, CUBIE_SIZE, CUBIE_SIZE)
            xVal += CUBIE_SIZE * 1.5.toInt()
        }

        //Paint the chosen cube side
        xVal = 250
        yVal = 200
        val sideColors = colorsInput[getIndexOfSide(sideChosen)]
        for (i in 0..2) {
            for (j in 0..2) {
                g.setColor(getColor(sideColors[i][j]))
                g.fillRect(xVal + j * CUBIE_SIZE, yVal + i * CUBIE_SIZE, CUBIE_SIZE, CUBIE_SIZE)
                g.setColor(Color.BLACK)
                g.drawRect(xVal + j * CUBIE_SIZE, yVal + i * CUBIE_SIZE, CUBIE_SIZE, CUBIE_SIZE)
            }
        }

        val _sideColors = colors[getIndexOfSide(sideChosen)]
        for (i in 0..2) {
            for (j in 0..2) {
                g.setColor(getColor(_sideColors[i][j]))
                g.fillRect(xVal + j * CUBIE_SIZE, yVal + i * CUBIE_SIZE, CUBIE_SIZE, CUBIE_SIZE)
                g.setColor(Color.BLACK)
                g.drawRect(xVal + j * CUBIE_SIZE, yVal + i * CUBIE_SIZE, CUBIE_SIZE, CUBIE_SIZE)
            }
        }

        //Paint the instructions for holding the cube
        g.setColor(Color.BLACK)
        g.drawString(
            "Hold the cube such that " + instructions[0] + " is facing up, " +
                instructions[1] + " is to the back, and " + instructions[2] + " is in front.",
            50, 130
        )
        g.drawString(
            "Enter the top colors.",
            50, 150
        )

        //Paint the color that is selected so user is sure to paint correct color
        g.setFont(Companion.font)
        g.drawString("Selected Color:", 100, 500 + CUBIE_SIZE * 2)
        g.setColor(getColor(colorSelected))
        g.fillRect(400, 465 + CUBIE_SIZE * 2, CUBIE_SIZE, CUBIE_SIZE)
        g.setColor(Color.BLACK)
        g.drawRect(400, 465 + CUBIE_SIZE * 2, CUBIE_SIZE, CUBIE_SIZE)
    }

    /**
     * Gets the index for colorsInputed[(index here)] that corresponds to the side currently being painted when in color
     * selection mode. Helper method for paintComponent().
     *
     * @param side
     * @return index
     */
    private fun getIndexOfSide(side: Char): Int {
        when (side) {
            'L' -> return 0
            'U' -> return 1
            'F' -> return 2
            'B' -> return 3
            'R' -> return 4
            'D' -> return 5
        }
        return 6
    }

    /**
     * Returns the appropriate Color object based on a cubie's color for appropriate
     * painting in the paintComponent() method.
     *
     * @param color: cubie color
     * @return corresponding Color object
     */
    private fun getColor(color: Char): Color {
        when (color) {
            'W' -> return Color.WHITE
            'Y' -> return Color.YELLOW
            'B' -> return Color.BLUE
            'G' -> return Color.GREEN
            'R' -> return Color.RED
            'O' -> return Color.ORANGE
        }
        return Color.BLACK
    }

    private fun getColor(color: CubeColor): Color {
        return when (color) {
            White -> Color.WHITE
            CubeColor.Yellow -> Color.YELLOW
            Blue -> Color.BLUE
            Green -> Color.GREEN
            Red -> Color.RED
            Orange -> Color.ORANGE
            else -> Color.BLACK
        }
    }

    /**
     * Updates the mode to either text scramble or color selection mode based on the parameter.
     *
     * @param str the mode to change to
     */
    fun updateMode(str: String) {
        if (mode != str) {
            mode = (str)
            cube = Cube()
            if (mode == TEXT_SCRAMBLE) {
                scramble = DEFAULT_SCRAMBLE
                resetScramble(scramble)
                inSolution = true
            }
            updateElements()
            repaint()
        }
    }

    /**
     * Sets `inSolution` to the parameter, determining whether a solution is to be displayed.
     *
     * @param inSoln whether mode should be switched to being in a solution or not
     */
    fun setInSolution(inSoln: Boolean) {
        inSolution = inSoln
    }

    /**
     * Takes in mouse inputs during color selection mode for selecting and inputting colors
     */
    override fun mouseClicked(e: MouseEvent) {
        mousePressed(e)
    }

    /**
     * Takes in mouse inputs during color selection mode for selecting and inputting colors
     */
    override fun mousePressed(e: MouseEvent) {
        if (mode == COLOR_SELECTION && !inSolution) {
            if (e.y > 200 && e.y < 200 + CUBIE_SIZE * 3) {
                val i = (e.y - 200) / CUBIE_SIZE
                val j = (e.x - 250) / CUBIE_SIZE
                colorsInput[getIndexOfSide(sideChosen)][i][j] = colorSelected
                repaint()
            } else if (e.y > 450 && e.y < 450 + CUBIE_SIZE) {
                val image = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
                val g2 = image.createGraphics()
                paint(g2)
                val color = image.getRGB(e.x, e.y)
                g2.dispose()
                when (color) {
                    -65536 -> colorSelected = 'R'
                    -16711936 -> colorSelected = 'G'
                    -16776961 -> colorSelected = 'B'
                    -256 -> colorSelected = 'Y'
                    -14336 -> colorSelected = 'O'
                    -1 -> colorSelected = 'W'
                }
                repaint()
            }
        }
    }

    override fun mouseReleased(e: MouseEvent) {}
    override fun mouseEntered(e: MouseEvent) {}
    override fun mouseExited(e: MouseEvent) {}

    companion object {
        //Standard frame rate delay
        const val DELAY = 100
        const val TEXT_SCRAMBLE = "Text Scramble"
        const val COLOR_SELECTION = "Color Selection"

        //Stroke for bold outline along edges of cubie colors
        val s = BasicStroke(
            1.0f, BasicStroke.CAP_BUTT,
            BasicStroke.JOIN_MITER, 10.0f
        )
        const val CUBIE_SIZE = 50

        private val font = Font("Courier", Font.PLAIN, 30)
    }

    /**
     * Initializes all elements of the CubePainter JPanel with which the user can interact.
     * This includes all buttons, sliders, and text fields.
     */
    init {
        layout = null //Allows for manually setting locations of components
        size = preferredSize
        ignoreRepaint = true
        isVisible = true
        mode = TEXT_SCRAMBLE
        inSolution = true
        phaseString = "Sunflower"
        colorSelected = 'R'
        instructions = arrayOf("Red", "Yellow", "White")
        sideChosen = 'L'
        colorsInput = Array(6) { Array(3) { CharArray(3) } }
        colors = Array(6) { Array(3) { arrayOf(None, None, None) } }
        _resetCubeInputs()
        resetCubeInputs()
        addMouseListener(this)

        //Initialize all buttons, sliders and text fields
        initializeComponents()
        resetScramble(inputScramble.text)
        //Initialize the frame timer
        frameTimer = Timer(DELAY * (11 - animSpeed.value)) {
            if (inSolution) {
                movesToPerform = performNextMove(movesToPerform)
                repaint()
            }
        }
    }
}
