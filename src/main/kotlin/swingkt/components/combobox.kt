package swingkt.components

import swingkt.component
import java.awt.Component
import java.awt.Container
import javax.swing.*

@Suppress("UNCHECKED_CAST")
class SimpleComboBox<T>(initValues: Collection<T> = emptyList()): JComboBox<T>() {

    private val cellRenderer = SimpleListCellRenderer<T>()

    init {
        setRenderer(cellRenderer)
        getModel().addAll(initValues)
    }

    override fun getSelectedItem(): T? = model.selectedItem as T?
    override fun getModel(): DefaultComboBoxModel<T>  = super.getModel() as DefaultComboBoxModel<T>

    override fun setModel(aModel: ComboBoxModel<T>?) {
        if (aModel is DefaultComboBoxModel<T>) {
            super.setModel(aModel)
        } else {
            throw UnsupportedOperationException()
        }
    }

    fun displayText(func: (T) -> String) { cellRenderer.displayTextSupplier = func }
    fun tooltipText(func: (T) -> String) { cellRenderer.tooltipSupplier = func }
}

fun <T> Container.SimpleComboBox(defaultValues: Collection<T> = emptyList(), func: SimpleComboBox<T>.() -> Unit): SimpleComboBox<T> = SimpleComboBox(defaultValues).apply(func)
fun <T> Container.simpleComboBox(defaultValues: Collection<T> = emptyList(), func: SimpleComboBox<T>.() -> Unit): SimpleComboBox<T> = component(SimpleComboBox(defaultValues), func)



class SimpleListCellRenderer<T>(
    var displayTextSupplier: (T) -> String = { it.toString() },
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
        val display = value?.let { displayTextSupplier(it) } ?: ""
        val tooltip = value?.let { tooltipSupplier(it) }

        val comp = delegate.getListCellRendererComponent(list, display, index, isSelected, cellHasFocus)
        if (comp is JComponent) {
            comp.toolTipText = tooltip
            comp.border = BorderFactory.createEmptyBorder(2, 4, 2, 0)
        }
        return comp
    }

}

