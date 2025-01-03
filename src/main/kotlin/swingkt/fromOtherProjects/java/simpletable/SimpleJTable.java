package swingkt.fromOtherProjects.java.simpletable;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.util.List;
import java.util.function.Function;

/*
Usage

                SimpleJTable<Map<String, String>> table = SimpleJTable.build(ctx -> keys.stream()
                        .map(k -> ctx.build(k, m -> m.get(k)).build())
                        .collect(Collectors.toList()));
 */

public class SimpleJTable<T> extends JTable {

    private SimpleTableModel<T> tableModel;

    public SimpleJTable(SimpleTableModel<T> tableModel) {
        super();
        setModel(tableModel);
    }

    public SimpleJTable() {
        super();
    }
    
    protected SimpleTableColumn.Builder<T> buildColumn(String name, Function<T, Object> getValue) {
        return new SimpleTableColumn.Builder<>(name, getValue);
    }

    public void setModel(@NotNull SimpleTableModel<T> tableModel) {
        super.setModel(tableModel);
        this.tableModel = tableModel;

        tableModel.getColumns().forEach(col -> {
            TableColumn tableColumn = getColumn(col.getColumnName());

            if (col.getCellRenderer() != null) {
                tableColumn.setCellRenderer(col.getCellRenderer());
            }
            if (col.getMaxWidth() != null) {
                setColumnMaxWidth(tableColumn, col.getMaxWidth());
            }
        });
    }

    public SimpleTableModel<T> getTableModel() {
        return tableModel;
    }

    private void setColumnMaxWidth(TableColumn tableColumn, int width) {
        tableColumn.setPreferredWidth(width);
        tableColumn.setMaxWidth(width);
    }

    public static <T> SimpleJTable<T> build(Function<SimpleJTableContext<T>, List<SimpleTableColumn<T>>> block) {
        SimpleJTable<T> table = new SimpleJTable<>();
        SimpleJTableContext<T> ctx = new SimpleJTableContext<>();
        List<SimpleTableColumn<T>> columns = block.apply(ctx);
        SimpleTableModel<T> model = new SimpleTableModel<>(columns);
        table.setModel(model);
        return table;
    }

    public static class SimpleJTableContext<T> {


        public SimpleTableColumn.Builder<T> build(String name, Function<T, Object> getValue) {
            return new SimpleTableColumn.Builder<>(name, getValue);
        }

        public SimpleTableColumn<T> create(String name, Function<T, Object> getValue) {
            return SimpleTableColumn.create(name, Object.class, getValue);
        }
    }
}
