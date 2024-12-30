package swingkt.demo.tabs

import swingkt.component
import swingkt.demo.TestPanel
import swingkt.layouts.VBox
import swingkt.style.applyStyle
import swingkt.style.linedBorder
import java.awt.Color

val VBoxTab = TabPanel {
    VBox {
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
