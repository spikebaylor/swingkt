package swingkt.components

import swingkt.component
import java.awt.Container
import javax.swing.Icon
import javax.swing.JRadioButton

fun Container.radiobutton(text: String? = null, icon: Icon? = null, selected: Boolean = false, block: JRadioButton.() -> Unit = {}) = component(JRadioButton(text, icon, selected), block)