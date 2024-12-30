package swingkt

import java.awt.Component
import java.awt.Container
import java.awt.Dimension
import java.awt.event.ActionEvent
import javax.swing.JButton
import javax.swing.JComboBox
import javax.swing.JLabel
import javax.swing.JTextField

fun <T : Component>Container.component(c: T, block: T.() -> Unit = {}): T {
    c.block()
    add(c)
    return c
}

fun Container.label(text: String, block: JLabel.() -> Unit = {}): JLabel = component(JLabel(text), block)
fun Container.textField(text: String = "", block: JTextField.() -> Unit = {}): JTextField = component(JTextField(text), block)
fun Container.button(text: String, block: JButton.() -> Unit = {}): JButton = component(JButton(text), block)

fun Dimension.toStr() = "($width,$height)"
infix fun Int.x(height: Int) = Dimension(this, height)
