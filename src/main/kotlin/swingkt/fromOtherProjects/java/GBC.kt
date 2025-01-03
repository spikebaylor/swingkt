package swingkt.fromOtherProjects.java

import java.awt.GridBagConstraints
import java.awt.Insets
import java.util.function.Consumer

class GBC : GridBagConstraints {
    private var defaultState: GridBagConstraints? = null

    constructor() {
        gridx = RELATIVE
        gridy = 0
        gridwidth = 1
        gridheight = 1

        weightx = 0.0
        weighty = 0.0
        anchor = CENTER
        fill = NONE

        insets = Insets(0, 0, 0, 0)
        ipadx = 0
        ipady = 0

        captureDefaultState()
    }

    /**
     * Creates a `GridBagConstraints` object with
     * all of its fields set to the passed-in arguments.
     *
     * Note: Because the use of this constructor hinders readability
     * of source code, this constructor should only be used by
     * automatic source code generation tools.
     *
     * @param gridx     The initial gridx value.
     * @param gridy     The initial gridy value.
     * @param gridwidth The initial gridwidth value.
     * @param gridheight        The initial gridheight value.
     * @param weightx   The initial weightx value.
     * @param weighty   The initial weighty value.
     * @param anchor    The initial anchor value.
     * @param fill      The initial fill value.
     * @param insets    The initial insets value.
     * @param ipadx     The initial ipadx value.
     * @param ipady     The initial ipady value.
     *
     * @see java.awt.GridBagConstraints.gridx
     *
     * @see java.awt.GridBagConstraints.gridy
     *
     * @see java.awt.GridBagConstraints.gridwidth
     *
     * @see java.awt.GridBagConstraints.gridheight
     *
     * @see java.awt.GridBagConstraints.weightx
     *
     * @see java.awt.GridBagConstraints.weighty
     *
     * @see java.awt.GridBagConstraints.anchor
     *
     * @see java.awt.GridBagConstraints.fill
     *
     * @see java.awt.GridBagConstraints.insets
     *
     * @see java.awt.GridBagConstraints.ipadx
     *
     * @see java.awt.GridBagConstraints.ipady
     *
     *
     * @since 1.2
     */
    constructor(
        gridx: Int, gridy: Int,
        gridwidth: Int, gridheight: Int,
        weightx: Double, weighty: Double,
        anchor: Int, fill: Int,
        insets: Insets?, ipadx: Int, ipady: Int
    ) {
        this.gridx = gridx
        this.gridy = gridy
        this.gridwidth = gridwidth
        this.gridheight = gridheight
        this.fill = fill
        this.ipadx = ipadx
        this.ipady = ipady
        this.insets = insets
        this.anchor = anchor
        this.weightx = weightx
        this.weighty = weighty
        captureDefaultState()
    }

    constructor(c: GridBagConstraints) {
        this.set(c)
        captureDefaultState()
    }

    fun set(c: GridBagConstraints): GBC {
        this.gridx = c.gridx
        this.gridy = c.gridy
        this.gridwidth = c.gridwidth
        this.gridheight = c.gridheight
        this.fill = c.fill
        this.ipadx = c.ipadx
        this.ipady = c.ipady
        this.insets = c.insets
        this.anchor = c.anchor
        this.weightx = c.weightx
        this.weighty = c.weighty
        return this
    }

    fun captureDefaultState() {
        this.defaultState = clone() as GridBagConstraints
    }

    fun reset(): GBC {
        this.set(defaultState!!)
        return this
    }

    fun coord(x: Int, y: Int): GBC {
        x(x)
        y(y)
        return this
    }

    fun row(row: Int): GBC {
        reset()
        relativeX()
        return y(row)
    }

    fun nextRow(): GBC {
        return row(this.gridy + 1)
    }

    fun x(gridx: Int): GBC {
        this.gridx = gridx
        return this
    }

    fun relativeX(): GBC {
        return this.x(RELATIVE)
    }

    fun y(gridy: Int): GBC {
        this.gridy = gridy
        return this
    }

    fun relativeY(): GBC {
        return this.y(RELATIVE)
    }

    fun width(gridwidth: Int): GBC {
        this.gridwidth = gridwidth
        return this
    }

    fun remainderWidth(): GBC {
        return width(REMAINDER)
    }

    fun relativeWidth(): GBC {
        return width(RELATIVE)
    }


    fun height(gridheight: Int): GBC {
        this.gridheight = gridheight
        return this
    }

    fun remainderHeight(): GBC {
        return width(REMAINDER)
    }

    fun relativeHeight(): GBC {
        return height(RELATIVE)
    }

    fun weightX(weightx: Double): GBC {
        this.weightx = weightx
        return this
    }

    fun weightY(weighty: Double): GBC {
        this.weighty = weighty
        return this
    }

    fun anchor(anchor: Int): GBC {
        this.anchor = anchor
        return this
    }

    fun center(): GBC {
        return anchor(CENTER)
    }

    fun north(): GBC {
        return anchor(NORTH)
    }

    fun northEast(): GBC {
        return anchor(NORTHEAST)
    }

    fun east(): GBC {
        return anchor(EAST)
    }

    fun southEast(): GBC {
        return anchor(SOUTHEAST)
    }

    fun south(): GBC {
        return anchor(SOUTH)
    }

    fun southWest(): GBC {
        return anchor(SOUTHWEST)
    }

    fun west(): GBC {
        return anchor(WEST)
    }

    fun northWest(): GBC {
        return anchor(NORTHWEST)
    }

    fun fill(fill: Int): GBC {
        this.fill = fill
        return this
    }

    fun fillHorizontal(): GBC {
        return this.fill(HORIZONTAL)
    }

    fun fillVertical(): GBC {
        return this.fill(VERTICAL)
    }

    fun fillBoth(): GBC {
        return this.fill(BOTH)
    }

    fun fillNone(): GBC {
        return this.fill(NONE)
    }

    fun insets(insets: Insets?): GBC {
        this.insets = insets
        return this
    }

    fun insets(top: Int, left: Int, bottom: Int, right: Int): GBC {
        return this.insets(Insets(top, left, bottom, right))
    }

    fun insets(all: Int): GBC {
        return this.insets(Insets(all, all, all, all))
    }

    fun padX(ipadx: Int): GBC {
        this.ipadx = ipadx
        return this
    }

    fun padY(ipady: Int): GBC {
        this.ipady = ipady
        return this
    }

    companion object {
        fun build(builder: Consumer<GBC?>): GBC {
            val c = GBC()
            builder.accept(c)
            c.captureDefaultState()
            return c
        }

        fun build(c: GridBagConstraints, builder: Consumer<GBC?>): GBC {
            val gbc = GBC(c)
            builder.accept(gbc)
            gbc.captureDefaultState()
            return gbc
        }
    }
}

