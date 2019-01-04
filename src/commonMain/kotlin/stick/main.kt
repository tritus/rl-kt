package stick

import stick.brain.StickMoverBrain
import stick.display.DisplayImpl
import stick.display.DisplayManager
import stick.display.StickDrawer
import stick.environment.MovingStick
import stick.geometry.Size

const val experimentTimeMs = 5000L

expect fun waitFor(timeInMs: Long)

fun main() {
    MovingStick()
            .also { StickMoverBrain().control(it) }
            .let { DisplayManager(DisplayImpl(Size(800, 600)), StickDrawer()).also { manager -> manager.display(it) } }
            .also { waitFor(experimentTimeMs) }
            .also { it.recycle() }
}