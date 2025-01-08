package swingkt.components

import swingkt.component
import java.awt.Container
import javax.swing.Icon
import javax.swing.JLabel
import javax.swing.SwingConstants

fun Container.label(text: String = "",
                    icon: Icon? = null,
                    horizontalAlignment: Int = if (icon != null && text.isBlank()) SwingConstants.CENTER else SwingConstants.LEADING,
                    block: JLabel.() -> Unit = {}
) = component(JLabel(text, icon, horizontalAlignment), block)
