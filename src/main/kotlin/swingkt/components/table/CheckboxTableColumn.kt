package swingkt.components.table

import swingkt.colors.screen
import swingkt.flex.demo.Nord
import java.awt.Color
import java.awt.Component
import javax.swing.JCheckBox
import javax.swing.JTable
import javax.swing.SwingConstants
import javax.swing.UIManager
import javax.swing.table.DefaultTableCellRenderer
import javax.swing.table.TableCellRenderer


class CheckboxTableColumn<T>(override val columnName: String, private val valueProducer: (T) -> Boolean) :
    SimpleTableColumn<T>, TableCellRenderer, JCheckBox() {
    override val columnClass = Boolean::class.java
    override val cellRenderer = this
    override var maxWidth = Int.MAX_VALUE
    override var minWidth = 0
    override var preferredWidth: Int? = null

    init {
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    override fun getValue(item: T): Boolean = valueProducer(item)

    override fun getTableCellRendererComponent(
        table: JTable, value: Any?, isSelected: Boolean, hasFocus: Boolean, row: Int, column: Int
    ): Component {



        if (isSelected) {
            foreground = table.selectionForeground
            //setBackground(table.selectionBackground)
            background = (UIManager.get("Table.selectionBackground") ?: table.selectionBackground) as Color
        } else {
            // fix alternate
            val alternateColor = UIManager.get("Table.alternateRowColor")

            background = if (alternateColor != null && alternateColor is Color && row % 2 == 1) {
                alternateColor
            } else {
                table.background
            }

            foreground = table.foreground
        }

        setSelected((value != null && (value as Boolean)))
        return this

    }
}

fun <R> SimpleTable<R>.checkboxColumn(
    columnName: String,
    valueProducer: (R) -> Boolean,
    block: CheckboxTableColumn<R>.() -> Unit = {}
) {
    val col = CheckboxTableColumn(columnName, valueProducer)
    col.block()
    addSimpleTableColumn(col)
}
