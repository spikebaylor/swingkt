package swingkt.flex.demo

import swingkt.flex.*
import swingkt.listeners.onDoubleClick
import swingkt.toStr
import java.awt.Color
import java.awt.Dimension
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import javax.swing.JLabel


class FlexItemPanel(width: Int = 100, height: Int = 100, color: Color = Color.LIGHT_GRAY, val parentLayout: FlexBoxLayout) : FlexBoxPanel(
    FlexBoxLayout(
    flexDirection = FlexDirection.COLUMN,
    gap = 2,
    justifyContent = FlexJustifyContent.CENTER,
    alignItem = FlexAlignItem.CENTER
)
) {

    private val actual = JLabel()
    private val pref = JLabel()

    init {
        isOpaque = true
        background = color
        preferredSize = Dimension(width, height)

        flexrow(gap = 2) {
            add(JLabel("actual:"))
            add(actual)
        }

        flexrow(gap = 2) {
            add(JLabel("pref:"))
            add(pref)
        }

        addComponentListener(object : ComponentAdapter() {
            override fun componentResized(e: ComponentEvent?) {
                updateSizes()
            }
        })

        updateSizes()

        onDoubleClick { FlexItemStyleDialog.showForComponent(parentLayout, this@FlexItemPanel, it.x, it.y) }
    }

    private fun updateSizes() {
        actual.text = size.toStr()
        pref.text = preferredSize.toStr()
    }

}
