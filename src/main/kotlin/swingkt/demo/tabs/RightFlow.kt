package swingkt.demo.tabs

import swingkt.component
import swingkt.demo.TestPanel
import swingkt.layouts.Flow
import swingkt.style.applyStyle
import swingkt.style.linedBorder
import java.awt.Color
import java.awt.FlowLayout


val RightFlow = TabPanel {
    Flow(alignment = FlowLayout.RIGHT) {
        applyStyle {
            linedBorder(Color.GREEN)
        }

        component(TestPanel()) {
            applyStyle(tps) { bgColor = Color.RED }
        }

        component(TestPanel()) {
            applyStyle(tps) { bgColor = Color.GREEN }
        }

        component(TestPanel()) {
            applyStyle(tps) { bgColor = Color.BLUE }
        }

        component(TestPanel()) {
            applyStyle(tps) { bgColor = Color.YELLOW }
        }

        component(TestPanel()) {
            applyStyle(tps) { bgColor = Color.CYAN }
        }

    }
}
