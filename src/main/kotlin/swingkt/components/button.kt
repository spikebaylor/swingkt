package swingkt.components

import swingkt.component
import java.awt.Container
import javax.swing.Action
import javax.swing.Icon
import javax.swing.JButton

fun Container.button(text: String? = null, icon: Icon? = null, block: JButton.() -> Unit = {}): JButton = component(JButton(text, icon), block)
fun Container.button(a: Action, block: JButton.() -> Unit = {}): JButton = component(JButton(a), block)
