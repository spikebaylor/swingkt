package swingkt.fromOtherProjects.java.oldtable

import java.awt.Color
import java.awt.Component
import javax.swing.JProgressBar
import javax.swing.JTable
import javax.swing.table.TableCellRenderer

class ProgressCellRender : JProgressBar, TableCellRenderer {
    constructor() : super()

    constructor(foreground: Color?, background: Color?) : super() {
        setForeground(foreground)
        setBackground(background)
    }

    constructor(foreground: Color?) : super() {
        setForeground(foreground)
    }

    override fun getTableCellRendererComponent(
        table: JTable, value: Any, isSelected: Boolean, hasFocus: Boolean,
        row: Int, column: Int
    ): Component {
        // OK VALUE passed to the column model needs to be a ProgressBarValue(float, String)
        // then we'll set the float as progress, and string as setString
        // pass foreground and background into renderer constructor.
        var progress = 0
        if (value is Float) {
            progress = Math.round(value * 100f)
        } else if (value is Int) {
            progress = value
        } else if (value is ProgressCellModel) {
            val model = value
            progress = Math.round((model.value) * 100f)
            isStringPainted = true
            string = model.text
        }
        setValue(progress)
        return this
    }

    class ProgressCellModel(val value: Float, val text: String)
}
