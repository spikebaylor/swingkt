package swingkt.layouts

import swingkt.component
import java.awt.Component
import java.awt.Container
import java.awt.Dimension
import javax.swing.Box
import javax.swing.BoxLayout
import javax.swing.JComponent

enum class FlexDirection(val boxDirection: Int) {
    ROW(BoxLayout.X_AXIS),
    COLUMN(BoxLayout.Y_AXIS)
}

enum class FlexJustifyContent {
    START,
    END,
    CENTER,
    SPACE_BETWEEN,
    SPACE_EVENLY
}

enum class FlexAlignItem {
    START,
    END,
    CENTER
}

class FlexBox(
    val direction: FlexDirection = FlexDirection.ROW,
    val justifyContent: FlexJustifyContent = FlexJustifyContent.START,
    val alignItems: FlexAlignItem = FlexAlignItem.CENTER,
    val gap: Int = 0
) : Box(direction.boxDirection) {

    private val components = mutableListOf<Component>()
    private var deferLayout = false

    override fun add(comp: Component): Component {
        components.add(comp)
        flexLayout()
        return comp
    }

    override fun remove(comp: Component) {
        components.remove(comp)
        flexLayout()
    }

    override fun removeAll() {
        components.clear()
        flexLayout()
    }

    fun deferLayout(builder: FlexBox.() -> Unit) {
        deferLayout = true
        builder()
        deferLayout = false
        flexLayout()
    }

    private fun flexLayout() {
        if (!deferLayout) {
            super.removeAll()
            // TODO add pre glue if space_around

            alignItems()

            handlePre()

            components.forEachIndexed { index, component ->
                layoutItem(component, index == components.lastIndex)
            }

            handlePost()
        }
    }

    private fun alignItems() {
        val alignValue: Float = when (alignItems) {
            FlexAlignItem.START -> 0f
            FlexAlignItem.END -> 1.0f
            FlexAlignItem.CENTER -> .5f
        }

        components.forEach {
            if (it is JComponent) {
                if (direction == FlexDirection.ROW) {
                    it.alignmentY = alignValue
                } else {
                    it.alignmentX = alignValue
                }
            }
        }
    }

    private fun handlePost() {
        when (justifyContent) {
            FlexJustifyContent.CENTER -> super.add(makeGlue())
            FlexJustifyContent.SPACE_EVENLY -> super.add(makeGlue())
            else -> { /* do nothing */ }
        }
    }

    private fun handlePre() {
        when (justifyContent) {
            FlexJustifyContent.END -> super.add(makeGlue())
            FlexJustifyContent.CENTER -> super.add(makeGlue())
            FlexJustifyContent.SPACE_EVENLY -> super.add(makeGlue())
            else -> { /* do nothing */ }
        }
    }

    private fun layoutItem(c: Component, isLast: Boolean) {
        super.add(c)
        if (!isLast) {
            super.add(makeGap())

            when (justifyContent) {
                FlexJustifyContent.SPACE_BETWEEN -> super.add(makeGlue())
                FlexJustifyContent.SPACE_EVENLY -> super.add(makeGlue())
                else -> { /* do nothing */ }
            }
        }
    }

    private fun makeGap(): Component {
        return if (direction == FlexDirection.ROW) {
            createRigidArea(Dimension(gap, 0))
        } else {
            createRigidArea(Dimension(0, gap))
        }
    }

    private fun makeGlue(): Component {
        return if (direction == FlexDirection.ROW) {
            createHorizontalGlue()
        } else {
            createVerticalGlue()
        }
    }
}

fun Container.Flexrow(justifyContent: FlexJustifyContent = FlexJustifyContent.START,
                      alignItems: FlexAlignItem = FlexAlignItem.CENTER,
                      gap: Int = 0,
                      builder: FlexBox.() -> Unit) = FlexBox(FlexDirection.ROW, justifyContent, alignItems, gap).apply { deferLayout { builder() } }

fun Container.Flexcol(justifyContent: FlexJustifyContent = FlexJustifyContent.START,
                      alignItems: FlexAlignItem = FlexAlignItem.CENTER,
                      gap: Int = 0,
                      builder: FlexBox.() -> Unit) = FlexBox(FlexDirection.COLUMN, justifyContent, alignItems, gap).apply { deferLayout { builder() } }

fun Container.flexrow(justifyContent: FlexJustifyContent = FlexJustifyContent.START,
                      alignItems: FlexAlignItem = FlexAlignItem.CENTER,
                      gap: Int = 0,
                      builder: FlexBox.() -> Unit) = component(FlexBox(FlexDirection.ROW, justifyContent, alignItems, gap)) { deferLayout { builder() } }

fun Container.flexcol(justifyContent: FlexJustifyContent = FlexJustifyContent.START,
                      alignItems: FlexAlignItem = FlexAlignItem.CENTER,
                      gap: Int = 0,
                      builder: FlexBox.() -> Unit) = component(FlexBox(FlexDirection.COLUMN, justifyContent, alignItems, gap)) { deferLayout { builder() } }
