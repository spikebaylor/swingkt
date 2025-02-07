package demos.lktable

import swingkt.component
import swingkt.components.button
import swingkt.components.label
import swingkt.layouts.flex.FlexAlignItem
import swingkt.layouts.flex.FlexBoxPanel
import swingkt.flex.demo.Nord
import swingkt.layouts.flex.flexcol
import swingkt.components.emptyBorder
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

            flexcol(gap = 8, alignItems = FlexAlignItem.CENTER) {
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

