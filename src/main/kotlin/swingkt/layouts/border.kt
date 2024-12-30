package swingkt.layouts

import swingkt.component
import java.awt.BorderLayout
import java.awt.Component
import java.awt.Container
import javax.swing.JPanel


class BorderPanel : JPanel(BorderLayout()) {

    private var context = BorderLayout.CENTER

    fun <T : Component> center(comp: T) = add(comp, BorderLayout.CENTER)
    fun <T : Component> north(comp: T) = add(comp, BorderLayout.NORTH)
    fun <T : Component> east(comp: T) = add(comp, BorderLayout.EAST)
    fun <T : Component> south(comp: T) = add(comp, BorderLayout.SOUTH)
    fun <T : Component> west(comp: T) = add(comp, BorderLayout.WEST)

    private fun inContext(ctx: String, builder: BorderPanel.() -> Unit)  { context = ctx; builder()  }

    fun center(builder: BorderPanel.() -> Unit) = inContext(BorderLayout.CENTER, builder)
    fun north(builder: BorderPanel.() -> Unit) = inContext(BorderLayout.NORTH, builder)
    fun east(builder: BorderPanel.() -> Unit) = inContext(BorderLayout.EAST, builder)
    fun south(builder: BorderPanel.() -> Unit) = inContext(BorderLayout.SOUTH, builder)
    fun west(builder: BorderPanel.() -> Unit) = inContext(BorderLayout.WEST, builder)

    override fun add(comp: Component): Component {
        add(comp, context)
        return comp
    }
}



fun Container.BorderPanel(block: BorderPanel.() -> Unit) = BorderPanel().apply(block)
fun Container.borderPanel(block: BorderPanel.() -> Unit = {}) = component(BorderPanel(), block)
