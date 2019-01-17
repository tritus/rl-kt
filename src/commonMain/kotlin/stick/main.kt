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

const val displayWidthPx = 800
const val displayHeightPx = 534
const val displayFPS = 25

const val stickInitialXCm = 15f
const val stickInitialYCm = 10f
const val stickInitialRadiusCm = 4.8f
const val stickInitialAngleRad = -PI.toFloat() / 2f
const val stickEndMassKg = 0.5f
const val stickCartSpeedMPerS = 1f
const val pivotFrictionCoefInNMS = 0.001f

expect fun runInScope(block: suspend CoroutineScope.() -> Unit)

fun main() {
    val recyclableItems = mutableListOf<RecyclableObject>()
    try {
        runInScope {
            MovingStick(
                    Point(stickInitialXCm, stickInitialYCm),
                    stickInitialRadiusCm,
                    stickInitialAngleRad,
                    stickEndMassKg,
                    stickCartSpeedMPerS,
                    1f / (displayFPS.toFloat() * 2),
                    pivotFrictionCoefInNMS,
                    this
            )
                    .also { recyclableItems.add(it) }
                    .also { StickMoverBrain().control(it) }
                    .let {
                        DisplayManager(
                                this,
                                StandardOutputDisplay(Size(displayWidthPx, displayHeightPx)),
                                StickDrawer(),
                                1000L / displayFPS
                        ).also { manager -> manager.display(it) }
                    }
                    .also { recyclableItems.add(it) }
                    .also { delay(experimentTimeMs) }
        }
    } finally {
        recyclableItems.forEach { item -> item.recycle() }
    }
}