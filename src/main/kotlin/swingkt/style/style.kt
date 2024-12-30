package swingkt.style

import swingkt.x
import java.awt.Color
import java.awt.Component
import java.awt.Font
import javax.swing.BorderFactory
import javax.swing.JComponent
import javax.swing.border.Border

interface Style {
    val prefWidth: Int?
    val prefHeight: Int?
    val minWidth: Int?
    val minHeight: Int?
    val maxWidth: Int?
    val maxHeight: Int?
    val bgColor: Color?
    val fgColor: Color?
    val font: Font?
    val horizontalAlignment: Float?
    val verticalAlignment: Float?
    val border: Border?
}

data class ImmutableStyle(
    override val prefWidth: Int? = null,
    override val prefHeight: Int? = null,
    override val minWidth: Int? = null,
    override val minHeight: Int? = null,
    override val maxWidth: Int? = null,
    override val maxHeight: Int? = null,
    override val bgColor: Color? = null,
    override val fgColor: Color? = null,
    override val font: Font? = null,
    override val horizontalAlignment: Float? = null,
    override val verticalAlignment: Float? = null,
    override val border: Border? = null
) : Style

data class MutableStyle(
    override var prefWidth: Int? = null,
    override var prefHeight: Int? = null,
    override var minWidth: Int? = null,
    override var minHeight: Int? = null,
    override var maxWidth: Int? = null,
    override var maxHeight: Int? = null,
    override var bgColor: Color? = null,
    override var fgColor: Color? = null,
    override var font: Font? = null,
    override var horizontalAlignment: Float? = null,
    override var verticalAlignment: Float? = null,
    override var border: Border? = null
) : Style

fun MutableStyle.fixedWidth(w: Int) { prefWidth = w; minWidth = w; maxWidth = w }
fun MutableStyle.fixedHeight(h: Int) { prefHeight = h; minHeight = h; maxHeight = h }
fun MutableStyle.linedBorder(color: Color, thickness: Int = 1, rounded: Boolean = false) { border = BorderFactory.createLineBorder(color, thickness, rounded) }
fun MutableStyle.leftAligned() { horizontalAlignment = Component.LEFT_ALIGNMENT }
fun MutableStyle.centerAligned() { horizontalAlignment = Component.CENTER_ALIGNMENT }
fun MutableStyle.rightAligned() { horizontalAlignment = Component.RIGHT_ALIGNMENT }


fun Style(block: MutableStyle.() -> Unit = {}): Style = MutableStyle().apply(block).toStyle()

fun Style.toMutable() = MutableStyle(prefWidth, prefHeight, minWidth, minHeight, maxWidth, maxHeight, bgColor, fgColor, font, horizontalAlignment, verticalAlignment, border)
fun Style.toStyle() = if (this is ImmutableStyle) this else ImmutableStyle(prefWidth, prefHeight, minWidth, minHeight, maxWidth, maxHeight, bgColor, fgColor, font, horizontalAlignment, verticalAlignment, border)

fun Style.applyStyle(style: Style): Style {
    val root = this.toMutable()

    style.prefWidth?.let { root.prefWidth = it }
    style.prefHeight?.let { root.prefHeight = it }
    style.minWidth?.let { root.minWidth = it }
    style.minHeight?.let { root.minHeight = it }
    style.maxWidth?.let { root.maxWidth = it }
    style.maxHeight?.let { root.maxHeight = it }

    style.bgColor?.let { root.bgColor = it }
    style.fgColor?.let { root.fgColor = it }

    style.font?.let { root.font = it }
    style.horizontalAlignment?.let { root.horizontalAlignment = it }
    style.verticalAlignment?.let { root.verticalAlignment = it }
    style.border?.let { root.border = it }

    return root.toStyle()
}

fun Style.applyStyle(block: MutableStyle.() -> Unit): Style {
    val root = this.toMutable()
    root.block()
    return root.toStyle()
}

fun Component.applyStyle(style: Style = getStyle(), block: MutableStyle.() -> Unit = {}): Style {
    val root = getStyle().applyStyle(style).toMutable()
    root.block()
    setStyle(root)
    return root.toStyle()
}

fun Component.setStyle(style: Style) {
    val prefWidth = style.prefWidth ?: this.preferredSize.width
    val prefHeight = style.prefHeight ?: this.preferredSize.height
    preferredSize = prefWidth x prefHeight

    val minWidth = style.minWidth ?: this.minimumSize.width
    val minHeight = style.minHeight ?: this.minimumSize.height
    minimumSize = minWidth x minHeight

    val maxWidth = style.maxWidth ?: this.maximumSize.width
    val maxHeight = style.maxHeight ?: this.maximumSize.height
    maximumSize = maxWidth x maxHeight

    style.bgColor?.let { background = it }
    style.fgColor?.let { foreground = it }

    style.font?.let { font = it }

    if (this is JComponent) {
        style.horizontalAlignment?.let { alignmentX = it }
        style.verticalAlignment?.let { alignmentY = it }

        style.border?.let { border = it }
    }

    revalidate()
}

fun Component.getStyle(): Style {
    val border = if (this is JComponent) this.border else null

    return ImmutableStyle(preferredSize.width, preferredSize.height,
        minimumSize.width, minimumSize.height,
        maximumSize.width, maximumSize.height,
        background, foreground, font, alignmentX, alignmentY, border
    )
}


