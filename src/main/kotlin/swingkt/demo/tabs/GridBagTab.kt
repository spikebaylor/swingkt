package swingkt.demo.tabs

import swingkt.demo.TestPanel
import swingkt.layouts.GridBag
import swingkt.style.applyStyle
import swingkt.style.fixedHeight
import swingkt.style.fixedWidth
import swingkt.style.linedBorder
import java.awt.Color


val GridBagTab = TabPanel {
    GridBag {
        applyStyle {
            linedBorder(Color.GREEN)
        }

        modifyDefaultGBC {
            weightx = 1.0
            weighty = 1.0
            fillBoth()
        }


        val colors = listOf(
            Color.RED,
            Color.BLUE,
            Color.CYAN,
            Color.GREEN,
            Color.YELLOW,
            Color.BLACK,
            Color.GRAY,
            Color.DARK_GRAY,
            Color.LIGHT_GRAY,
            Color.MAGENTA,
            Color.ORANGE,
            Color.PINK,
            Color.WHITE,
        )
        val panels = colors.associateWith { TestPanel().apply { applyStyle(tps) { bgColor = it } } }

        var row = 0

        add(panels[Color.RED]!!, row, 0)
        add(panels[Color.BLUE]!!, row, 1)
        add(panels[Color.GREEN]!!, row, 2) {
            weightx = 1.0
            weighty = 1.0
            fillBoth()
        }

        add(panels[Color.PINK]!!, ++row, 0)
        add(panels[Color.CYAN]!!, row, 1)
        add(panels[Color.MAGENTA]!!.apply { applyStyle { fixedWidth(200); fixedHeight(200) } }, row, 2)

    }
}
