package swingkt.layouts.flex

import swingkt.component
import java.awt.Color
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
    var alignItems by layout::alignItem
    var gap by layout::gap

    init {
        isOpaque = false
    }

    fun spacer(size: Int) = component(JPanel()) {
        isOpaque = false

        fun setSizes() {
            preferredSize = this@FlexBoxPanel.layout.toNormalizedDimension(size, 1)
            minimumSize = preferredSize
            maximumSize = preferredSize
        }
        setSizes()
        this@FlexBoxPanel.layout.onChange { setSizes() }
    }

    fun flexSpacer(flexGrow: Int) = component(JPanel()) {
        isOpaque = false
        flexGrow(flexGrow)
    }

    fun opaqueBackground(color: Color) {
        isOpaque = true
        background = color
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
    fun Component.flexGrow(grow: Int) {
        if (this is FlexBoxPanel && this.parent is FlexBoxPanel) {
            (this.parent as FlexBoxPanel).modifyFlexItemConstraints(this) { copy(flexGrow = grow) }
        } else {
            this@FlexBoxPanel.modifyFlexItemConstraints(this) { copy(flexGrow = grow) }
        }
    }
    fun Component.alignSelf(align: FlexAlignItem) {
        if (this is FlexBoxPanel && this.parent is FlexBoxPanel) {
            (this.parent as FlexBoxPanel).modifyFlexItemConstraints(this) { copy(alignSelf = align) }
        } else {
            this@FlexBoxPanel.modifyFlexItemConstraints(this) { copy(alignSelf = align) }
        }
    }

}

fun FlexBox(
    flexDirection: FlexDirection = FlexDirection.ROW,
    justifyContent: FlexJustifyContent = FlexJustifyContent.START,
    alignItems: FlexAlignItem = FlexAlignItem.STRETCH,
    gap: Int = 0,
    builder: FlexBoxPanel.() -> Unit = {}) = FlexBoxPanel(FlexBoxLayout(flexDirection, justifyContent, alignItems, gap)).apply(builder)

fun Container.flexbox(
    flexDirection: FlexDirection = FlexDirection.ROW,
    justifyContent: FlexJustifyContent = FlexJustifyContent.START,
    alignItems: FlexAlignItem = FlexAlignItem.STRETCH,
    gap: Int = 0,
    builder: FlexBoxPanel.() -> Unit = {}) = component(FlexBox(flexDirection, justifyContent, alignItems, gap), builder)

fun Container.flexrow(
    justifyContent: FlexJustifyContent = FlexJustifyContent.START,
    alignItems: FlexAlignItem = FlexAlignItem.STRETCH,
    gap: Int = 0,
    builder: FlexBoxPanel.() -> Unit = {}) = component(FlexBox(FlexDirection.ROW, justifyContent, alignItems, gap), builder)

fun Container.flexcol(
    justifyContent: FlexJustifyContent = FlexJustifyContent.START,
    alignItems: FlexAlignItem = FlexAlignItem.STRETCH,
    gap: Int = 0,
    builder: FlexBoxPanel.() -> Unit = {}) = component(FlexBox(FlexDirection.COLUMN, justifyContent, alignItems, gap), builder)

