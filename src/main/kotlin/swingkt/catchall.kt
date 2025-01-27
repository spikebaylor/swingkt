package swingkt

import java.awt.Component
import java.awt.Container
import java.awt.Dimension
import javax.swing.*

fun <T : Component>Container.component(c: T, block: T.() -> Unit = {}): T {
    add(c)
    c.block()
    return c
}


fun Container.TextField(text: String = "", columns: Int = 0, block: JTextField.() -> Unit = {}): JTextField = JTextField(text, columns).apply(block)
fun Container.textField(text: String = "", columns: Int = 0, block: JTextField.() -> Unit = {}): JTextField = component(JTextField(text, columns), block)

fun Dimension.toStr() = "($width,$height)"
infix fun Int.x(height: Int) = Dimension(this, height)
