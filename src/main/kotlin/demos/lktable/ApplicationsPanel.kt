package demos.lktable

import swingkt.component
import swingkt.components.label
import swingkt.components.emptyBorder
import swingkt.flex.demo.Nord
import swingkt.layouts.flex.*
import swingkt.listeners.onAction
import swingkt.listeners.onMouseEntered
import swingkt.listeners.onMouseExited
import swingkt.x
import java.awt.Color
import java.awt.Component
import java.awt.Container
import java.awt.Font
import javax.swing.*

class ApplicationsMainPanel(private val state: AppState) : FlexBoxPanel() {

    private lateinit var runningOptionsPanel: FlexBoxPanel
    private lateinit var optionsPanel: FlexBoxPanel
    private lateinit var runningAppsPanel: FlexBoxPanel
    private lateinit var appsPanel: FlexBoxPanel

    init {
        flexDirection = FlexDirection.COLUMN
        emptyBorder(8)

        flexrow(alignItems = FlexAlignItem.MAX) {

            flexcol {
                //flexGrow(1)
                label("Local-Kube Options") {
                    font = font.deriveFont(Font.BOLD, 18f)
                    foreground = Nord.Frost4
                }
                spacer(4)
                component(JSeparator())
                spacer(8)

                runningOptionsPanel = flexcol(gap = 4)

            }

            component(JSeparator(SwingConstants.VERTICAL))

            flexcol {
                flexGrow(1)

                flexrow(gap=8) {
                    emptyBorder(0, 4, 0, 0)
                    label("Developer Applications") {
                        font = font.deriveFont(Font.BOLD, 18f)
                        foreground = Nord.Frost4
                    }
                    hoverIcon(LKIcons.restart(), LKIcons.restart(color = Nord.Frost4)) {
                        onAction { state.reinstallAll() }
                        toolTipText = "Reinstall All Applications"
                    }
                }
                spacer(4)
                component(JSeparator())
                spacer(8)

                runningAppsPanel = flexcol(gap = 4) {
                    flexGrow(1)
                    emptyBorder(0, 4, 0, 0)
                }
            }
        }

        spacer(4)
        component(JSeparator())
        spacer(8)

        flexrow {
            flexGrow(1)
            optionsPanel = flexcol(gap = 4)

            component(JSeparator(SwingConstants.VERTICAL))

            appsPanel = flexcol(gap = 4) {
                flexGrow(1)
                emptyBorder(0, 4, 0, 0)
            }

        }

        setModel(state.getApplications())
        state.onApplicationsChanged { setModel(it) }

    }

    fun setModel(applications: List<Application>) {
        val runningOptions = applications.filter { it.isOption && it.isRunning }.sortedBy { it.name }
        val options = applications.filter { it.isOption && !it.isRunning }.sortedBy { it.name }
        val runningApps = applications.filter { !it.isOption && it.isRunning}.sortedBy { it.name }
        val apps = applications.filter { !it.isOption && !it.isRunning}.sortedBy { it.name }

        runningOptionsPanel.removeAll()
        runningOptions.forEach {runningOptionsPanel.add(ApplicationPanel(it, state)) }
        if (runningOptions.isEmpty()) {
            runningOptionsPanel.component(JPanel()) {
                preferredSize = 300 x 1 // matches the ApplicationPanel width
            }
        }

        optionsPanel.removeAll()
        options.forEach { optionsPanel.add(ApplicationPanel(it, state)) }
        if (options.isEmpty()) {
            optionsPanel.component(JPanel()) {
                preferredSize = 300 x 1 // matches the ApplicationPanel width
            }
        }

        runningAppsPanel.removeAll()
        runningApps.forEach { runningAppsPanel.add(ApplicationPanel(it, state)) }

        appsPanel.removeAll()
        apps.forEach { appsPanel.add(ApplicationPanel(it, state)) }

        relayout(this)
    }

    // For some reason the areas aren't resized correctly when changed.  this fixes it.
    private fun relayout(comp: Component) {
        revalidate()
        if (comp is Container) {
            comp.components.forEach { relayout(it) }
            comp.doLayout()
        }
        repaint()
    }

}


class ApplicationPanel(private val application: Application, private val state: AppState) : FlexBoxPanel(FlexBoxLayout(gap = 4, alignItems = FlexAlignItem.CENTER)) {

    var defaultBackground: Color = background

    init {
        isOpaque = true
        emptyBorder(2)
        preferredSize = 300 x 28

        if (!application.isRunning) {
            hoverIcon(LKIcons.play(), LKIcons.play(color = Nord.AuroraGreen), { if (it) onHover() else onNotHover()}) {
                onAction { state.installApplication(application) }
                toolTipText = "Install ${application.name}"
            }
        } else {
            hoverIcon(LKIcons.restart(), LKIcons.restart(color = Nord.Frost4), { if (it) onHover() else onNotHover()}) {
                onAction { state.reinstall(application) }
                toolTipText = "Reinstall ${application.name}"
            }
            hoverIcon(LKIcons.stop(), LKIcons.stop(color = Nord.AuroraRed), { if (it) onHover() else onNotHover()}) {
                onAction { state.uninstallApplication(application) }
                toolTipText = "Uninstall ${application.name}"
            }
        }
        spacer(4)
        label(application.name) {
            flexGrow(1)
        }

        onMouseEntered { onHover() }
        onMouseExited { onNotHover() }
    }

    private fun onHover() {
        defaultBackground = this.background
        background = Nord.Frost2
    }

    private fun onNotHover() {
        background = defaultBackground
    }

}

fun Container.hoverIcon(icon: Icon, hoverIcon: Icon, hovered: (Boolean) -> Unit = {}, block: HoverIconButton.() -> Unit = {}) = component(
    HoverIconButton(icon, hoverIcon, hovered), block)

class HoverIconButton(icon: Icon, hoverIcon: Icon, hovered: (Boolean) -> Unit = {}) : JButton(icon) {
    init {
        onMouseEntered { this.icon = hoverIcon; hovered(true) }
        onMouseExited { this.icon = icon; hovered(false) }
    }
}


val ApplicationData = listOf(
    Application("grafana", true, true),
    Application("prometheus", true, true),
    Application("postgres-operator", true, false),
    Application("freeipa", true, false),
    Application("elk", true, false),
    Application("git", true, true),
    Application("keycloak", true, true),

    Application("user-documentation-as-code", false, true),
    Application("user-documentation-as-code-250129-120000", false, false),
    Application("ipa-exporter", false, false),
    Application("certificate-status-exporter", false, false),
    Application("pm-grafana", false, false),
)
