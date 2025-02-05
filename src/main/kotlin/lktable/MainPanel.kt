package lktable

import swingkt.component
import swingkt.components.button
import swingkt.components.label
import swingkt.flex.FlexAlignItem
import swingkt.flex.FlexBoxPanel
import swingkt.flex.demo.Nord
import swingkt.flex.flexcol
import swingkt.fromOtherProjects.java.emptyBorder
import swingkt.x
import javax.swing.JTabbedPane


class MainPanel(val state: AppState) : FlexBoxPanel() {

    init {
        isOpaque = true
        flexcol {
            emptyBorder(8)
            preferredSize = 150 x 100
            opaqueBackground(Nord.SnowStorm1)

            flexcol(gap = 8) {
                flexGrow(1)
                button("Create")
                button("Edit")
                button("Delete")
            }

            flexcol(gap = 8, alignItem = FlexAlignItem.CENTER) {
                flexGrow(1)
                label("Some stuff")
                label("Other stuff")
                label("Probably Hostnames")
            }

        }

        component(JTabbedPane()) {
            flexGrow(1)

            addTab("Applications", ApplicationsMainPanel(state))
            addTab("Pods", PodTablePanel(state))
        }

    }

}

