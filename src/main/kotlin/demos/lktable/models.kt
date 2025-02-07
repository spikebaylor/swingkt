package demos.lktable

data class Pod(
    val name: String,
    val namespace: String,
    val containers: List<K8sContainer>,
    val state: String,
    val restarts: Int,
    val images: List<String>,
    val testBoolean: Boolean = false
)

data class Application(
    val name: String,
    val isOption: Boolean,
    val isRunning: Boolean,
)

data class K8sContainer(val name: String, val state: String)
