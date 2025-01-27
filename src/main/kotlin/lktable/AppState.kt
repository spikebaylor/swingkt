package lktable

typealias AppsChangeListener = (List<Application>) -> Unit
typealias PodsChangeListener = (List<Pod>) -> Unit

class AppState {

    private val applications = mutableListOf(
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

    private val pods = mutableListOf(
        Pod("udac", "udac", listOf(K8sContainer("UDAC", "Running"), K8sContainer("Loader", "Running")), "Running", 0, listOf("harbor.cesiworks.com/user-documentation-as-code/user-documentation-as-code:latest", "harbor.cesiworks.com/common/sidecar:latest")),
        Pod("udac2", "udac", listOf(K8sContainer("UDAC", "Failed"), K8sContainer("Loader", "Running")), "Failed", 5, listOf("harbor.cesiworks.com/user-documentation-as-code/user-documentation-as-code:latest", "harbor.cesiworks.com/common/sidecar:latest")),
        Pod("grafana", "telemetry", listOf(K8sContainer("Grafana", "Running"), K8sContainer("Stuff", "Succeeded")), "Running",0, listOf("grafana:latest"), true),
        Pod("prometheus", "telemetry", listOf(K8sContainer("Grafana", "Running"), K8sContainer("Stuff", "Succeeded"), K8sContainer("OtherStuff", "Succeeded")), "Pending",0, listOf("prometheus:latest"), true),
        Pod("startupPod", "core", listOf(K8sContainer("pod", "Succeeded")), "Succeeded",0, listOf("something:latest"), true)
    )

    fun installApplication(application: Application) {
        println("Install ${application.name}")
        applications.replaceIf({ it.name == application.name }) { it.copy(isRunning = true) }
        updateAppListeners()
    }

    fun uninstallApplication(application: Application) {
        println("Uninstall ${application.name}")
        applications.replaceIf({ it.name == application.name }) { it.copy(isRunning = false) }
        updateAppListeners()
    }

    fun reinstallAll() {
        applications.filter { !it.isOption && it.isRunning }.forEach {
            println("Reinstall ${it.name}")
        }
    }

    fun reinstall(application: Application) {
        println("application ${application.name}")
    }

    private val appListeners: MutableList<AppsChangeListener> = mutableListOf()
    private val podListeners: MutableList<PodsChangeListener> = mutableListOf()


    private fun updateAppListeners() = appListeners.forEach { it(applications) }
    private fun updatePodListeners() = podListeners.forEach { it(pods) }

    fun getApplications() = applications.toList()
    fun getPods() = pods.toList()

    fun onApplicationsChanged(l: AppsChangeListener) {
        appListeners.add(l)
    }

    fun onPodsChanged(l: PodsChangeListener) {
        podListeners.add(l)
    }


}

fun <T> MutableList<T>.replaceIf(predicate: (T) -> Boolean, replacement: (T) -> T) = this.replaceAll { if (predicate(it)) replacement(it) else it }
