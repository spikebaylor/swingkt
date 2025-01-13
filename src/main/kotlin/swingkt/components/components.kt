package swingkt.components

import swingkt.x
import java.awt.Component

fun Component.setMaxWidth(width: Int) { maximumSize = width x  maximumSize.height }
fun Component.setMaxHeight(height: Int) { maximumSize = maximumSize.width x height }

fun Component.setMinWidth(width: Int) { minimumSize = width x  minimumSize.height }
fun Component.setMinHeight(height: Int) { minimumSize = minimumSize.width x height }

fun Component.setPreferredWidth(width: Int) { preferredSize = width x  preferredSize.height }
fun Component.setPreferredHeight(height: Int) { preferredSize = preferredSize.width x height }

