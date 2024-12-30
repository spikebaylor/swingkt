package swingkt.layouts

import swingkt.component
import java.awt.Container
import java.awt.FlowLayout
import javax.swing.JPanel

fun Container.Flow(alignment: Int = FlowLayout.CENTER, hgap: Int = 5, vgap: Int = 5, block: JPanel.() -> Unit = {}) = JPanel(FlowLayout(alignment, hgap, vgap)).apply(block)

fun Container.flow(alignment: Int = FlowLayout.CENTER, hgap: Int = 5, vgap: Int = 5, block: JPanel.() -> Unit = {}) = component(Flow(alignment, hgap, vgap, block))
fun Container.centerFlow(hgap: Int = 5, vgap: Int = 5, block: JPanel.() -> Unit = {}) = component(Flow(FlowLayout.CENTER, hgap, vgap, block))
fun Container.leftFlow(hgap: Int = 5, vgap: Int = 5, block: JPanel.() -> Unit = {}) = component(Flow(FlowLayout.LEFT, hgap, vgap, block))
fun Container.rightFlow(hgap: Int = 5, vgap: Int = 5, block: JPanel.() -> Unit = {}) = component(Flow(FlowLayout.RIGHT, hgap, vgap, block))

