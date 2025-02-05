package swingkt.colors

import java.awt.Color
import kotlin.math.max
import kotlin.math.min

fun Color.darker(factor: Float): Color {
    return Color(
        max((red * factor).toInt(), 0),
        max((green * factor).toInt(), 0),
        max((blue * factor).toInt(), 0),
        alpha
    )
}

fun Color.multiply(c2: Color): Color {
    val multiply = { a: Float, b:Float -> a * b}
    val r = min(255, floatMath(this.red, c2.red, multiply))
    val g = min(255, floatMath(this.green, c2.green, multiply))
    val b = min(255, floatMath(this.blue, c2.blue, multiply))
    return Color(r, g, b)
}

fun Color.screen(c2: Color): Color {
    val screen = { a: Float, b:Float -> 1 - (1-a) - (1-b)}
    val r = max(0, floatMath(this.red, c2.red, screen))
    val g = max(0, floatMath(this.green, c2.green, screen))
    val b = max(0, floatMath(this.blue, c2.blue, screen))
    return Color(r, g, b)
}

private fun floatMath(c1: Int, c2: Int, work: (Float, Float) -> Float): Int {
    val c1f = (c1.toFloat() / 255f)
    val c2f = (c2.toFloat() / 255f)
    val res = work(c1f, c2f)
    return (res * 255).toInt()
}

fun Color.lighter(factor: Float = 0.7f): Color {
    return Color(
        min((red / factor).toInt(), 255),
        min((green / factor).toInt(), 255),
        min((blue / factor).toInt(), 255),
        alpha
    )
}

fun Color.blend(c2: Color, inRatio: Float): Color {
    var ratio = inRatio
    val c1 = this

    if (ratio > 1f) ratio = 1f
    else if (ratio < 0f) ratio = 0f
    val iRatio = 1.0f - ratio

    val i1 = c1.rgb
    val i2 = c2.rgb

    val a1 = (i1 shr 24 and 0xff)
    val r1 = ((i1 and 0xff0000) shr 16)
    val g1 = ((i1 and 0xff00) shr 8)
    val b1 = (i1 and 0xff)

    val a2 = (i2 shr 24 and 0xff)
    val r2 = ((i2 and 0xff0000) shr 16)
    val g2 = ((i2 and 0xff00) shr 8)
    val b2 = (i2 and 0xff)

    val a = ((a1 * iRatio) + (a2 * ratio)).toInt()
    val r = ((r1 * iRatio) + (r2 * ratio)).toInt()
    val g = ((g1 * iRatio) + (g2 * ratio)).toInt()
    val b = ((b1 * iRatio) + (b2 * ratio)).toInt()

    return Color(a shl 24 or (r shl 16) or (g shl 8) or b)
}
