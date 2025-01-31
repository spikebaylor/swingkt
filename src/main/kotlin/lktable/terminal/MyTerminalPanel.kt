package lktable.terminal

import com.jediterm.terminal.TerminalMode
import com.jediterm.terminal.model.JediTerminal
import lktable.LKIcons
import swingkt.component
import swingkt.components.button
import swingkt.components.toggleButton
import swingkt.flex.FlexBoxPanel
import swingkt.flex.FlexJustifyContent
import swingkt.flex.demo.Nord
import swingkt.flex.flexcol
import swingkt.fromOtherProjects.java.emptyBorder
import swingkt.listeners.onAction
import swingkt.threading.swingWorker
import java.awt.Robot
import java.awt.event.InputEvent
import java.awt.event.KeyEvent
import java.awt.event.MouseEvent
import java.lang.Thread.sleep
import java.util.*
import java.util.prefs.Preferences
import javax.swing.JButton
import javax.swing.JColorChooser
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class rememberObject<R: Any, T: Any>(private val key: String,
                              private val defaultValue: T,
                              private val serialize: (T) -> String = { Objects.toString(it) },
                              private val deserialize: (String) -> T) : ReadWriteProperty<R, T> {

    private var cacheValue: T? = null

    override fun getValue(thisRef: R, property: KProperty<*>): T {
        if (cacheValue == null) {
            val keyPref: Preferences = Preferences.userRoot().node("SwingKtMemory")
            val str = keyPref.get(key, null)
            cacheValue = if (str != null) deserialize(str) else defaultValue
        }
        return cacheValue!!
    }

    override fun setValue(thisRef: R, property: KProperty<*>, value: T) {
        val keyPref: Preferences = Preferences.userRoot().node("SwingKtMemory")
        val str = serialize(value)
        keyPref.put(key, str)
        cacheValue = value
    }
}

class MyTerminalPanel : FlexBoxPanel() {

    private val jedipanel = BasicTerminalExample.createTerminalWidget()
    private val jediterm = jedipanel.terminal as JediTerminal
    private val ICON_SIZE = 22
    private val ICON_COLOR = Nord.PolarNight4

    private val DEFAULT_BG = Nord.PolarNight1
    private val DEFAULT_FG = Nord.SnowStorm3

    private lateinit var bgButton: JButton
    private lateinit var fgButton: JButton

    init {
        opaqueBackground(Nord.SnowStorm3)
        emptyBorder(8)
        gap = 8

        flexcol(gap = 4, justifyContent = FlexJustifyContent.SPACE_BETWEEN) {
            flexcol(gap = 4) {
                toggleButton(icon = LKIcons.wrapText(color = Nord.PolarNight4, size = ICON_SIZE), selected = jediterm.isAutoWrap) {
                    fun updateBackground() { background = if (!isSelected) Nord.SnowStorm2 else Nord.Frost1 }
                    selectedIcon = LKIcons.wrapText(color = Nord.SnowStorm3, size = ICON_SIZE)
                    toolTipText = "Soft-Wrap"
                    updateBackground()
                    onAction { setAutoWrap(isSelected); updateBackground() }
                }

                button(icon = LKIcons.copyAll(color = ICON_COLOR, size = ICON_SIZE)) {
                    background = Nord.SnowStorm2
                    toolTipText = "Copy Terminal Output to clipboard"
                    onAction { copyAll() }
                }
                button(icon = LKIcons.deleteSweep(color = ICON_COLOR, size = ICON_SIZE)) {
                    background = Nord.SnowStorm2
                    toolTipText = "Clear Terminal"
                    onAction { clearTerminal() }
                }
            }

            flexcol(gap = 4) {
                bgButton = button(icon = LKIcons.formatColorFill(color = ICON_COLOR, size = ICON_SIZE)) {
                    background = BasicTerminalExample.bg
                    foreground = BasicTerminalExample.fg
                    toolTipText = "Set Terminal Background Color"
                    onAction {
                        JColorChooser.showDialog(this@MyTerminalPanel, "Choose Terminal Background Color", BasicTerminalExample.bg)?.let {
                            BasicTerminalExample.bg = it
                            jedipanel.repaint()
                            updateButtonColors()
                        }
                    }
                }

                fgButton = button(icon = LKIcons.formatColorText(color = ICON_COLOR, size = ICON_SIZE)) {
                    background = BasicTerminalExample.fg
                    foreground = BasicTerminalExample.bg
                    toolTipText = "Set Terminal Foreground Color"
                    onAction {
                        JColorChooser.showDialog(this@MyTerminalPanel, "Choose Terminal Foreground Color", BasicTerminalExample.fg)?.let {
                            BasicTerminalExample.fg = it
                            jedipanel.repaint()
                            updateButtonColors()
                        }
                    }
                }

                button(icon = LKIcons.invertColors(color = ICON_COLOR, size = ICON_SIZE)) {
                    toolTipText = "Swap Background and Foreground Colors"
                    onAction {
                        val bg = BasicTerminalExample.bg
                        val fg = BasicTerminalExample.fg
                        BasicTerminalExample.bg = fg
                        BasicTerminalExample.fg = bg
                        jedipanel.repaint()
                        updateButtonColors()
                    }
                }

                button(icon = LKIcons.formatColorReset(color = ICON_COLOR, size = ICON_SIZE)) {
                    toolTipText = "Reset Terminal Colors"
                    onAction {
                        BasicTerminalExample.bg = DEFAULT_BG
                        BasicTerminalExample.fg = DEFAULT_FG
                        jedipanel.repaint()
                        updateButtonColors()
                    }
                }
            }
        }

        component(jedipanel) {
            flexGrow(1)
        }

    }

    private fun updateButtonColors() {
        bgButton.background = BasicTerminalExample.bg
        bgButton.foreground = BasicTerminalExample.fg
        fgButton.background = BasicTerminalExample.fg
        fgButton.foreground = BasicTerminalExample.bg
    }

    fun setAutoWrap(enable: Boolean) {
        jediterm.setModeEnabled(TerminalMode.AutoWrap, enable)
    }

    fun clearTerminal() {
        jedipanel.terminalPanel.clearBuffer()
    }

    fun copyAll() {
        // TODO This whole thing is super hacky because I can't get to the internals of TerminalPanel

        // Select All
        jedipanel.terminalPanel.selectAll()

        // use Robot to simulate CTRL + SHIFT + C key press in terminal (Copy)
        val robot = Robot()
        jedipanel.terminalPanel.requestFocusInWindow()
        robot.pressAndReleaseKey(KeyEvent.VK_CONTROL, KeyEvent.VK_SHIFT, KeyEvent.VK_C)

        // TODO SUPER FRIGGIN HACKY
        // setup a swing worker to wait 10ms then come back on the EDT to fake a mouse event on the terminalPanel which has the side effect of clearing the selection
        swingWorker<Unit, Unit>(
            inBackground = { sleep(10) },
            onDone = {
                val event = MouseEvent(jedipanel.terminalPanel, MouseEvent.MOUSE_PRESSED, 1, InputEvent.BUTTON1_DOWN_MASK, 0, 0, 1, false, MouseEvent.BUTTON1)
                jedipanel.terminalPanel.mouseListeners.forEach { it.mousePressed(event) }
            }
        ).execute()
    }

    private fun Robot.pressAndReleaseKey(vararg events: Int) {
        for (event in events) {
            keyPress(event)
            sleep(1)
        }
        for (event in events) {
            keyRelease(event)
            sleep(1)
        }
    }

}

