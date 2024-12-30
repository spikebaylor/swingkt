package swingkt.demo

import swingkt.listeners.onDoubleClick
import swingkt.layouts.hbox
import swingkt.layouts.vspace
import swingkt.style.ImmutableStyle
import swingkt.toStr
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.JLabel


fun FixedSizeStyle(width: Int, height: Int) = ImmutableStyle(width, height, width, height, width, height)


class TestPanel : Box(BoxLayout.Y_AXIS) {

    private val actual = JLabel()
    private val pref = JLabel()
    private val min = JLabel()
    private val max= JLabel()

    init {
        isOpaque = true


        hbox {
            add(JLabel("actual:"))
            add(actual)
        }

        vspace(2)

        hbox {
            add(JLabel("pref:"))
            add(pref)
        }

        vspace(2)

        hbox {
            add(JLabel("min:"))
            add(min)
        }

        vspace(2)

        hbox {
            add(JLabel("max:"))
            add(max)
        }

        addComponentListener(object : ComponentAdapter() {
            override fun componentResized(e: ComponentEvent?) {
                updateSizes()
            }
        })

        updateSizes()

        onDoubleClick { StyleDialog.showForComponent(this@TestPanel) }
    }

    private fun updateSizes() {
        actual.text = size.toStr()
        pref.text = preferredSize.toStr()
        min.text = minimumSize.toStr()
        max.text = maximumSize.toStr()
    }

}
