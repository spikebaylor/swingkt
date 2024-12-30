package swingkt.demo.tabs

import swingkt.style.Style
import swingkt.style.fixedHeight
import swingkt.style.fixedWidth
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Container
import javax.swing.BorderFactory
import javax.swing.JPanel

class TabPanel(containerBuilder: TabPanel.() -> Container) : JPanel(BorderLayout()) {
    val tps = Style {
        fixedWidth(100)
        fixedHeight(100)
        border = BorderFactory.createLineBorder(Color.BLACK)
    }

    init {
        add(containerBuilder(), BorderLayout.CENTER)
    }

}
