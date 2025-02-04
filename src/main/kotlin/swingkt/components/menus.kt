package swingkt.components

import swingkt.component
import javax.swing.*

fun JFrame.menuBar(builder: JMenuBar.() -> Unit = {}) {
    val mb = JMenuBar()
    mb.builder()
    jMenuBar = mb
}

fun JMenuBar.menu(name: String, builder: JMenu.() -> Unit = {}) = component(JMenu(name), builder)
fun JMenu.menuItem(name: String? = null, icon: Icon? = null, builder: JMenuItem.() -> Unit = {}) = component(JMenuItem(name, icon), builder)