package swingkt.components.table

import swingkt.colors.screen
import java.awt.Color
import java.awt.Component
import java.awt.Dimension
import java.awt.Font
import java.util.*
import javax.swing.JLabel
import javax.swing.JTable
import javax.swing.SwingConstants
import javax.swing.UIManager
import javax.swing.table.DefaultTableCellRenderer
import javax.swing.table.TableCellRenderer

class TextTableColumn<RowT, CellT>(override val columnName: String, private val valueProducer: (RowT) -> CellT, cellClass: Class<*> = String::class.java ) :
    TableCellRenderer, SimpleTableColumn<RowT> {

    override val columnClass = cellClass
    override val cellRenderer: TableCellRenderer = this
    private var displayTextSupplier: (CellT) -> String = { Objects.toString(it) }
    private var nullDisplayTextSupplier: () -> String = { "NULL" }
    private var toolTipSupplier: ((CellT) -> String)? = null
    private val defaultTableCellRenderer = DefaultTableCellRenderer()
    private var horizontalAlignment: Int = SwingConstants.LEFT
    private var backgroundSupplier: (CellT) -> Color? = { null }
    private var foregroundSupplier: (CellT) -> Color? = { null }
    private var fontSupplier: (CellT) -> Font? = { null }
    override var maxWidth = Int.MAX_VALUE
    override var minWidth = 0
    override var preferredWidth: Int? = null

    override fun getValue(item: RowT) = valueProducer(item)

    fun displayText(dt: (CellT) -> String) {
        displayTextSupplier = dt
    }

    fun nullDisplayText(dt: () -> String) {
        nullDisplayTextSupplier = dt
    }

    fun toolTipText(dt: (CellT) -> String) {
        toolTipSupplier = dt
    }

    fun leftAligned() { horizontalAlignment = SwingConstants.LEFT }
    fun rightAligned() { horizontalAlignment = SwingConstants.RIGHT }
    fun centerAligned() { horizontalAlignment = SwingConstants.CENTER }

    fun backgroundColor(supplier: (CellT) -> Color?) { backgroundSupplier = supplier }
    fun textColor(supplier: (CellT) -> Color?) { foregroundSupplier = supplier }
    fun font(supplier: (CellT) -> Font?) { fontSupplier = supplier }

    private var defaultBackground: Color? = null
    private var defaultForeground: Color? = null
    private var defaultFont: Font? = null

    private fun resetColors(comp: JLabel) {
        if (defaultBackground == null) { defaultBackground = comp.background }
        if (defaultForeground == null) { defaultForeground = comp.foreground }
        if (defaultFont == null) { defaultFont = comp.font }
        comp.background = defaultBackground
        comp.foreground = defaultForeground
        comp.font = defaultFont
    }

    private fun setSizes(comp: JLabel) {
        if (comp.minimumSize.width != minWidth) {
            println("Set minWidth: $minWidth")
            comp.minimumSize = Dimension(minWidth, comp.minimumSize.height)
        }
        if (comp.maximumSize.width != maxWidth) {
            println("Set maxWidth: $maxWidth")
            comp.maximumSize = Dimension(maxWidth, comp.maximumSize.height)
        }
        if (comp.preferredSize.width != preferredWidth && preferredWidth != null) {
            println("Set prefWidth: $preferredWidth")
            preferredWidth?.let {  comp.preferredSize = Dimension(it, comp.maximumSize.height) }
        }
    }

    private fun renderComponent(table: JTable, comp: JLabel, value: CellT, isSelected: Boolean, hasFocus: Boolean, row: Int): Component {
        resetColors(comp)

        comp.text = value?.let { displayTextSupplier(it) } ?: nullDisplayTextSupplier()
        toolTipSupplier?.let { tooltip -> comp.toolTipText = tooltip(value) }
        comp.horizontalAlignment = horizontalAlignment

        // fix alternate
        val alternateColor = UIManager.get("Table.alternateRowColor")

        if(isSelected) {
            comp.background = (UIManager.get("Table.selectionBackground") ?: table.selectionBackground ?: defaultBackground) as Color
        }

        if (!isSelected && alternateColor != null && alternateColor is Color && row % 2 == 1) {
            comp.background = alternateColor
        }

        if (!isSelected) {
            backgroundSupplier(value)?.let {
                // lightly blend the color with alternate row
                if (alternateColor != null && alternateColor is Color && row % 2 == 1) {
                    comp.background = alternateColor.screen(it)
                } else {
                    comp.background = it
                }
            }
        }
        foregroundSupplier(value)?.let { comp.foreground = it }
        fontSupplier(value)?.let { comp.font = it }


        return comp
    }

    @Suppress("UNCHECKED_CAST")
    override fun getTableCellRendererComponent(
        table: JTable, value: Any?, isSelected: Boolean, hasFocus: Boolean, row: Int, column: Int
    ): Component {
        val d = defaultTableCellRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column)
        return renderComponent(table, d as JLabel, value as CellT, isSelected, hasFocus, row)
    }

}
fun <R, C> SimpleTable<R>.textColumn(columnName: String, valueProducer: (R) -> C, cellClass: Class<*> = String::class.java, block: TextTableColumn<R, C>.() -> Unit = {}) {
    val col = TextTableColumn(columnName,valueProducer,cellClass)
    col.block()
    addSimpleTableColumn(col)
}
