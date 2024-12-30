package swingkt.listeners

import java.awt.event.ActionEvent
import javax.swing.AbstractButton
import javax.swing.JComboBox

fun JComboBox<*>.onAction(block: (ActionEvent) -> Unit) {
    this.addActionListener { block(it) }
}

fun AbstractButton.onAction(block: (ActionEvent) -> Unit) {
    this.addActionListener { block(it) }
}
