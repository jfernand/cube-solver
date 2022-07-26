import java.awt.BorderLayout
import java.awt.EventQueue
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JFrame
import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem

fun main(args: Array<String>) {
    EventQueue.invokeLater { CubeDisplayer() }
}

class CubeDisplayer : JFrame(), ActionListener {
    private var cubePainter //The JPanel that will handle painting and user input
        : CubePainter
    var menuBar: JMenuBar
    var modes: JMenu
    private var colorSelection: JMenuItem
    private var scramble: JMenuItem

    /**
     * Toggles between color selection mode and text scramble mode in the cubePainter instance.
     */
    override fun actionPerformed(e: ActionEvent) {
        if (e.source === colorSelection) {
            cubePainter.setInSolution(false)
            cubePainter.updateMode(CubePainter.COLOR_SELECTION)
        } else if (e.source === scramble) {
            cubePainter.setInSolution(true)
            cubePainter.updateMode(CubePainter.TEXT_SCRAMBLE)
        }
    }

    //JMenuItem colorSelection, scramble;
    /**
     * Creates a new CubeDisplayer and initializes it with a new CubePainter for the user
     * to interact with.
     */
    init {
        title = "Cube Displayer"
        layout = BorderLayout()
        setSize(700, 770)
        defaultCloseOperation = EXIT_ON_CLOSE
        isResizable = false
        ignoreRepaint = true
        menuBar = JMenuBar()
        modes = JMenu("Mode Selection")
        colorSelection = JMenuItem("Color Selection Mode")
        scramble = JMenuItem("Text Scramble Mode")
        modes.add(colorSelection)
        modes.add(scramble)
        colorSelection.addActionListener(this)
        scramble.addActionListener(this)
        menuBar.add(modes)
        jMenuBar = menuBar

        //Create a new CubePainter JPanel
        cubePainter = CubePainter()
        add(cubePainter)
        cubePainter.isVisible = true
        cubePainter.isEnabled = true
        menuBar.isVisible = true
        isVisible = true
        this.repaint()
    }
}
