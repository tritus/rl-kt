package stick

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

actual fun waitFor(timeInMs: Long) {
    runBlocking { delay(timeInMs) }
}