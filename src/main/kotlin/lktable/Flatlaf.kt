package lktable

import swingkt.flex.demo.Nord
import java.awt.Color
import javax.swing.UIManager

object FlatlafKt {

    val Table = FlatLafTable
    val Panel = FlatLatPanel

    fun table(block: FlatLafTable.() -> Unit) = Table.apply(block)
    fun panel(block: FlatLatPanel.() -> Unit) = Panel.apply(block)

}

object FlatLatPanel {
    fun background(color: Color) = UIManager.put("Panel.background", color)
}

object FlatLafTable {
    fun background(color: Color) = UIManager.put("Table.background", color)
    fun alternateRowColor(color: Color) = UIManager.put("Table.alternateRowColor", color)
    fun rowHeight(height: Int) = UIManager.put("Table.rowHeight", height)
    fun selectionBackground(color: Color) = UIManager.put("Table.selectionBackground", color)
}




