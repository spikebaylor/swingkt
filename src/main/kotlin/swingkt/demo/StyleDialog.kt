package swingkt.demo

import swingkt.*
import swingkt.layouts.*
import swingkt.listeners.onAction
import swingkt.style.*
import java.awt.Component
import java.awt.FlowLayout
import javax.swing.*


object StyleDialog : JDialog() {

    private lateinit var prefWidthTF: JTextField
    private lateinit var prefHeightTF: JTextField
    private lateinit var minWidthTF: JTextField
    private lateinit var minHeightTF: JTextField
    private lateinit var maxWidthTF: JTextField
    private lateinit var maxHeightTF: JTextField

    private var halignGroup = ButtonGroup()
    private var left = JRadioButton("Left")
    private var center = JRadioButton("Center")
    private var right = JRadioButton("Right")

    private var valignGroup = ButtonGroup()
    private var top = JRadioButton("Top")
    private var middle = JRadioButton("Middle")
    private var bottom = JRadioButton("Bottom")

    private lateinit var component: Component
    private var resetStyle: Style = Style()

    val rootStyle = Style { fixedHeight(30) }
    val labelStyle = rootStyle.applyStyle { fixedWidth(150); }
    val tfStyle = rootStyle.applyStyle { fixedWidth(50); }
    val sliderStyle = rootStyle.applyStyle { horizontalAlignment = Component.LEFT_ALIGNMENT }

    fun showForComponent(component: Component) {
        updateView(component)
        isVisible = true
    }

    init {
        title = "Edit Style"
        contentPane = VBox() {
            applyStyle {
                border = BorderFactory.createEmptyBorder(8, 8, 8, 8)
            }

            component(form())
            vspace(16)
            component(buttons())

        }

        pack()
        setSize(500, 400)
    }

    private fun form(): Box {

        return VBox {
            prefWidthTF = sizeSliderRow("Preferred Width: ")
            prefHeightTF = sizeSliderRow("Preferred Height: ")
            minWidthTF = sizeSliderRow("Min Width: ")
            minHeightTF = sizeSliderRow("Min Height: ")
            maxWidthTF = sizeSliderRow("Max Width: ")
            maxHeightTF = sizeSliderRow("Max Height: ")

            horizontalAlignmentRow()
            verticalAlignmentRow()

        }
    }

    private fun Box.horizontalAlignmentRow() = hbox {
        label("Horizontal Alignment: ") { horizontalAlignment = SwingConstants.RIGHT; applyStyle(labelStyle) }
        hspace(8)
        component(left) { onAction { onSave() } }
        hspace(4)
        component(center) { onAction { onSave() } }
        hspace(4)
        component(right) { onAction { onSave() } }
        hglue()
        halignGroup.apply {
            add(left)
            add(center)
            add(right)
        }

    }

    private fun Box.verticalAlignmentRow() = hbox {
        label("Vertical Alignment: ") { horizontalAlignment = SwingConstants.RIGHT; applyStyle(labelStyle) }
        hspace(8)
        component(top) { onAction { onSave() } }
        hspace(4)
        component(middle) { onAction { onSave() } }
        hspace(4)
        component(bottom) { onAction { onSave() } }
        hglue()
        valignGroup.apply {
            add(top)
            add(middle)
            add(bottom)
        }

    }

    private fun Box.sizeSliderRow(label: String): JTextField {
        var tf = JTextField()
        hbox {
            label(label) { horizontalAlignment = SwingConstants.RIGHT; applyStyle(labelStyle) }
            hspace(8)
            tf = textField {
                applyStyle(tfStyle);
                isEditable = false
            }
            hspace(4)
            component(JSlider(0, 500)) {
                applyStyle(sliderStyle)
                addChangeListener { tf.text = value.toString(); onSave() }
            }
        }
        return tf
    }

    private fun buttons() = JPanel().apply {
        layout = FlowLayout(FlowLayout.RIGHT, 8, 0)

        button("Close").onAction { onClose() }
        button("Reset Style").onAction { onReset() }
    }

    private fun updateView(component: Component) {
        this.component = component
        resetStyle = component.getStyle()
        prefWidthTF.text = (resetStyle.prefWidth ?: 0).toString()
        prefHeightTF.text = (resetStyle.prefHeight ?: 0).toString()
        minWidthTF.text = (resetStyle.minWidth ?: 0).toString()
        minHeightTF.text = (resetStyle.minHeight ?: 0).toString()
        maxWidthTF.text = (resetStyle.maxWidth ?: 0).toString()
        maxHeightTF.text = (resetStyle.maxHeight ?: 0).toString()

        val halign = when(resetStyle.horizontalAlignment) {
            Component.LEFT_ALIGNMENT -> left
            Component.CENTER_ALIGNMENT -> center
            Component.RIGHT_ALIGNMENT -> right
            else -> null
        }
        if (halign != null) {
            halignGroup.setSelected(halign.model, true)
        } else {
            halignGroup.clearSelection()
        }

        val valign = when(resetStyle.verticalAlignment) {
            Component.TOP_ALIGNMENT -> top
            Component.CENTER_ALIGNMENT -> middle
            Component.BOTTOM_ALIGNMENT -> bottom
            else -> null
        }
        if (valign != null) {
            valignGroup.setSelected(valign.model, true)
        } else {
            valignGroup.clearSelection()
        }
    }

    private fun onSave() {
        val style = MutableStyle()

        prefWidthTF.text.toIntOrNull()?.let { style.prefWidth = it }
        prefHeightTF.text.toIntOrNull()?.let { style.prefHeight = it }
        minWidthTF.text.toIntOrNull()?.let { style.minWidth = it }
        minHeightTF.text.toIntOrNull()?.let { style.minHeight = it }
        maxWidthTF.text.toIntOrNull()?.let { style.maxWidth = it }
        maxHeightTF.text.toIntOrNull()?.let { style.maxHeight = it }

        style.horizontalAlignment = when(halignGroup.selection) {
            left.model -> Component.LEFT_ALIGNMENT
            center.model -> Component.CENTER_ALIGNMENT
            right.model -> Component.RIGHT_ALIGNMENT
            else -> resetStyle.horizontalAlignment
        }

        style.verticalAlignment = when(valignGroup.selection) {
            top.model -> Component.TOP_ALIGNMENT
            middle.model -> Component.CENTER_ALIGNMENT
            bottom.model -> Component.BOTTOM_ALIGNMENT
            else -> resetStyle.verticalAlignment
        }

        this.component.applyStyle(style)

    }

    private fun onReset() {
        component.applyStyle(resetStyle)
        updateView(component)
    }

    private fun onClose() {
        isVisible = false
    }

}
