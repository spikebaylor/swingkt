package swingkt.demo.tabs

import swingkt.demo.TestPanel
import swingkt.layouts.BorderPanel
import swingkt.style.applyStyle
import java.awt.Color


val BorderTab = TabPanel {
    BorderPanel {

        north(TestPanel().apply {
            applyStyle(tps) { bgColor = Color.RED }
        })

        east(TestPanel().apply {
            applyStyle(tps) { bgColor = Color.green }
        })

        south(TestPanel().apply {
            applyStyle(tps) { bgColor = Color.blue }
        })

        west(TestPanel().apply {
            applyStyle(tps) { bgColor = Color.magenta }
        })

        center(TestPanel().apply {
            applyStyle(tps) { bgColor = Color.GRAY }
        })

    }
}
