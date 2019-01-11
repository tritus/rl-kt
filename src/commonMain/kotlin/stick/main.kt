package stick

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import stick.brain.StickMoverBrain
import stick.display.StandardOutputDisplay
import stick.display.DisplayManager
import stick.display.StickDrawer
import stick.environment.MovingStick
import stick.geometry.Point
import stick.geometry.Size
import stick.lifecycle.RecyclableObject
import kotlin.math.PI

const val experimentTimeMs = 5000L

const val stickInitialXCm = 15f
const val stickInitialYCm = 10f
const val stickInitialRadiusCm = 4.8f
const val stickInitialAngleRad = -PI.toFloat()/2f
const val stickEndMassKg = 0.5f

const val displayWidthPx = 800
const val displayHeightPx = 534

expect fun runInScope(block: suspend CoroutineScope.() -> Unit)

fun main() {
    runInScope {
        val recyclableItems = mutableListOf<RecyclableObject>()
        MovingStick(Point(stickInitialXCm, stickInitialYCm), stickInitialRadiusCm, stickInitialAngleRad, stickEndMassKg, this)
                .also { recyclableItems.add(it) }
                .also { StickMoverBrain().control(it) }
                .let { DisplayManager(this, StandardOutputDisplay(Size(displayWidthPx, displayHeightPx)), StickDrawer()).also { manager -> manager.display(it) } }
                .also { recyclableItems.add(it) }
                .also { delay(experimentTimeMs) }
                .also { recyclableItems.forEach { item -> item.recycle() } }
    }
}