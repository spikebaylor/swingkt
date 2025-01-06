package swingkt.threading

import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicLong
import javax.swing.SwingUtilities


interface Scheduler {

    fun run(runner: Runnable)

}

object Schedulers {

    val IO = from(Executors.newFixedThreadPool(10, NamedThreadFactory { "scheduler-io-$it" }))
    val Compute = from(Executors.newFixedThreadPool(10, NamedThreadFactory { "scheduler-compute-$it" }))

    object EDT : Scheduler {

        override fun run(runner: Runnable) {
            if (SwingUtilities.isEventDispatchThread()) {
                runner.run()
            } else {
                SwingUtilities.invokeLater(runner)
            }
        }
    }

    fun from(ex: Executor): Scheduler = ExecutorScheduler(ex)

    fun runOn(s: Scheduler, runner: Runnable) {
        s.run(runner)
    }

}

class NamedThreadFactory(private val wrapped: ThreadFactory = Executors.defaultThreadFactory(),
                         private val namePattern: (Long) -> String) : ThreadFactory {

    private var count = AtomicLong(0)

    override fun newThread(r: Runnable): Thread {
        return wrapped.newThread(r).apply {
            name = namePattern(count.getAndIncrement())
        }
    }

}

private class ExecutorScheduler(private val executor: Executor) : Scheduler {
    override fun run(runner: Runnable) {
        executor.execute(runner)
    }
}



