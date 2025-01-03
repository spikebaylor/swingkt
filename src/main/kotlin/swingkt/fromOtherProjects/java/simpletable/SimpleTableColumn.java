package swingkt.fromOtherProjects.java.simpletable;

import javax.swing.table.TableCellRenderer;
import java.util.function.Function;

public class SimpleTableColumn<T> {

    private final String name;
    private final Class<?> colClass;
    private final Function<T, Object> func;

    private TableCellRenderer renderer = null;
    private Integer maxWidth = null;

    public static <T> SimpleTableColumn<T> create(String name, Function<T, Object> getValue) {
        return new SimpleTableColumn<>(name, getValue, Object.class);
    }

    public static <T> SimpleTableColumn<T> create(String name, Class<?> type, Function<T, Object> getValue) {
        return new SimpleTableColumn<>(name, getValue, type);
    }

    public static <T> Builder<T> build(String name, Function<T, Object> getValue) {
        return new Builder<>(name, getValue);
    }

    private SimpleTableColumn(String name, Function<T, Object> getValue, Class<?> type) {
        this.name = name;
        this.colClass = type;
        this.func = getValue;
    }

    public String getColumnName() {
        return name;
    }

    public Class<?> getColumnClass() {
        return colClass;
    }

    public Object getValue(T item) {
        return func.apply(item);
    }

    public TableCellRenderer getCellRenderer() {
        return renderer;
    }

    public Integer getMaxWidth() {
        return maxWidth;
    }

    public void setRenderer(TableCellRenderer renderer) {
        this.renderer = renderer;
    }

    public void setMaxWidth(Integer maxWidth) {
        this.maxWidth = maxWidth;
    }

    public static class Builder<T> {

        private final String name;
        private final Function<T, Object> getValue;
        private Class<?> classType = Object.class;
        private TableCellRenderer renderer = null;
        private Integer maxWidth = null;

        public Builder(String name, Function<T, Object> getValue) {
            this.name = name;
            this.getValue = getValue;
        }

        public Builder<T> classType(Class<?> classType) {
            this.classType = classType;
            return this;
        }

        public Builder<T> renderer(TableCellRenderer renderer) {
            this.renderer = renderer;
            return this;
        }

        public Builder<T> maxWidth(int pxWidth) {
            this.maxWidth = pxWidth;
            return this;
        }

        public SimpleTableColumn<T> build() {
            SimpleTableColumn<T> col = SimpleTableColumn.create(name, classType, getValue);
            col.setRenderer(renderer);
            col.setMaxWidth(maxWidth);
            return col;
        }

    }

}
