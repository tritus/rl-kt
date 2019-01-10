package stick

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import stick.brain.StickMoverBrain
import stick.display.DisplayImpl
import stick.display.DisplayManager
import stick.display.StickDrawer
import stick.environment.MovingStick
import stick.geometry.Size

const val experimentTimeMs = 5000L

expect fun runInScope(block: suspend CoroutineScope.() -> Unit)

fun main() {
    runInScope {
        MovingStick()
                .also { StickMoverBrain().control(it) }
                .let { DisplayManager(this, DisplayImpl(Size(800, 600)), StickDrawer()).also { manager -> manager.display(it) } }
                .also { delay(experimentTimeMs) }
                .also { it.recycle() }
    }
}