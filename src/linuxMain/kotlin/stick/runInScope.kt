package stick

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking

actual fun runInScope(block: suspend CoroutineScope.() -> Unit) {
    runBlocking { block() }
}