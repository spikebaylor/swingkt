package swingkt.components.table

import javax.swing.table.TableCellRenderer

class CustomTableColumn<RowT, CellT>(override val columnName: String,
                              override val cellRenderer: TableCellRenderer,
                              private val valueProducer: (RowT) -> CellT,
                              cellClass: Class<*> = Any::class.java) : SimpleTableColumn<RowT> {
    override val columnClass = cellClass
    override var maxWidth = Int.MAX_VALUE
    override var minWidth = 0
    override var preferredWidth: Int? = null

    override fun getValue(item: RowT) = valueProducer(item)
}

fun <R, C> SimpleTable<R>.customColumn(
    columnName: String,
    cellRenderer: TableCellRenderer,
    valueProducer: (R) -> C,
    cellClass: Class<*> = Any::class.java,
    block: CustomTableColumn<R, C>.() -> Unit = {}
) {
    val col = CustomTableColumn(columnName, cellRenderer, valueProducer, cellClass)
    col.block()
    addSimpleTableColumn(col)
}
