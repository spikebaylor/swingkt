package swingkt.listeners

import java.awt.Component
import java.awt.event.KeyEvent
import java.awt.event.KeyListener

class KtKeyListener(
    var doOnKeyTyped: (e: KeyEvent) -> Unit = {},
    var doOnKeyPressed: (e: KeyEvent) -> Unit = {},
    var doOnKeyReleased: (e: KeyEvent) -> Unit = {},

) : KeyListener {

    fun onKeyTyped(handler: (KeyEvent) -> Unit) { doOnKeyTyped = handler }
    fun onKeyPressed(handler: (KeyEvent) -> Unit) { doOnKeyPressed = handler }
    fun onKeyReleased(handler: (KeyEvent) -> Unit) { doOnKeyTyped = handler }

    override fun keyTyped(e: KeyEvent) = doOnKeyTyped(e)
    override fun keyPressed(e: KeyEvent) = doOnKeyPressed(e)
    override fun keyReleased(e: KeyEvent) = doOnKeyReleased(e)
}

fun Component.addKtKeyListener(builder: KtKeyListener.() -> Unit): KeyListener {
    val l = KtKeyListener()
    l.builder()
    addKeyListener(l)
    return l
}

fun Component.onKeyTyped(handler: (KeyEvent) -> Unit) { addKtKeyListener { doOnKeyTyped = handler } }
fun Component.onKeyPressed(handler: (KeyEvent) -> Unit) { addKtKeyListener { doOnKeyPressed = handler } }
fun Component.onKeyReleased(handler: (KeyEvent) -> Unit) { addKtKeyListener { doOnKeyReleased = handler } }
