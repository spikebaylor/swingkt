package swingkt.components.table

import swingkt.component
import java.awt.Container
import javax.swing.JTable
import javax.swing.table.TableColumn
import javax.swing.table.TableModel


class SimpleTable<T>(initData: List<T> = emptyList()) : JTable(SimpleTableModel(initData)) {


    override fun setModel(dataModel: TableModel) {
        if (dataModel is SimpleTableModel<*>) {
            super.setModel(dataModel)
        } else {
            throw UnsupportedOperationException("Can not set data model")
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun getModel(): SimpleTableModel<T> {
        return super.getModel() as SimpleTableModel<T>
    }

    fun addSimpleTableColumn(col: SimpleTableColumn<T>) {
        model.addColumn(col)
        updateRenderers()
    }

    private fun updateRenderers() {
        // for some reason when i add a new column, the cell renderers are being removed. I think the col model is
        // dropping them and recreating the columns each time i'm sure i'm just doing something incorrectly.

        model.getColumns().forEach { col ->
            val tableColumn = getColumn(col.columnName)
            tableColumn.cellRenderer = col.cellRenderer
            tableColumn.maxWidth = col.maxWidth
            tableColumn.minWidth = col.minWidth
            col.preferredWidth?.let { tableColumn.preferredWidth = it }
        }
    }
//
//    fun setModel(tableModel: SimpleTableModel<T>) {
//        super.setModel(tableModel)
//        this.tableModel = tableModel
//
//        tableModel.columns.forEach { col ->
//            val tableColumn = getColumn(col.columnName)
//            if (col.cellRenderer != null) {
//                tableColumn.cellRenderer = col.cellRenderer
//            }
//            setColumnMaxWidth(tableColumn, col.maxWidth)
//        }
//    }


    private fun setColumnMaxWidth(tableColumn: TableColumn, width: Int) {
        tableColumn.preferredWidth = width
        tableColumn.maxWidth = width
    }

//    fun createColumn(name: String, getValue: Function<T, Any>) {
//        addSimpleTableColumn(SimpleTableColumn.create(name, Any::class.java, getValue))
//    }

    companion object {
//        fun <T> build(block: Function<SimpleJTableContext<T>, List<SimpleTableColumn<T>>>): SimpleTable<T> {
//            val table: SimpleTable<T> = SimpleTable()
//            val ctx = SimpleJTableContext<T>()
//            val columns = block.apply(ctx)
//            val model = SimpleTableModel(columns)
//            table.setModel(model)
//            return table
//        }

//        fun <T> create(initData: List<T> = emptyList(), block: SimpleTable<T>.() -> Unit): SimpleTable<T> {
//            val table: SimpleTable<T> = SimpleTable(initData)
//            table.block()
//            return table
//        }
    }
}

//class SimpleJTableContext<T> {
//    fun build(name: String, getValue: Function<T, Any>): SimpleTableColumn.Builder<T> {
//        return SimpleTableColumn.Builder(name, getValue)
//    }
//
//    fun create(name: String, getValue: Function<T, Any>): SimpleTableColumn<T> {
//        return SimpleTableColumn.create(name, Any::class.java, getValue)
//    }
//}


fun <T> Container.simpleTable(rowValues: List<T> = emptyList(), builder: SimpleTable<T>.() -> Unit) =
    component(SimpleTable(rowValues), builder)

/*

simpleTable<T>(values: List<T>) {

    textcolumn(header) { rowValue -> rowValue.valueForCell }
    textcolumn(header) { rowValue ->
        centerAligned()
        background { cellValue ->  if (it.isBad) RED else BLACK }
        textColor { cellValue -> if (it.isBad) RED else BLACK }
        displayText { cellValue -> something different than it.valueForCell }
        editable { newValue -> setValue() }
        rowValue.valueForCell // this block must return the value
    }

    checkboxColumn(header) { it.someBooleanValue }

    customColumn(header, renderer, editor) {
        editable { newValue -> setValue() }
        it.valueForCell // this block must return the value
    }


}



 */
