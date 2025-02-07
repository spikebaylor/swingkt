package swingkt.components

import java.awt.Component
import javax.swing.*

class SimpleListCellRenderer<T>(
    var displayTextSupplier: (T) -> String = { it.toString() },
    var nullDisplayTextSupplier: () -> String? = { "NULL" },
    var tooltipSupplier: (T) -> String? = { null },
    val delegate: ListCellRenderer<Any> = DefaultListCellRenderer(),
) : ListCellRenderer<T> {

    override fun getListCellRendererComponent(
        list: JList<out T>?,
        value: T?,
        index: Int,
        isSelected: Boolean,
        cellHasFocus: Boolean
    ): Component {
        val display = value?.let { displayTextSupplier(it) } ?: nullDisplayTextSupplier()
        val tooltip = value?.let { tooltipSupplier(it) }

        val comp = delegate.getListCellRendererComponent(list, display, index, isSelected, cellHasFocus)
        if (comp is JComponent) {
            comp.toolTipText = tooltip
            comp.border = BorderFactory.createEmptyBorder(2, 4, 2, 0)
        }
        return comp
    }

}