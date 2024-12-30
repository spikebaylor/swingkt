package swingkt.listeners

import java.awt.Component
import java.awt.event.MouseEvent
import java.awt.event.MouseListener

class KtMouseListener(
    var doOnMouseClicked: ((MouseEvent) -> Unit) = {},
    var doOnMousePressed: ((MouseEvent) -> Unit) = {},
    var doOnMouseReleased: ((MouseEvent) -> Unit) = {},
    var doOnMouseEntered: ((MouseEvent) -> Unit) = {},
    var doOnMouseExited: ((MouseEvent) -> Unit) = {},
    var doOnDoubleClick: ((MouseEvent) -> Unit) = {},
    var doOnLeftClick: ((MouseEvent) -> Unit) = {},
    var doOnRightClick: ((MouseEvent) -> Unit) = {},
    var doOnMiddleClick: ((MouseEvent) -> Unit) = {}
) : MouseListener {

    override fun mouseClicked(e: MouseEvent) {
        if (e.button == MouseEvent.BUTTON1 && e.clickCount == 2) {
            doOnDoubleClick(e)
        } else if (e.button == MouseEvent.BUTTON1) {
            doOnLeftClick(e)
        } else if (e.button == MouseEvent.BUTTON3) {
            doOnRightClick(e)
        } else if (e.button == MouseEvent.BUTTON2) {
            doOnMiddleClick(e)
        }

        doOnMouseClicked(e)
    }

    fun onMouseClicked(handler: (MouseEvent) -> Unit) {doOnMouseClicked = handler }
    fun onMousePressed(handler: (MouseEvent) -> Unit) {doOnMousePressed = handler }
    fun onMouseReleased(handler: (MouseEvent) -> Unit) {doOnMouseReleased = handler }
    fun onMouseEntered(handler: (MouseEvent) -> Unit) {doOnMouseEntered = handler }
    fun onMouseExited(handler: (MouseEvent) -> Unit) {doOnMouseExited = handler }

    fun onDoubleClick(handler: (MouseEvent) -> Unit) {doOnDoubleClick = handler }
    fun onLeftClick(handler: (MouseEvent) -> Unit) {doOnLeftClick = handler }
    fun onRightClick(handler: (MouseEvent) -> Unit) {doOnRightClick = handler }
    fun onMiddleClick(handler: (MouseEvent) -> Unit) {doOnMiddleClick = handler }

    override fun mousePressed(e: MouseEvent) = doOnMousePressed(e)
    override fun mouseReleased(e: MouseEvent) = doOnMouseReleased(e)
    override fun mouseEntered(e: MouseEvent) = doOnMouseEntered(e)
    override fun mouseExited(e: MouseEvent) = doOnMouseExited(e)
}

fun Component.addKtMouseListener(builder: KtMouseListener.() -> Unit): MouseListener {
    val l = KtMouseListener()
    l.builder()
    addMouseListener(l)
    return l
}

fun Component.onMouseClicked(handler: (MouseEvent) -> Unit) { addKtMouseListener { doOnMouseClicked = handler } }
fun Component.onMousePressed(handler: (MouseEvent) -> Unit)  { addKtMouseListener { doOnMousePressed = handler } }
fun Component.onMouseReleased(handler: (MouseEvent) -> Unit)  { addKtMouseListener { doOnMouseReleased = handler } }
fun Component.onMouseEntered(handler: (MouseEvent) -> Unit)  { addKtMouseListener { doOnMouseEntered = handler } }
fun Component.onMouseExited(handler: (MouseEvent) -> Unit)  { addKtMouseListener { doOnMouseExited = handler } }

fun Component.onDoubleClick(handler: (MouseEvent) -> Unit)  { addKtMouseListener { doOnDoubleClick = handler } }
fun Component.onLeftClick(handler: (MouseEvent) -> Unit)  { addKtMouseListener { doOnLeftClick = handler } }
fun Component.onRightClick(handler: (MouseEvent) -> Unit) {  addKtMouseListener { doOnRightClick = handler } }
fun Component.onMiddleClick(handler: (MouseEvent) -> Unit) { addKtMouseListener { doOnMiddleClick = handler } }
