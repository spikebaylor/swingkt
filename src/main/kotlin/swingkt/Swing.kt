package swingkt

import swingkt.components.emptyBorder
import java.awt.Component
import java.awt.Dimension
import java.awt.Point
import java.awt.Window
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.util.prefs.Preferences
import javax.swing.JComponent
import javax.swing.JFrame
import javax.swing.JScrollPane


// JFrames
fun JFrame.exitOnClose() = setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)

// Alignment
fun JComponent.leftAligned() { this.alignmentX = Component.LEFT_ALIGNMENT }
fun JComponent.rightAligned() { this.alignmentX = Component.RIGHT_ALIGNMENT }
fun JComponent.centerAligned() { this.alignmentX = Component.CENTER_ALIGNMENT }


fun Component.scrollable() = JScrollPane(this).apply { emptyBorder(); verticalScrollBar.setUnitIncrement(8); }

fun Window.resetMemory() {
    val sizes: Preferences = Preferences.userRoot().node("SwingWindowSizes")
    sizes.removeNode()
    val location: Preferences = Preferences.userRoot().node("SwingWindowLocation")
    location.removeNode()
    val keyPref: Preferences = Preferences.userRoot().node("SwingKtMemory")
    keyPref.removeNode()
}

fun Window.rememberSize(key: String, defaultWidth: Int, defaultHeight: Int) {
    val keyPref: Preferences = Preferences.userRoot().node("SwingWindowSizes").node(key)

    val width: String = keyPref["width", defaultWidth.toString()]
    val height: String = keyPref["height", defaultHeight.toString()]

    try {
        this.setSize(width.toInt(), height.toInt())
    } catch (e: Exception) {
        this.setSize(defaultWidth, defaultHeight)
    }

    this.addComponentListener(object : ComponentAdapter() {
        override fun componentResized(e: ComponentEvent) {
            val size = e.component.size
            keyPref.put("width", size.width.toString())
            keyPref.put("height", size.height.toString())
        }
    })
}

fun Window.rememberLocation(key: String, defaultX: Int = 0, defaultY: Int = 0) {
    val keyPref: Preferences = Preferences.userRoot().node("SwingWindowLocation").node(key)

    val x: String = keyPref["x", defaultX.toString()]
    val y: String = keyPref["y", defaultY.toString()]

    try {
        this.setLocation(x.toInt(), y.toInt())
    } catch (e: Exception) {
        e.printStackTrace()
        this.setLocation(0, 0)
    }

    this.addComponentListener(object : ComponentAdapter() {
        override fun componentMoved(e: ComponentEvent) {
            val location: Point = e.component.location
            keyPref.put("x", java.lang.String.valueOf(location.x))
            keyPref.put("y", java.lang.String.valueOf(location.y))
        }
    })
}


// Debug
fun Component.debugSizeChange(name: String = "${javaClass.name} :: ${hashCode()}", verbose: Boolean = false) = addComponentListener(object : ComponentAdapter() {
    override fun componentResized(e: ComponentEvent) {
        val c = e.component
        if (verbose) println()
        println("[$name] size: ${c.size}")
        if (verbose) {
            println("[$name]  min: ${c.minimumSize}")
            println("[$name] pref: ${c.preferredSize}")
            println("[$name]  max: ${c.minimumSize}")
        }
    }
})

fun Dimension.copy(width: Int = this.width, height: Int = this.height) = Dimension(width, height)
