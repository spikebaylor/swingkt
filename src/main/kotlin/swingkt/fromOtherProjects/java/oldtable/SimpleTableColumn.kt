package swingkt.fromOtherProjects.java.oldtable

import java.util.function.Function

class SimpleTableColumn<T, R> private constructor(
    val columnName: String,
    val columnClass: Class<R>,
    private val func: Function<T, R>
) {

    companion object {
        fun <T> create(name: String, getValue: Function<T, Any>): SimpleTableColumn<T, Any> = SimpleTableColumn(name, Any::class.java, getValue)
        fun <T, R> create(name: String, type: Class<R>, getValue: Function<T, R>): SimpleTableColumn<T, R> = SimpleTableColumn(name, type, getValue)
    }

    fun getValue(item: T): R {
        return func.apply(item)
    }
}

