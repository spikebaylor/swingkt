package swingkt

import java.awt.Component
import java.awt.Container
import java.awt.Dimension
import javax.swing.*

fun <T : Component>Container.component(c: T, block: T.() -> Unit = {}): T {
    c.block()
    add(c)
    return c
}


fun Container.textField(text: String = "", block: JTextField.() -> Unit = {}): JTextField = component(JTextField(text), block)

fun Dimension.toStr() = "($width,$height)"
infix fun Int.x(height: Int) = Dimension(this, height)
