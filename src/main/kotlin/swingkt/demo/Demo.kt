package swingkt.demo

import swingkt.button
import swingkt.layouts.BorderPanel
import swingkt.layouts.Flexcol
import swingkt.style.Style
import swingkt.style.applyStyle
import swingkt.style.fixedWidth
import java.awt.Color
import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.SwingUtilities
import javax.swing.WindowConstants

fun main() {

    SwingUtilities.invokeLater {
/*        Demo().apply {
            defaultCloseOperation = EXIT_ON_CLOSE
        }.isVisible = true*/

        TestDialogKt().apply {
            defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE
        }.isVisible = true
    }
}

class Demo : JFrame("SwingKT Demo") {

/*    init {
        val tabbedPanel = JTabbedPane()
        contentPane = tabbedPanel
        tabbedPanel.addTab("VBox", VBoxTab)
        tabbedPanel.addTab("HBox", HBoxTab)
        tabbedPanel.addTab("Left Flow", LeftFlow)
        tabbedPanel.addTab("Center Flow", CenterFlow)
        tabbedPanel.addTab("Right Flow", RightFlow)
        tabbedPanel.addTab("Border", BorderTab)
        tabbedPanel.addTab("GridBag", GridBagTab)
        tabbedPanel.addTab("FlexBox", FlexBoxTab)

        pack()
        size = Dimension(700, 700)
    }*/

    init {
        contentPane = BorderPanel {
            val buttonStyle = Style { fixedWidth(150) }
            west(Flexcol(gap = 8) {
                applyStyle { background = Color.lightGray; isOpaque = true; fixedWidth(200) }

                button("Menu Item 1") { applyStyle(buttonStyle) }
                button("Menu 2") { applyStyle(buttonStyle) }
            })
            center(TestPanel().apply { applyStyle { background = Color.BLUE } })
        }

        pack()
        size = Dimension(700, 700)

    }

}

