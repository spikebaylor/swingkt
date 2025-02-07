package demos.lktable

import com.formdev.flatlaf.FlatClientProperties
import swingkt.components.SimpleComboBox
import swingkt.components.label
import swingkt.components.simpleComboBox
import swingkt.components.table.*
import swingkt.flex.demo.Nord
import swingkt.components.emptyBorder
import swingkt.layouts.flex.*
import swingkt.listeners.onAction
import swingkt.listeners.onKeyReleased
import swingkt.listeners.onMouseEntered
import swingkt.listeners.onMouseExited
import swingkt.scrollable
import swingkt.textField
import java.awt.Font
import javax.swing.*
import javax.swing.table.TableRowSorter


class PodTablePanel(state: AppState) : FlexBoxPanel(FlexBoxLayout(flexDirection = FlexDirection.COLUMN)) {

    private val allNamespaces = "All Namespaces"
    private val table = makeTable(state.getPods())
    private lateinit var filterText: JTextField
    private lateinit var namespaceCB: SimpleComboBox<String>

    init {
        emptyBorder(8)

        spacer(8)

        val pods = state.getPods()

        flexrow(justifyContent = FlexJustifyContent.END, gap=4) {
            label("Namespace: ")
            namespaceCB = simpleComboBox(pods.map { it.namespace }.distinct().toMutableList().apply { add(0,
                allNamespaces
            ) }) {
                selectedIndex = 0
                onAction { updateFilter() }
            }

            spacer(28)

            label("Filter: ")
            add(makeFilterTextField())

        }

        spacer(16)
        add(table.scrollable().apply {
            flexGrow(1)
        })
    }

    private fun updateFilter() {
        val sorter = table.rowSorter as TableRowSorter
        val filter = filterText.text
        val namespace = namespaceCB.selectedItem as String

        val rowFilterRegex = if (filter.isNotEmpty()) ".*$filter.*" else ".*"
        val namespaceFilterRegex = if (namespace == allNamespaces) ".*" else namespace

        val rowFilter = RowFilter.regexFilter<SimpleTableModel<Pod>, Int>(rowFilterRegex)
        val namespaceFilter = RowFilter.regexFilter<SimpleTableModel<Pod>, Int>(namespaceFilterRegex, 2)

        sorter.rowFilter = RowFilter.andFilter(listOf(rowFilter, namespaceFilter))
    }

    private fun makeTable(pods: List<Pod>) = SimpleTable(pods).apply {
        val boldLabelFont = UIManager.getDefaults().getFont("Label.font").deriveFont(Font.BOLD)
        foreground = Nord.PolarNight1
        autoCreateRowSorter = true
        selectionModel.selectionMode = ListSelectionModel.SINGLE_SELECTION

        checkboxColumn("Test", { it.testBoolean} ) {
            setWidths(50, 50, 50)
        }

        fun String.uppercase() = this.toUpperCase()

        textColumn("Pod", { it.name }) {
            minWidth = 100
            preferredWidth = 200
            toolTipText { it }
        }

        textColumn("Namespace", { it.namespace }) {
            minWidth = 100
            toolTipText { it }
        }

        val cellRenderer = ContainersTableCellRenderer()
        customColumn("Containers", cellRenderer, { it.containers }) {
            setWidths(100, 100, 100)
        }

        textColumn("Restarts", { it.restarts }, Int::class.java) {
            maxWidth = 150
            setWidths(50, 100, 150)
            centerAligned()
            font { if (it > 0) boldLabelFont else null }
            textColor { if (it > 0) Nord.AuroraOrange else null }
        }

        textColumn("State", { it.state} ) {
            centerAligned()
            setWidths(100, 150, 200)
            font { boldLabelFont }
            textColor { when(it) {
                "Running" -> Nord.AuroraGreen
                "Failed" -> Nord.AuroraRed
                "Pending" -> Nord.AuroraYellow
                "Succeeded" -> Nord.PolarNight4
                else -> Nord.AuroraPurple
            } }
            toolTipText { it }
        }

        textColumn("Images", { it.images } ) {
            setWidths(100, 200, Int.MAX_VALUE)
            displayText { value -> value.joinToString(", ") { it.substringAfterLast("/") } }
            toolTipText { "<html>${it.joinToString("<br>")}</html>" }
        }

    }

    private fun makeFilterTextField(): JTextField {
        filterText = textField(columns = 20) {
            var clearIcon = LKIcons.CLEAR_FILTER_LIGHT

            val clearButton: JButton = JButton(clearIcon).apply {
                toolTipText = "Clear"
                onMouseEntered {
                    icon = LKIcons.CLEAR_FILTER_RED
                }
                onMouseExited {
                    icon = clearIcon
                }
                onAction {
                    filterText.text = ""
                    clearIcon = LKIcons.CLEAR_FILTER_LIGHT
                    icon = clearIcon
                    updateFilter()
                }
            }

            onKeyReleased {
                clearIcon = if (text.isEmpty()) LKIcons.CLEAR_FILTER_LIGHT else LKIcons.CLEAR_FILTER_DARK
                clearButton.icon = clearIcon
                updateFilter()
            }

            // search toolbar
            val searchToolbar = JToolBar()
            searchToolbar.add(clearButton)
            putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_COMPONENT, searchToolbar)
            putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Filter text")
        }
        return filterText
    }

}
