package demos.lktable

import swingkt.layouts.flex.FlexAlignItem
import swingkt.layouts.flex.FlexBoxPanel
import swingkt.flex.demo.Nord
import swingkt.components.emptyBorder
import swingkt.x
import java.awt.Color
import java.awt.Component
import java.awt.Point
import java.awt.event.MouseEvent
import javax.swing.BorderFactory
import javax.swing.JPanel
import javax.swing.JTable
import javax.swing.UIManager
import javax.swing.table.TableCellRenderer


class ContainersTableCellRenderer : TableCellRenderer, FlexBoxPanel() {

    private val cache: MutableMap<K8sContainer, ContainerPanel> = mutableMapOf()

    init {
        gap = 4
        alignItems = FlexAlignItem.CENTER
        emptyBorder(4)
        isOpaque = true
    }

    override fun getToolTipText(event: MouseEvent): String? {
        val comp = this.components.filterIsInstance<ContainerPanel>().find { it.containsPoint(event.point) }
        return comp?.toolTipText
    }


    override fun getTableCellRendererComponent(
        table: JTable, value: Any?, isSelected: Boolean, hasFocus: Boolean, row: Int, column: Int
    ): Component {
        val containers = value as List<K8sContainer>
        this.removeAll()
        containers.forEach {
            add(cache.getOrPut(it) { ContainerPanel(it) })
        }

        if (isSelected) {
            foreground = table.selectionForeground
            background = (UIManager.get("Table.selectionBackground") ?: table.selectionBackground) as Color
        } else {
            // fix alternate
            val alternateColor = UIManager.get("Table.alternateRowColor")

            background = if (alternateColor != null && alternateColor is Color && row % 2 == 1) {
                alternateColor
            } else {
                table.background
            }

            foreground = table.foreground
        }
        return this
    }
}

class ContainerPanel(container: K8sContainer) : JPanel() {

    private val SIZE = 12

    init {
        preferredSize = SIZE x SIZE
        minimumSize = preferredSize
        maximumSize = preferredSize
        border = BorderFactory.createLineBorder(Nord.PolarNight4, 1, true)
        toolTipText = "<html>Name: ${container.name}<br>State: ${container.state}</html>"
        background = when(container.state) {
            "Running" -> Nord.AuroraGreen
            "Failed" -> Nord.AuroraRed
            "Succeeded" -> Nord.SnowStorm2
            else -> Nord.AuroraPurple
        }
    }

    fun containsPoint(p: Point): Boolean {
        return p.x > location.x && p.x < location.x + SIZE && p.y > location.y && p.y < location.y + SIZE
    }
}
