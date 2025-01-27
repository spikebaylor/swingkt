package lktable

import com.formdev.flatlaf.FlatLightLaf
import swingkt.flex.demo.Nord
import swingkt.fromOtherProjects.java.debugSizeChange
import swingkt.fromOtherProjects.java.rememberLocation
import swingkt.fromOtherProjects.java.rememberSize
import swingkt.fromOtherProjects.java.resetMemory
import java.awt.Dimension
import java.awt.Toolkit
import javax.swing.JFrame
import javax.swing.SwingUtilities


fun main() {

    SwingUtilities.invokeLater {
        FlatLightLaf.setup()

        FlatlafKt.table {
            background(Nord.SnowStorm3)
            alternateRowColor(Nord.SnowStorm1)
            rowHeight(28)
            selectionBackground(Nord.Frost2)
        }

        FlatlafKt.Panel.background(Nord.SnowStorm3)

        val state = AppState()

        JFrame("Local Kube Launcher").apply {
            contentPane = MainPanel(state)

            pack()

            val screenSize: Dimension = Toolkit.getDefaultToolkit().screenSize
            val defaultWidth = 1200
            val defaultHeight = 650
            val defaultX = (screenSize.width - defaultWidth) / 2
            val defaultY = (screenSize.height - defaultHeight) / 2

            //resetMemory()

            rememberSize("LocalKubeLauncher", defaultWidth, defaultHeight)
            rememberLocation("LocalKubeLauncher", defaultX, defaultY)

        }.isVisible = true
    }


}
