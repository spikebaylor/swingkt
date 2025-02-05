package swingkt.components

import swingkt.component
import java.awt.Container
import javax.swing.Action
import javax.swing.Icon
import javax.swing.JButton
import javax.swing.JToggleButton

fun Container.button(text: String? = null, icon: Icon? = null, block: JButton.() -> Unit = {}): JButton = component(JButton(text, icon), block)
fun Container.button(a: Action, block: JButton.() -> Unit = {}): JButton = component(JButton(a), block)


fun Container.toggleButton(text: String? = null,
                           icon: Icon? = null,
                           selected: Boolean = false,
                           block: JToggleButton.() -> Unit = {}): JToggleButton = component(JToggleButton(text, icon, selected),block)
