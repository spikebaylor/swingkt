package swingkt.components

import swingkt.component
import java.awt.Container
import javax.swing.JSpinner
import javax.swing.SpinnerNumberModel

fun Container.numberSpinner(value: Int, min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE, step: Int = 1, builder: JSpinner.() -> Unit)
    = component(JSpinner(SpinnerNumberModel(value, min, max, step)), builder)
fun Container.numberSpinner(value: Double, min: Double = Double.MIN_VALUE, max: Double = Double.MAX_VALUE, step: Double = 1.0, builder: JSpinner.() -> Unit)
        = component(JSpinner(SpinnerNumberModel(value, min, max, step)), builder)

fun JSpinner.onChange(block: () -> Unit) = addChangeListener { block() }
