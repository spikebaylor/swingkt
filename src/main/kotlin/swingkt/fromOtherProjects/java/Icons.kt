package swingkt.fromOtherProjects.java

import java.awt.Image
import javax.swing.Icon
import javax.swing.ImageIcon


object Icons {

    private val map = mutableMapOf<Pair<String, Int>, Icon>()

    val APP_NOTIFICATION = "icons/app-notification.png" // TODO this icon doesn't exist here but a godo example.
    fun appNotification(size: Int = 16): Icon = loadIcon(APP_NOTIFICATION, size)

    private fun loadIcon(file: String, size: Int = 16): Icon {
        return map.getOrPut(file to size) {
            val url = Icons::class.java.classLoader.getResource(file)!!
            val imageIcon = ImageIcon(url)
            val image = imageIcon.image // transform it
            val newimg = image.getScaledInstance(size, size, Image.SCALE_SMOOTH)
            return ImageIcon(newimg)
        }
    }
}
