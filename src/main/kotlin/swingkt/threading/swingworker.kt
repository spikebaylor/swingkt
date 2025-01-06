package swingkt.threading

import javax.swing.SwingWorker

class KtSwingWorker<T, V>(
    private val onDone: KtSwingWorker<T, V>.() -> Unit,
    private val onProcess: KtSwingWorker<T, V>.(List<V>) -> Unit,
    private val inBackground: () -> T,
) : SwingWorker<T, V>() {

    /**
     * on Worker
     */
    override fun doInBackground(): T {
        return inBackground()
    }

    /**
     * on EDT
     */
    override fun done() {
        onDone()
    }

    /**
     * on EDT
     */
    override fun process(chunks: List<V>?) {
        chunks?.let { onProcess(it) }
    }
}

fun <T, V> swingWorker(onDone: KtSwingWorker<T, V>.() -> Unit = {},
                       onProcess: KtSwingWorker<T, V>.(List<V>) -> Unit = {},
                       inBackground: () -> T): KtSwingWorker<T, V> {
    return KtSwingWorker(onDone, onProcess, inBackground)
}
