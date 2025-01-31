package lktable.terminal

import com.jediterm.core.Color
import com.jediterm.terminal.TerminalColor
import com.jediterm.terminal.TtyConnector
import com.jediterm.terminal.ui.JediTermWidget
import com.jediterm.terminal.ui.settings.DefaultSettingsProvider
import swingkt.flex.demo.Nord
import java.io.IOException
import java.io.PipedReader
import java.io.PipedWriter
import javax.swing.JFrame

object BasicTerminalExample {
    const val ESC = 27.toChar()
    val terminalWriter = PipedWriter()
    var bg by rememberObject("MyTerminalPanel.background", Nord.PolarNight1, {it.rgb.toString() } ) { java.awt.Color.decode(it) }
    var fg by rememberObject("MyTerminalPanel.foreground", Nord.SnowStorm3, {it.rgb.toString() } ) { java.awt.Color.decode(it) }

    fun createTerminalWidget(): JediTermWidget {
        // TODO an example of making my own settings.
        //   This could be user driven
        val settings = object : DefaultSettingsProvider() {

            override fun getDefaultBackground(): TerminalColor {
                return TerminalColor { Color(bg.red, bg.green, bg.blue )}
            }

            override fun getDefaultForeground(): TerminalColor {
                return TerminalColor { Color(fg.red, fg.green, fg.blue )}
            }
        }

        val widget = JediTermWidget(80, 24, settings)
        widget.ttyConnector = ExampleTtyConnector(terminalWriter)
        widget.start()
        settings.terminalColorPalette
        return widget
    }

    fun createAndShowGUI() {
        val frame = JFrame("Basic Terminal Example")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.contentPane = createTerminalWidget()
        frame.pack()
        frame.isVisible = true
    }

    /**
     * TODO For most of my use cases there is an existing ProcessTtyConnector that I could
     *   probably use to just hook directly to the processes i'm using
     * TODO This Example may still be useful for showing Logs?
     */
    private class ExampleTtyConnector(writer: PipedWriter) : TtyConnector {
        private var myReader: PipedReader? = null

        init {
            try {
                myReader = PipedReader(writer)
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
        }

        override fun close() {
        }

        @Throws(IOException::class)
        override fun read(buf: CharArray?, offset: Int, length: Int): Int {
            return myReader!!.read(buf, offset, length)
        }

        override fun write(bytes: ByteArray?) {
        }

        override fun write(string: String?) {
        }

        override fun isConnected(): Boolean {
            return true
        }

        override fun waitFor(): Int {
            return 0
        }

        @Throws(IOException::class)
        override fun ready(): Boolean {
            return myReader!!.ready()
        }

        override fun getName(): String {
            return "ExampleTtyConnector"
        }
    }
}
