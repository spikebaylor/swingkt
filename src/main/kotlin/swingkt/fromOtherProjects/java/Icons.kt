package swingkt.fromOtherProjects.java

import java.awt.Color
import java.awt.Image
import java.awt.image.BufferedImage
import javax.swing.Icon
import javax.swing.ImageIcon


object Icons {

    private val map = mutableMapOf<Pair<String, Int>, Icon>()

    /**
     * @param file: the file in resources that contains the icon
     * @param size: the size to resize to, default 16
     * @param baseColor: the color that is invisible, and should not be replaced
     * @param color: the color to change the icon to where the color isn't baseColor.
     */
    fun loadIcon(file: String, size: Int = 16, baseColor: Color? = null, color: Color? = null): Icon {
        return map.getOrPut(file to size) {
            val url = Icons::class.java.classLoader.getResource(file)!!
            val imageIcon = ImageIcon(url)
            val image = imageIcon.image // transform it
            val coloredImage = if (color == null || baseColor == null) image else changeColor(image, baseColor, color)
            val scaledImage = coloredImage.getScaledInstance(size, size, Image.SCALE_SMOOTH)
            return ImageIcon(scaledImage)
        }
    }

    private fun changeColor(image: Image, baseColor: Color, color: Color): Image {
        val bimage = image.toBufferedImage()
        for (y in 0 until bimage.height) for (x in 0 until bimage.width) {
            val imageColor = Color(bimage.getRGB(x, y))
            if (imageColor != baseColor) {
                bimage.setRGB(x, y, color.rgb)
            }
        }
        return bimage
    }
}

fun Image.toBufferedImage(): BufferedImage {
    if (this is BufferedImage) {
        return this
    }
    val bufferedImage = BufferedImage(this.getWidth(null), this.getHeight(null), BufferedImage.TYPE_INT_ARGB)

    val graphics2D = bufferedImage.createGraphics()
    graphics2D.drawImage(this, 0, 0, null)
    graphics2D.dispose()

    return bufferedImage
}
