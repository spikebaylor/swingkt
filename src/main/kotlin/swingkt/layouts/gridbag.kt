package swingkt.layouts

import swingkt.component
import java.awt.Component
import java.awt.Container
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.JPanel


class GridBag(c: GridBagConstraints = GBC()) : JPanel(GridBagLayout()) {

    var defaultGBC: GBC

    init {
        this.defaultGBC = c.asGBC()
    }

    fun <T : Component> add(comp: T, row: Int, col: Int = GridBagConstraints.RELATIVE, rootGBC: GridBagConstraints = defaultGBC, gbc: GBC.() -> Unit = {}): T {
        val copy = rootGBC.asGBC().copy()
        copy.gridx = col
        copy.gridy = row
        copy.gbc()
        add(comp, copy)
        return comp
    }

    fun modifyDefaultGBC(block: GBC.() -> Unit) {
        val copy = defaultGBC.copy()
        copy.block()
        defaultGBC = copy
    }
}

fun Container.GridBag(defaultGBC: GridBagConstraints = GBC(), block: GridBag.() -> Unit) = GridBag(defaultGBC).apply(block)

fun Container.gridbag(defaultGBC: GridBagConstraints = GBC(), block: GridBag.() -> Unit = {}) = component(GridBag(defaultGBC), block)
fun Container.gridbag(gbc: GBC.() -> Unit = {}, block: GridBag.() -> Unit = {}) = component(GridBag(GBC().apply{gbc()}), block)


class GBC(c: GridBagConstraints = GridBagConstraints()) : GridBagConstraints() {

    init {
        set(c)
    }

    fun set(c : GridBagConstraints) {
        gridx = c.gridx
        gridy = c.gridy
        gridwidth = c.gridwidth
        gridheight = c.gridheight
        anchor = c.anchor
        insets = c.insets
        fill = c.fill
        weightx = c.weightx
        weighty = c.weighty
        ipadx = c.ipadx
        ipady = c.ipady
    }


    private fun anchor(anchor: Int) { this.anchor = anchor }
    /*
     The orientation relative values are: PAGE_START, PAGE_END, LINE_START, LINE_END, FIRST_LINE_START, FIRST_LINE_END, LAST_LINE_START and LAST_LINE_END.
     The baseline relative values are: BASELINE, BASELINE_LEADING, BASELINE_TRAILING, ABOVE_BASELINE, ABOVE_BASELINE_LEADING, ABOVE_BASELINE_TRAILING, BELOW_BASELINE, BELOW_BASELINE_LEADING, and BELOW_BASELINE_TRAILING.
     */
    fun anchorCenter() = anchor(CENTER)
    fun anchorNorth() = anchor(NORTH)
    fun anchorNorthEast() = anchor(NORTHEAST)
    fun anchorEast() = anchor(EAST)
    fun anchorSouthEast() = anchor(SOUTHEAST)
    fun anchorSouth() = anchor(SOUTH)
    fun anchorSouthWest() = anchor(SOUTHWEST)
    fun anchorWest() = anchor(WEST)
    fun anchorNorthWest() = anchor(NORTHWEST)

    private fun fill(fill: Int) { this.fill = fill }
    fun fillNone() = fill(NONE)
    fun fillBoth() = fill(BOTH)
    fun fillHorizontal() = fill(HORIZONTAL)
    fun fillVertical() = fill(VERTICAL)


    fun width(gridwidth: Int) { this.gridwidth = gridwidth }
    fun remainderWidth() = width(REMAINDER)
    fun relativeWidth() = width(RELATIVE)

    fun height(gridheight: Int) { this.gridheight = gridheight }
    fun remainderHeight() = height(REMAINDER)
    fun relativeHeight() = height(RELATIVE)

    fun insets(top: Int = insets.top, left: Int = insets.left, bottom: Int = insets.bottom, right: Int = insets.right) {
        val insets = Insets(top, left, bottom, right)
        this.insets = insets
    }

    fun copy(): GBC = this.clone() as GBC

}

fun GridBagConstraints.asGBC(): GBC = if (this is GBC) this else GBC(this)
