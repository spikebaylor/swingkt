package lktable

import swingkt.flex.demo.Nord
import swingkt.fromOtherProjects.java.Icons
import java.awt.Color

object LKIcons {

    private val defaultColor = Nord.SnowStorm1

    private val CLEAR = "icons/clear.png" to Color.BLACK
    private val PLAY = "icons/play.png" to Color.BLACK
    private val STOP = "icons/stop.png" to Color.BLACK
    private val RESTART = "icons/restart.png" to Color.BLACK

    val CLEAR_LIGHT = clear(18, Nord.SnowStorm3)
    val CLEAR_DARK = clear(18, Nord.PolarNight4)
    val CLEAR_RED = clear(18, Nord.AuroraRed)

    fun clear(size: Int = 18, color: Color = defaultColor) = loadIcon(CLEAR, size, color)
    fun play(size: Int = 18, color: Color = defaultColor) = loadIcon(PLAY, size, color)
    fun stop(size: Int = 18, color: Color = defaultColor) = loadIcon(STOP, size, color)
    fun restart(size: Int = 18, color: Color = defaultColor) = loadIcon(RESTART, size, color)

    private fun loadIcon(icon: Pair<String, Color>, size: Int = 18, color: Color? = null) = Icons.loadIcon(icon.first, size, icon.second, color)



}
