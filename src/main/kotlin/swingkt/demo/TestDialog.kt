package swingkt.demo

import swingkt.button
import swingkt.components.SimpleComboBox
import swingkt.layouts.*
import swingkt.listeners.onAction
import java.awt.*
import javax.swing.*

class TestDialogKt(owner: Frame? = null) : JDialog(owner, "Test Dialog Kt") {

    init {
        contentPane = BorderPanel {
            border = BorderFactory.createEmptyBorder(8, 8, 8, 8)
            center(mainPanel())
            south { flexrow(justifyContent = FlexJustifyContent.END, gap=8) {
                button("Save") {
                    onAction { this@TestDialogKt.isVisible = false }
                }
                button("Cancel") {
                    onAction { this@TestDialogKt.isVisible = false }
                }
            }}
        }

        pack()
        setSize(300, 200)
    }

    fun mainPanel() = GridBag {
        modifyDefaultGBC {
            margin(4, 4, 4 ,0)
        }

        data class TestData(val display: String, val tooltip: String)
        val data = listOf(
            TestData("Test 1", "My TOolip"),
            TestData("Test 2", "My TOolip2"),
            TestData("Test 3", "My TOolip3"),
            TestData("Test 4", "My TOolip4"),
        )

        val combobox = SimpleComboBox(data) {
            displayText { it.display }
            tooltipText { it.tooltip }
            onAction { println("Selected: ${selectedItem?.display}") }
        }


        var row = 0

        add(JLabel("First Name"), row = row)
        add(JTextField(), row) {
            fillHorizontal()
            weightx = 1.0
        }

        add(JLabel("Last Name"), row = ++row)
        add(JTextField(), row) {
            fillHorizontal()
            weightx = 1.0
        }

        add(JLabel("Test"), row = ++row)
        add(combobox, row) {
            fillHorizontal()
            weightx = 1.0
        }

        add(JPanel(), row = ++row) {
            fillBoth()
            gridwidth=2
            weightx = 1.0
            weighty = 1.0
        }
    }
}

class TestDialogPure(owner: Frame? = null) : JDialog(owner, "Test Dialog Pure") {

    private val saveButton = JButton("Save")
    private val cancelButton = JButton("Cancel")
    private val firstNameTF = JTextField()
    private val lastNameTF = JTextField()

    init {
        layoutComponents()
        pack()
        setSize(300, 150)
    }

    private fun layoutComponents() {
        val content = JPanel(BorderLayout())
        content.border = BorderFactory.createEmptyBorder(8, 8, 8, 8)
        content.add(mainPanel(), BorderLayout.CENTER)
        content.add(buttonPanel(), BorderLayout.SOUTH)
        contentPane = content
    }

    private fun mainPanel(): JPanel {
        val mainPanel = JPanel(GridBagLayout())
        val c = GridBagConstraints()
        var y = 0
        c.insets = Insets(4, 4, 4, 4)

        c.gridx = GridBagConstraints.RELATIVE; c.gridy = y
        mainPanel.add(JLabel("First Name: "), c)
        c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1.0
        mainPanel.add(firstNameTF, c)

        c.gridy = ++y; c.fill = GridBagConstraints.NONE; c.weightx = 0.0
        mainPanel.add(JLabel("Last Name: "), c)
        c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1.0
        mainPanel.add(lastNameTF, c)

        return mainPanel
    }

    private fun buttonPanel(): JPanel {
        val buttonPanel = JPanel(FlowLayout(FlowLayout.RIGHT))
        buttonPanel.add(saveButton)
        buttonPanel.add(cancelButton)

        saveButton.addActionListener { this@TestDialogPure.isVisible = false}
        cancelButton.addActionListener { this@TestDialogPure.isVisible = false}

        return buttonPanel
    }

}


