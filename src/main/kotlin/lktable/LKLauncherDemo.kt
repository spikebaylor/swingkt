package lktable

import com.formdev.flatlaf.FlatLightLaf
import lktable.terminal.BasicTerminalExample
import lktable.terminal.MyTerminalPanel
import swingkt.flex.demo.Nord
import swingkt.fromOtherProjects.java.exitOnClose
import swingkt.fromOtherProjects.java.rememberLocation
import swingkt.fromOtherProjects.java.rememberSize
import swingkt.fromOtherProjects.java.resetMemory
import java.awt.Dimension
import java.awt.Toolkit
import java.lang.Thread.sleep
import javax.swing.JDialog
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

        JFrame("Terminal").apply {
            contentPane = MyTerminalPanel()
            pack()
            //resetMemory()
            rememberSize("jediterm", 800, 600)
            rememberLocation("jediterm", 0, 0)
            exitOnClose()
        }.isVisible = true

        Thread {
            for (j in 1..50) {
                val i = j % 10
                sleep(100)
                val color = 30+i
                val colorCode = BasicTerminalExample.ESC + "[${color}m"
                val resetCode = BasicTerminalExample.ESC + "[0m"
                val resetCursor = BasicTerminalExample.ESC + "[H"
                BasicTerminalExample.terminalWriter.write("${colorCode}This is a longer test that maybe goes off teh side of the screen because its a very long line test:$resetCode [$j]\r\n")
            }
        }.start()

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
