package swingkt.fromOtherProjects.java

import java.awt.*
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.util.concurrent.Executors
import java.util.prefs.Preferences
import javax.swing.*
import javax.swing.border.Border


// JFrames
fun JFrame.exitOnClose() = setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)

// Layouts
fun Container.vboxLayout() { this.layout = BoxLayout(this, BoxLayout.Y_AXIS) }
fun Container.hboxLayout() { this.layout = BoxLayout(this, BoxLayout.X_AXIS) }
fun Container.borderLayout() { this.layout = BorderLayout() }
fun Container.flowLayoutLeftAligned(hgap: Int = 5, vgap: Int = 5) { this.layout = FlowLayout(FlowLayout.LEFT, hgap, vgap) }
fun Container.flowLayoutRightAligned(hgap: Int = 5, vgap: Int = 5) { this.layout = FlowLayout(FlowLayout.RIGHT, hgap, vgap) }
fun Container.flowLayout(hgap: Int = 5, vgap: Int = 5) { flowLayoutLeftAligned(hgap, vgap) }

// Borders
fun JComponent.titledBorder(title: String) { this.border = BorderFactory.createTitledBorder(title) }
fun JComponent.titledBorder(title: String, internalBorderBuilder: JComponent.() -> Border) {
    this.border = BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(title), internalBorderBuilder())
}

fun JComponent.EmptyBorder(top: Int = 0, left: Int = top, bottom: Int = top, right: Int = left): Border = BorderFactory.createEmptyBorder(top, left, bottom, right)
fun JComponent.emptyBorder(top: Int = 0, left: Int = top, bottom: Int = top, right: Int = left) { this.border = EmptyBorder(top, left, bottom, right) }

// Alignment
fun JComponent.leftAligned() { this.alignmentX = Component.LEFT_ALIGNMENT }
fun JComponent.rightAligned() { this.alignmentX = Component.RIGHT_ALIGNMENT }
fun JComponent.centerAligned() { this.alignmentX = Component.CENTER_ALIGNMENT }


fun Component.scrollable() = JScrollPane(this).apply { emptyBorder(); verticalScrollBar.setUnitIncrement(8); }

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

fun Window.rememberLocation(key: String, x: Int = 0, y: Int = 0) {
    val keyPref: Preferences = Preferences.userRoot().node("SwingWindowLocation").node(key)

    val x: String = keyPref["x", x.toString()]
    val y: String = keyPref["y", y.toString()]

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
fun Component.debugSizeChange() = addComponentListener(object : ComponentAdapter() {
    override fun componentResized(e: ComponentEvent) {
        println(e.component.size)
    }
})

object IO {
    private val executor = Executors.newCachedThreadPool()

    fun onIoThread(run: Runnable) {
        executor.execute(run)
    }
}