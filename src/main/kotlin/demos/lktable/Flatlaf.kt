package demos.lktable

import java.awt.Color
import javax.swing.UIManager

object FlatlafKt {

    val Table = FlatLafTable
    val Panel = FlatLafPanel

    fun table(block: FlatLafTable.() -> Unit) = Table.apply(block)
    fun panel(block: FlatLafPanel.() -> Unit) = Panel.apply(block)

}

object FlatLafPanel {
    fun background(color: Color) = UIManager.put("JPanel.background", color)
}

object FlatLafTable {
    fun background(color: Color) = UIManager.put("Table.background", color)
    fun alternateRowColor(color: Color) = UIManager.put("Table.alternateRowColor", color)
    fun rowHeight(height: Int) = UIManager.put("Table.rowHeight", height)
    fun selectionBackground(color: Color) = UIManager.put("Table.selectionBackground", color)
}




