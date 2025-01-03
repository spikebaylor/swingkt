package swingkt.fromOtherProjects.java.oldtable

import javax.swing.table.DefaultTableModel

/*
Usage: from a JTable
        List<SimpleTableColumn<SimulationRun, ?>> columns = List.of(
                SimpleTableColumn.create("State", SimulationRun::getState),
                SimpleTableColumn.create("Date", r -> getDate(r.getDate())),
                SimpleTableColumn.create("Trials", r -> r.getConfig().getTrials()),
                SimpleTableColumn.create("Scale", r -> r.getConfig().getScale()),
                SimpleTableColumn.create("Progress", r -> {
                    float value = (float) r.getSummary().size() / (float)r.getConfig().getTrials();
                    String text = r.getSummary().size() + " / " + r.getConfig().getTrials();
                    return new ProgressCellRender.ProgressCellModel(value, text);
                }),
                SimpleTableColumn.create("ID", SimulationRun::getId)
        );
        model = new SimpleTableModel<>(columns);
        this.setModel(model);
 */

class SimpleTableModel<T>(private val columns: List<SimpleTableColumn<T, *>>) : DefaultTableModel() {
    private val myData: MutableList<T> = ArrayList()

    fun removeItemRow(item: T) {
        myData.remove(item)
        fireTableDataChanged()
    }

    fun setData(items: List<T>) {
        myData.clear()
        myData.addAll(items)
        fireTableDataChanged()
    }

    /*    public void reset() {
        data.clear();
        fireTableDataChanged();
    }*/
    override fun getRowCount(): Int {
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

    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any {
        if (columnIndex < 0 || columnIndex >= columns.size) {
            return "ERROR"
        }
        if (rowIndex < 0 || rowIndex >= myData.size) {
            return "ERROR"
        }

        val row = myData[rowIndex]
        val col = columns[columnIndex]

        return col.getValue(row)!!
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
