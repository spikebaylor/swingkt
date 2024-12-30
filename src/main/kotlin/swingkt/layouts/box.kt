package swingkt.layouts

import swingkt.component
import java.awt.Container
import java.awt.Dimension
import javax.swing.Box


fun Container.VBox(block: Box.() -> Unit = {}) = Box.createVerticalBox().apply(block)
fun Container.HBox(block: Box.() -> Unit = {}) = Box.createHorizontalBox().apply(block)

fun Container.box(axis: Int, block: Box.() -> Unit = {}) = component(Box(axis), block)
fun Container.vbox(block: Box.() -> Unit = {}) = component(Box.createVerticalBox(), block)
fun Container.hbox(block: Box.() -> Unit = {}) = component(Box.createHorizontalBox(), block)

fun Box.vspace(height: Int) = component(Box.createRigidArea(Dimension(0,height)))
fun Box.hspace(width: Int) = component(Box.createRigidArea(Dimension(width,0)))
fun Box.vglue() = component(Box.createVerticalGlue())
fun Box.hglue() = component(Box.createHorizontalGlue())
