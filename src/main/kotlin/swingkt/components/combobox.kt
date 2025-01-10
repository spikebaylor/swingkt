package swingkt.components

import swingkt.component
import java.awt.Container
import java.awt.event.ItemEvent
import javax.swing.*

@Suppress("UNCHECKED_CAST")
class SimpleComboBox<T>(initValues: Collection<T> = emptyList()): JComboBox<T>() {

    private val cellRenderer = SimpleListCellRenderer<T>()
    private val selectedItemListeners = mutableListOf<(T?) -> Unit>()

    init {
        setRenderer(cellRenderer)
        getModel().addAll(initValues)

        this.addItemListener { event ->
            if (event.stateChange == ItemEvent.SELECTED) {
                selectedItemListeners.forEach { it.invoke(selectedItem) }
            }
        }
    }

    override fun getSelectedItem(): T? = model.selectedItem as T?
    override fun getModel(): DefaultComboBoxModel<T>  = super.getModel() as DefaultComboBoxModel<T>

    fun onSelectedItemChanged(listener: (selectedItem: T?) -> Unit): (T?) -> Unit {
        selectedItemListeners.add(listener)
        return listener
    }

    fun removeSelectedItemChangedListener(listener: (T?) -> Unit) {
        selectedItemListeners.remove(listener)
    }

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



