package swingkt.demo.tabs

import swingkt.component
import swingkt.demo.TestPanel
import swingkt.layouts.*
import swingkt.style.applyStyle
import swingkt.style.linedBorder
import java.awt.Color


val FlexBoxTab = TabPanel {
    Flexrow(justifyContent = FlexJustifyContent.SPACE_BETWEEN, gap = 4, alignItems = FlexAlignItem.END) {
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

        applyStyle {
            linedBorder(Color.GREEN)
        }
    }
}
