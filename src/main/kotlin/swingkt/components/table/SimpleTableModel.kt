package swingkt.components.table

import javax.swing.table.DefaultTableModel


class SimpleTableModel<T>(initData: List<T> = emptyList()) : DefaultTableModel() {
    private val myData: MutableList<T> = initData.toMutableList()
    private val columns = mutableListOf<SimpleTableColumn<T>>()

    fun addColumn(column: SimpleTableColumn<T>) {
        columns.add(column)
        addColumn(column.columnName)
    }

    fun removeColumn(column: SimpleTableColumn<T>) {
        columns.remove(column)
    }

    fun getColumns() = columns.toList()

    fun removeItemRow(item: T) {
        myData.remove(item)
        fireTableDataChanged()
    }

    fun addItemRow(item: T) {
        myData.add(item)
        fireTableDataChanged()
    }

    fun setData(items: List<T>) {
        myData.clear()
        myData.addAll(items)
        fireTableDataChanged()
    }

    fun clear() {
        myData.clear()
        fireTableDataChanged()
    }

    override fun getRowCount(): Int {
        if (myData == null) { // how is this null?
            return 0
        }
        return myData.size
    }

    override fun getColumnCount(): Int {
        return columns.size
    }

    override fun isCellEditable(row: Int, column: Int): Boolean {
        return false
    }

    override fun getColumnName(column: Int): String {
        if (column < 0 || column >= columns.size) {
            return "ERROR"
        }
        return columns[column].columnName
    }

    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any? {
        if (columnIndex < 0 || columnIndex >= columns.size) {
            return "ERROR"
        }
        if (rowIndex < 0 || rowIndex >= myData.size) {
            return "ERROR"
        }

        val row = myData[rowIndex]
        val col = columns[columnIndex]

        return col.getValue(row)
    }

    override fun getColumnClass(columnIndex: Int): Class<*>? {
        if (columnIndex < 0 || columnIndex >= columns.size) {
            return null
        }

        val col = columns[columnIndex]

        return col.columnClass
    }

    fun getRow(row: Int): T {
        return myData[row]
    }
}
