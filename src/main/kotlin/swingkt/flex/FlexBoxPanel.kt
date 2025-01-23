package swingkt.flex

import swingkt.component
import java.awt.Component
import java.awt.Container
import java.awt.LayoutManager
import javax.swing.JPanel

open class FlexBoxPanel(flexLayout: FlexBoxLayout = FlexBoxLayout(
    FlexDirection.ROW,
    FlexJustifyContent.START,
    FlexAlignItem.STRETCH,
    0)
) : JPanel(flexLayout) {

    var flexDirection by layout::flexDirection
    var justifyContent by layout::justifyContent
    var alignItem by layout::alignItem
    var gap by layout::gap

    init {
        isOpaque = false
    }

    fun getFlexItemConstraints(comp: Component) = layout.getFlexItemConstraints(comp)
    fun setFlexItemConstraints(comp: Component, constraints: FlexItemConstraints) = layout.setFlexItemConstraints(comp, constraints)
    fun modifyFlexItemConstraints(comp: Component, builder: FlexItemConstraints.() -> FlexItemConstraints) = layout.modifyFlexItemConstraints(comp, builder)

    override fun getLayout(): FlexBoxLayout {
        return super.getLayout() as FlexBoxLayout
    }

    override fun setLayout(mgr: LayoutManager?) {
        if (super.getLayout() == null && mgr is FlexBoxLayout) {
            super.setLayout(mgr)
        } else {
            throw UnsupportedOperationException("FlexBox does not support setLayout")
        }
    }

    // Additional methods on Components that are within a FlexBox context
    fun Component.setConstraints(c: FlexItemConstraints) =this@FlexBoxPanel.setFlexItemConstraints(this, c)
    fun Component.flexGrow(grow: Int) = this@FlexBoxPanel.modifyFlexItemConstraints(this) { copy(flexGrow = grow) }
    fun Component.alignSelf(align: FlexAlignItem) = this@FlexBoxPanel.modifyFlexItemConstraints(this) { copy(alignSelf = align) }

}

fun FlexBox(
    flexDirection: FlexDirection = FlexDirection.ROW,
    justifyContent: FlexJustifyContent = FlexJustifyContent.START,
    alignItem: FlexAlignItem = FlexAlignItem.STRETCH,
    gap: Int = 0,
    builder: FlexBoxPanel.() -> Unit = {}) = FlexBoxPanel(FlexBoxLayout(flexDirection, justifyContent, alignItem, gap)).apply(builder)

fun Container.flexbox(
    flexDirection: FlexDirection = FlexDirection.ROW,
    justifyContent: FlexJustifyContent = FlexJustifyContent.START,
    alignItem: FlexAlignItem = FlexAlignItem.STRETCH,
    gap: Int = 0,
    builder: FlexBoxPanel.() -> Unit = {}) = component(FlexBox(flexDirection, justifyContent, alignItem, gap), builder)

fun Container.flexrow(
    justifyContent: FlexJustifyContent = FlexJustifyContent.START,
    alignItem: FlexAlignItem = FlexAlignItem.STRETCH,
    gap: Int = 0,
    builder: FlexBoxPanel.() -> Unit = {}) = component(FlexBox(FlexDirection.ROW, justifyContent, alignItem, gap), builder)

fun Container.flexcol(
    justifyContent: FlexJustifyContent = FlexJustifyContent.START,
    alignItem: FlexAlignItem = FlexAlignItem.STRETCH,
    gap: Int = 0,
    builder: FlexBoxPanel.() -> Unit = {}) = component(FlexBox(FlexDirection.COLUMN, justifyContent, alignItem, gap), builder)

