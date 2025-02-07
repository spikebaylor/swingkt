package demos.lktable

import swingkt.flex.demo.Nord
import swingkt.images.Icons
import java.awt.Color

object LKIcons {

    private val defaultColor = Nord.SnowStorm1

    private val CLOSE = "icons/close.png" to Color.BLACK
    private val PLAY = "icons/play_arrow.png" to Color.BLACK
    private val STOP = "icons/stop.png" to Color.BLACK
    private val RESTART = "icons/restart_alt.png" to Color.BLACK
    private val COPY_ALL = "icons/copy_all.png" to Color.BLACK
    private val DELETE_SWEEP = "icons/delete_sweep.png" to Color.BLACK
    private val SYNC_ARROW_DOWN = "icons/sync_arrow_down.png" to Color.BLACK
    private val TEXT_SELECT_MOVE_DOWN = "icons/text_select_move_down.png" to Color.BLACK
    private val WRAP_TEXT = "icons/wrap_text.png" to Color.BLACK
    private val FORMAT_COLOR_FILL = "icons/format_color_fill.png" to Color.BLACK
    private val FORMAT_COLOR_RESET = "icons/format_color_reset.png" to Color.BLACK
    private val FORMAT_COLOR_TEXT = "icons/format_color_text.png" to Color.BLACK
    private val INVERT_COLORS = "icons/invert_colors.png" to Color.BLACK

    val CLEAR_FILTER_LIGHT = close(18, Nord.SnowStorm3)
    val CLEAR_FILTER_DARK = close(18, Nord.PolarNight4)
    val CLEAR_FILTER_RED = close(18, Nord.AuroraRed)

    fun close(size: Int = 18, color: Color = defaultColor) = loadIcon(CLOSE, size, color)
    fun play(size: Int = 18, color: Color = defaultColor) = loadIcon(PLAY, size, color)
    fun stop(size: Int = 18, color: Color = defaultColor) = loadIcon(STOP, size, color)
    fun restart(size: Int = 18, color: Color = defaultColor) = loadIcon(RESTART, size, color)
    fun copyAll(size: Int = 18, color: Color = defaultColor) = loadIcon(COPY_ALL, size, color)
    fun deleteSweep(size: Int = 18, color: Color = defaultColor) = loadIcon(DELETE_SWEEP, size, color)
    fun syncArrowDown(size: Int = 18, color: Color = defaultColor) = loadIcon(SYNC_ARROW_DOWN, size, color)
    fun textSelectMoveDown(size: Int = 18, color: Color = defaultColor) = loadIcon(TEXT_SELECT_MOVE_DOWN, size, color)
    fun formatColorFill(size: Int = 18, color: Color = defaultColor) = loadIcon(FORMAT_COLOR_FILL, size, color)
    fun formatColorReset(size: Int = 18, color: Color = defaultColor) = loadIcon(FORMAT_COLOR_RESET, size, color)
    fun formatColorText(size: Int = 18, color: Color = defaultColor) = loadIcon(FORMAT_COLOR_TEXT, size, color)
    fun invertColors(size: Int = 18, color: Color = defaultColor) = loadIcon(INVERT_COLORS, size, color)
    fun wrapText(size: Int = 18, color: Color = defaultColor) = loadIcon(WRAP_TEXT, size, color)

    private fun loadIcon(icon: Pair<String, Color>, size: Int = 18, color: Color? = null) = Icons.loadIcon(icon.first, size, icon.second, color)



}
