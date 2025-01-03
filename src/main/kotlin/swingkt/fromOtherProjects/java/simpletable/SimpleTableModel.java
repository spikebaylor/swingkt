package swingkt.fromOtherProjects.java.simpletable;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class SimpleTableModel<T> extends DefaultTableModel {

    private final List<SimpleTableColumn<T>> columns;
    private final List<T> myData = new ArrayList<>();

    public SimpleTableModel(List<SimpleTableColumn<T>> columns) {
        super();
        this.columns = columns;
    }

    public List<SimpleTableColumn<T>> getColumns() {
        return this.columns;
    }

    public void removeItemRow(T item) {
        myData.remove(item);
        fireTableDataChanged();
    }

    public void addItemRow(T item) {
        myData.add(item);
        fireTableDataChanged();
    }

    public void setData(List<T> items) {
        myData.clear();
        myData.addAll(items);
        fireTableDataChanged();
    }

    public void clear() {
        myData.clear();
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        if (myData == null) { // how is this null?
            return 0;
        }
        return myData.size();
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public String getColumnName(int column) {
        if (column < 0 || column >= columns.size()) { return "ERROR"; }
        return columns.get(column).getColumnName();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex < 0 || columnIndex >= columns.size()) { return "ERROR"; }
        if (rowIndex < 0 || rowIndex >= myData.size()) { return "ERROR"; }

        T row = myData.get(rowIndex);
        SimpleTableColumn<T> col = columns.get(columnIndex);

        return col.getValue(row);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex < 0 || columnIndex >= columns.size()) { return null; }

        SimpleTableColumn<T> col = columns.get(columnIndex);

        return col.getColumnClass();
    }

    public T getRow(int row) {
        return myData.get(row);
    }
}
