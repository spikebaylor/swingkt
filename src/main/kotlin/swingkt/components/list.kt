package swingkt.components

import swingkt.component
import java.awt.Container
import javax.swing.DefaultListModel
import javax.swing.JList
import javax.swing.ListModel


class SimpleList<T>(initValues: Collection<T> = emptyList()): JList<T>(DefaultListModel()) {

    private val cellRenderer = SimpleListCellRenderer<T>()
    private val selectedItemListeners = mutableListOf<(List<T>) -> Unit>()

    init {
        setCellRenderer(cellRenderer)
        getModel().addAll(initValues)

        addListSelectionListener { e ->
            if (!e.valueIsAdjusting) {
                selectedItemListeners.forEach { it.invoke(selectedValuesList.orEmpty()) }
            }
        }
    }

    fun onSelectedItemsChanged(listener: (selectedItem: List<T>) -> Unit): (List<T>) -> Unit {
        selectedItemListeners.add(listener)
        return listener
    }

    fun removeSelectedItemsChangedListener(listener: (List<T>) -> Unit) {
        selectedItemListeners.remove(listener)
    }

    override fun getModel(): DefaultListModel<T>  = super.getModel() as DefaultListModel<T>

    override fun setModel(aModel: ListModel<T>?) {
        if (aModel is DefaultListModel<T>) {
            super.setModel(aModel)
        } else {
            throw UnsupportedOperationException()
        }
    }

    fun displayText(func: (T) -> String) { cellRenderer.displayTextSupplier = func }
    fun tooltipText(func: (T) -> String) { cellRenderer.tooltipSupplier = func }
}

fun <T> Container.SimpleList(defaultValues: Collection<T> = emptyList(), func: SimpleList<T>.() -> Unit): SimpleList<T> = SimpleList(defaultValues).apply(func)
fun <T> Container.simpleList(defaultValues: Collection<T> = emptyList(), func: SimpleList<T>.() -> Unit): SimpleList<T> = component(SimpleList(defaultValues), func)

