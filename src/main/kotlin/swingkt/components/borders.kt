package swingkt.components

import java.awt.Color
import javax.swing.BorderFactory
import javax.swing.JComponent
import javax.swing.border.Border

// Borders
fun JComponent.TitledBorder(title: String) = BorderFactory.createTitledBorder(title)
fun JComponent.titledBorder(title: String) { this.border = TitledBorder(title) }

fun JComponent.CompoundBorder(outside: Border, inside: Border) = BorderFactory.createCompoundBorder(outside, inside)
fun JComponent.compoundBorder(outside: Border, inside: Border) { this.border = CompoundBorder(outside, inside) }

fun JComponent.EmptyBorder(top: Int = 0, left: Int = top, bottom: Int = top, right: Int = left): Border = BorderFactory.createEmptyBorder(top, left, bottom, right)
fun JComponent.emptyBorder(top: Int = 0, left: Int = top, bottom: Int = top, right: Int = left) { this.border = EmptyBorder(top, left, bottom, right) }

fun JComponent.LinedBorder(color: Color = Color.BLACK, thickness: Int = 1, rounded: Boolean = false): Border = BorderFactory.createLineBorder(color, thickness, rounded);
fun JComponent.linedBorder(color: Color = Color.BLACK, thickness: Int = 1, rounded: Boolean = false) { this.border = LinedBorder(color, thickness, rounded) }