package swingkt.components.table

import javax.swing.table.TableCellRenderer

interface SimpleTableColumn<RowT> {
    val columnName: String
    val columnClass: Class<*>
    val cellRenderer: TableCellRenderer
    var maxWidth: Int
    var minWidth: Int
    var preferredWidth: Int?
    fun getValue(item: RowT): Any?

    fun setWidths(min: Int, pref: Int, max: Int) {
        minWidth = min
        preferredWidth = pref
        maxWidth = max
    }
}
