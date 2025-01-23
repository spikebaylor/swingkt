package swingkt.flex.demo

import swingkt.component
import swingkt.components.*
import swingkt.flex.*
import swingkt.fromOtherProjects.java.emptyBorder
import swingkt.listeners.onAction
import swingkt.textField
import swingkt.x
import java.awt.Component
import javax.swing.*


object FlexItemStyleDialog : JDialog() {

    private lateinit var alignSelfCB: SimpleComboBox<FlexAlignItem?>
    private lateinit var resetFunction: () -> Unit
    private lateinit var prefWidthTF: JTextField
    private lateinit var prefHeightTF: JTextField
    private lateinit var flexGrowSpinner: JSpinner

    private lateinit var component: Component
    private lateinit var layout: FlexBoxLayout

    fun showForComponent(layout: FlexBoxLayout, component: Component, x: Int, y: Int) {
        updateView(component, layout)
        component.locationOnScreen.x
        setLocation(component.locationOnScreen.x + x, component.locationOnScreen.y + y)
        isVisible = true
    }

    init {
        title = "Edit Flex Item"
        contentPane = FlexBox(
            flexDirection = FlexDirection.COLUMN,
            gap = 16,
            alignItem = FlexAlignItem.STRETCH,
            justifyContent = FlexJustifyContent.SPACE_BETWEEN
        ) {

            emptyBorder(8)

            component(form())
            component(buttons())
        }

        pack()
        setSize(380, 200)
        isResizable = false
    }

    private fun form() = FlexBox(flexDirection = FlexDirection.COLUMN, gap = 8) {
        prefWidthTF = sizeSliderRow("Preferred Width: ")
        prefHeightTF = sizeSliderRow("Preferred Height: ")

        flexrow(gap = 8) {
            label("Flex Grow: ") {
                horizontalAlignment = SwingConstants.RIGHT;
                preferredSize = 100 x preferredSize.height
            }
            flexGrowSpinner = numberSpinner(0, 0, 10, 1) {
                onChange { onSave() }
            }
        }

        flexrow(gap = 8) {
            label("Align Self: ") {
                horizontalAlignment = SwingConstants.RIGHT;
                preferredSize = 100 x preferredSize.height
            }
            alignSelfCB = simpleComboBox(listOf(null, FlexAlignItem.START, FlexAlignItem.END, FlexAlignItem.CENTER, FlexAlignItem.STRETCH)) {
                displayText { it!!.name }
                nullDisplayText { "Inherit" }
                onAction { onSave() }
            }
        }
    }


    private fun JPanel.sizeSliderRow(label: String): JTextField {
        var tf = JTextField()
        flexrow(gap = 8) {
            label(label) {
                horizontalAlignment = SwingConstants.RIGHT;
                preferredSize = 100 x preferredSize.height
            }
            tf = textField {
                isEditable = false
            }
            component(JSlider(0, 500)) {
                addChangeListener { tf.text = value.toString(); onSave() }
            }
        }
        return tf
    }

    private fun buttons() = FlexBox(gap = 8, justifyContent = FlexJustifyContent.END) {
        button("Reset Style").onAction { resetFunction(); onSave() }
        button("Close").onAction { onClose() }
    }

    private fun updateView(component: Component, layout: FlexBoxLayout) {
        FlexItemStyleDialog.component = component
        FlexItemStyleDialog.layout = layout
        val currentWidth = component.preferredSize.width
        val currentHeight = component.preferredSize.height
        val constraints = layout.getFlexItemConstraints(component)
        val flexGrow = constraints.flexGrow
        val alignSelf = constraints.alignSelf

        resetFunction = {
            prefWidthTF.text = currentWidth.toString()
            prefHeightTF.text = currentHeight.toString()
            flexGrowSpinner.value = flexGrow
            alignSelfCB.selectedItem = alignSelf
        }


        resetFunction()

    }

    private fun onSave() {
        val width = prefWidthTF.text.toIntOrNull() ?: component.preferredSize.width
        val height = prefHeightTF.text.toIntOrNull() ?: component.preferredSize.height

        val fg = flexGrowSpinner.value as Int
        val alignSelf = alignSelfCB.selectedItem

        component.preferredSize = width x height
        component.revalidate()

        layout.setFlexItemConstraints(component, FlexItemConstraints(fg, alignSelf))
    }

    private fun onClose() {
        isVisible = false
    }

}
