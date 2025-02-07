package swingkt.layouts.flex

import java.awt.*
import javax.swing.SwingUtilities
import kotlin.math.min


private data class FlexComponentRestraints(
    val mainAxisPos: Int = 0,
    val crossAxisPos: Int = 0,
    val mainAxisSize: Int = 0,
    val crossAxisSize: Int = 0
)

data class RelativeDimension(val mainAxis: Int, val crossAxis: Int)
private typealias ChangeListener = ()->Unit

class FlexBoxLayout(
    flexDirection: FlexDirection = FlexDirection.ROW,
    justifyContent: FlexJustifyContent = FlexJustifyContent.START,
    alignItems: FlexAlignItem = FlexAlignItem.STRETCH,
    gap: Int = 0,
) : LayoutManager2 {

    var flexDirection = flexDirection
        set(value) { field = value; propertiesUpdated() }

    var justifyContent = justifyContent
        set(value) { field = value; propertiesUpdated()  }

    var alignItem = alignItems
        set(value) { field = value; propertiesUpdated()  }

    var gap = gap
        set(value) { field = value; propertiesUpdated()  }

    private var invalided = true
    private var mainAxisTotal = 0
    private var crossAxisTotal = 0
    private var containerSize: RelativeDimension = RelativeDimension(0, 0)
    private var containerOrigin: RelativeDimension = RelativeDimension(0, 0)
    private val map = mutableMapOf<Component, FlexComponentRestraints>()
    private val flexItemMap = mutableMapOf<Component, FlexItemConstraints>()
    private var container: Container = Container()
    private var emptyMainSpace = 0
    private var emptyCrossSpace = 0
    private val changeListeners = mutableListOf<ChangeListener>()

    private fun log(msg: String) {
        println("[${Thread.currentThread().name}] (${container.javaClass.hashCode()}): $msg")
    }

    private fun propertiesUpdated() {
        SwingUtilities.invokeLater {
            invalidateLayout(container)
            container.revalidate()
            notifyChangeListeners()
        }
    }

    fun onChange(l: ChangeListener): ChangeListener {
        changeListeners.add(l)
        return l
    }

    fun removeChangeListener(l: ChangeListener) {
        changeListeners.remove(l)
    }

    private fun notifyChangeListeners() {
        changeListeners.forEach { it() }
    }

    fun getFlexItemConstraints(comp: Component): FlexItemConstraints {
        return flexItemMap[comp] ?: FlexItemConstraints()
    }

    fun setFlexItemConstraints(comp: Component, constraints: FlexItemConstraints)  {
        flexItemMap[comp] = constraints
        invalidateLayout(container)
        comp.revalidate()
    }

    fun modifyFlexItemConstraints(comp: Component, builder: FlexItemConstraints.() -> FlexItemConstraints) {
        val cur = getFlexItemConstraints(comp)
        setFlexItemConstraints(comp, cur.builder())
    }

    private fun calculate(parent: Container) {
        if (invalided) {
            //log("\n\nCALCULATE!")
            invalided = false
            mainAxisTotal = 0
            crossAxisTotal = 0
            this.container = parent
            map.clear()

            val dim = parent.size
            dim.width = dim.width - parent.insets.left - parent.insets.right
            dim.height = dim.height - parent.insets.top - parent.insets.bottom

            //log("parent.size: ${parent.size}")
            //log("containerSize (- insets): $dim")

            containerSize = dim.toRelative()
            containerOrigin = Dimension(parent.insets.left, parent.insets.top).toRelative()
            //log("origin: ${containerOrigin.toNormal()}")

            val components = parent.components.toList()
            components.forEach{
                flexItemMap.putIfAbsent(it, FlexItemConstraints())
                map[it] = FlexComponentRestraints()
            }

            if (components.isNotEmpty()) {

                mainAxisTotal =
                    components.sumOf { it.preferredSize.toRelative().mainAxis } + ((components.size - 1) * gap)
                crossAxisTotal = components.maxOf { it.preferredSize.toRelative().crossAxis }

                emptyMainSpace = containerSize.mainAxis - mainAxisTotal
                emptyCrossSpace = containerSize.crossAxis - crossAxisTotal

                determineMainSizes(components)
                determineCrossSizes(components)
                determineCrossPos(components)
                determineMainPos(components)
            }
        }
    }

    private fun determineMainPos(components: List<Component>) {
        val spaceAroundGap = (emptyMainSpace / components.size)/2

        val gapBetweenItems = when (justifyContent) {
            FlexJustifyContent.SPACE_BETWEEN -> (if (components.size > 1) emptyMainSpace / (components.size-1) else 0) + gap
            FlexJustifyContent.SPACE_EVENLY -> (emptyMainSpace / (components.size+1)) + gap
            FlexJustifyContent.SPACE_AROUND -> (spaceAroundGap * 2) + gap
            else -> gap
        }

        val outsideGap = when (justifyContent) {
            FlexJustifyContent.SPACE_EVENLY -> (emptyMainSpace / (components.size+1))
            FlexJustifyContent.SPACE_AROUND -> spaceAroundGap
            else -> 0
        }

        var pos = when (justifyContent) {
            FlexJustifyContent.START, FlexJustifyContent.SPACE_BETWEEN -> containerOrigin.mainAxis
            FlexJustifyContent.END ->  containerOrigin.mainAxis + emptyMainSpace
            FlexJustifyContent.CENTER -> containerOrigin.mainAxis + (emptyMainSpace / 2)
            FlexJustifyContent.SPACE_EVENLY, FlexJustifyContent.SPACE_AROUND -> containerOrigin.mainAxis + outsideGap
        }

        components.forEach { component ->
            map.computeIfPresent(component) { _, v ->
                val result = v.copy(mainAxisPos = pos)
                pos += map[component]!!.mainAxisSize + gapBetweenItems
                result
            }
        }
    }

    private fun determineCrossPos(components: List<Component>) {
        components.forEach { c -> map.computeIfPresent(c) { _, v ->
            val computedAlignItem = getFlexItemConstraints(c).alignSelf ?: alignItem
            val crossPos = when (computedAlignItem) {
                FlexAlignItem.START, FlexAlignItem.STRETCH, FlexAlignItem.MAX -> containerOrigin.crossAxis
                FlexAlignItem.END -> containerOrigin.crossAxis + containerSize.crossAxis - v.crossAxisSize
                FlexAlignItem.CENTER -> containerOrigin.crossAxis + (containerSize.crossAxis / 2) - (v.crossAxisSize / 2)
            }
            v.copy(crossAxisPos = crossPos)
        }}
    }

    private fun determineCrossSizes(components: List<Component>) {
        components.forEach { c -> map.computeIfPresent(c) { _, v ->
            val computedAlignItem = getFlexItemConstraints(c).alignSelf ?: alignItem
            val max = components.maxOf { it.preferredSize.toRelative().crossAxis }
            val size = when(computedAlignItem) {
                FlexAlignItem.STRETCH -> containerSize.crossAxis
                FlexAlignItem.MAX -> max
                else -> c.preferredSize.toRelative().crossAxis
            }
            v.copy(crossAxisSize = size)
        } }
    }

    private fun determineMainSizes(components: List<Component>) {
        val growItems = flexItemMap.filter { it.value.flexGrow > 0 }.count()
        val growTotal = flexItemMap.filter { it.value.flexGrow > 0 }.map { it.value.flexGrow }.sum()
        val growRatio: Double = if (growTotal > 0) 1 / growTotal.toDouble() else 0.0
        val spacePerRatio = if (growRatio > 0) emptyMainSpace * growRatio else 0.0

        if (growItems > 0) {
            // set empty space to 0 since we're going to be using it all.  This will help follow on calculations
            // such as SPACE_BETWEEN
            emptyMainSpace = 0
        }

        // log("growItems = $growItems :: growRatio = $growRatio :: empty = $emptyMainSpace :: spacePerRatio = $spacePerRatio")

        components.forEach { c -> map.computeIfPresent(c) { _, v ->
            val grow = (spacePerRatio * getFlexItemConstraints(c).flexGrow).toInt()
            v.copy(mainAxisSize = c.preferredSize.toRelative().mainAxis + grow)
        } }
    }

    override fun layoutContainer(parent: Container?) {
        //log("\nLayoutContainer")
        parent?.let { target ->
            synchronized (target.getTreeLock()) {
                calculate(target)
                val components = target.components.toList()
                components.forEachIndexed { index, component ->
                    val r = map[component]
                    if (r != null) {
                        val pos = RelativeDimension(r.mainAxisPos, r.crossAxisPos).toNormalized()
                        val size = RelativeDimension(r.mainAxisSize, r.crossAxisSize).toNormalized()
                        component.setBounds(pos.width, pos.height, size.width, size.height)
                        //log("COMP: ${component.getBounds()}")
                    }
                }
            }
        }
    }

    private fun Dimension.toRelative(): RelativeDimension {
        if (flexDirection == FlexDirection.ROW) {
            return RelativeDimension(this.width, this.height)
        } else {
            return RelativeDimension(this.height, this.width)
        }
    }

    private fun RelativeDimension.toNormalized(): Dimension {
        return if (flexDirection == FlexDirection.ROW) Dimension(mainAxis, crossAxis) else Dimension(crossAxis, mainAxis)
    }

    fun toRelativeDimension(x: Int, y: Int): RelativeDimension {
        return Dimension(x, y).toRelative()
    }

    fun toNormalizedDimension(x: Int, y: Int): Dimension {
        return RelativeDimension(x, y).toNormalized()
    }

    override fun preferredLayoutSize(parent: Container?): Dimension {
        var size: Dimension
        val normalized = RelativeDimension(mainAxisTotal, crossAxisTotal).toNormalized()
        synchronized(this) {
            calculate(parent!!)
            size = Dimension(normalized.width, normalized.height)
        }

        val insets: Insets = parent!!.insets
        size.width = min((size.width + insets.left + insets.right), Int.MAX_VALUE)
        size.height = min((size.height + insets.top + insets.bottom), Int.MAX_VALUE)
        return size
    }

    override fun minimumLayoutSize(parent: Container?): Dimension {
        return preferredLayoutSize(parent)
    }

    override fun maximumLayoutSize(target: Container?): Dimension {
        return preferredLayoutSize(target)
    }

    override fun getLayoutAlignmentX(target: Container?): Float {
        return 0f
    }

    override fun getLayoutAlignmentY(target: Container?): Float {
        return 0f
    }

    override fun invalidateLayout(target: Container?) {
        //log("invalidate called")
        invalided = true
    }

    override fun addLayoutComponent(comp: Component?, constraints: Any?) {
        comp?.let {
            if (constraints is FlexItemConstraints) {
                flexItemMap[comp] = constraints
            }
            invalidateLayout(comp.parent)
        }
    }

    override fun addLayoutComponent(name: String?, comp: Component?) {
        comp?.let {
            invalidateLayout(comp.parent)
        }
    }

    override fun removeLayoutComponent(comp: Component?) {
        comp?.let {
            invalidateLayout(comp.parent)
            flexItemMap.remove(comp)
        }
    }
}
