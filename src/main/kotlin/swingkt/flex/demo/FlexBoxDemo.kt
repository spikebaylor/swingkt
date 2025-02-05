package swingkt.flex.demo

import swingkt.component
import swingkt.components.*
import swingkt.flex.*
import swingkt.fromOtherProjects.java.debugSizeChange
import swingkt.fromOtherProjects.java.emptyBorder
import swingkt.fromOtherProjects.java.exitOnClose
import swingkt.layouts.BorderPanel
import swingkt.listeners.onAction
import swingkt.listeners.onRightClick
import swingkt.x
import java.awt.Color
import java.awt.Dimension
import javax.swing.*

class FlexBoxTest : JFrame("FlexBoxTest") {

    lateinit var flex: FlexBoxLayout
    lateinit var flexPanel: JPanel

    init {

        contentPane = BorderPanel {

            flexPanel = flexpanel()
            center(flexPanel)

            north {

                flexrow(gap = 4, alignItem = FlexAlignItem.CENTER) {
                    isOpaque = true
                    background = Nord.SnowStorm1
                    emptyBorder(8)

                    label("Direction")
                    simpleComboBox(FlexDirection.entries) {
                        displayText { it.name }
                        this.selectedItem = flex.flexDirection
                        onAction { flex.flexDirection = this.selectedItem!! }
                    }

                    spacer(8)

                    label("Justify Content")
                    simpleComboBox(FlexJustifyContent.entries) {
                        displayText { it.name }
                        this.selectedItem = flex.justifyContent
                        onAction { flex.justifyContent = this.selectedItem!! }
                    }

                    spacer(8)

                    label("Align Items")
                    simpleComboBox(FlexAlignItem.entries) {
                        displayText { it.name }
                        this.selectedItem = flex.alignItem
                        onAction { flex.alignItem = this.selectedItem!! }
                    }

                    spacer(8)

                    label("Gap")
                    numberSpinner(flex.gap, 0, Int.MAX_VALUE, 1) {
                        preferredSize = 75 x 28
                        onChange {
                            flex.gap = value as Int
                        }
                        debugSizeChange()
                    }

                    spacer(8)

                    button("Add Panel") {
                        onAction { addPanel() }
                    }

                }

            }

        }


        pack()
        size = Dimension(1100, 800)
        exitOnClose()
    }

    private fun addPanel() {
        flexPanel.add(makePanel(150, 150, getNextColor()), FlexItemConstraints());
        flexPanel.revalidate()
    }

    private fun removePanel(panel: JPanel) {
        flexPanel.remove(panel)
        flexPanel.revalidate()
        flexPanel.repaint()
    }

    private fun flexpanel() = FlexBox {
        flex = layout

        background = Nord.SnowStorm3
        border = BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 20, 40, 80),
            BorderFactory.createLineBorder(Nord.PolarNight4, 1)
        )

        add(makePanel(150, 150, getNextColor()), FlexItemConstraints())
        add(makePanel(150, 300, getNextColor()), FlexItemConstraints())
        add(makePanel(150, 200, getNextColor()), FlexItemConstraints())
    }


    private fun makePanel(pWidth: Int, pHeight: Int, color: Color): FlexItemPanel {
        return FlexItemPanel(pWidth, pHeight, color, flex).apply {
            val p = this
            onRightClick { me ->
                JPopupMenu("Stuff").apply {
                    add(JMenuItem("Remove Panel").apply {
                        onAction { removePanel(p) }
                    })
                }.show(p, me.x, me.y)
            }
        }
    }


    private var colorIndex = 0
    private fun getNextColor(): Color {
        val color = Nord.colorWheel[colorIndex]
        colorIndex = if (colorIndex == Nord.colorWheel.lastIndex) 0 else colorIndex + 1
        return color
    }

}

fun main() {
    val frame = FlexBoxTest()
    SwingUtilities.invokeLater {
        frame.isVisible = true
    }
}
