package stick.environment

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import stick.brain.MovableStick
import stick.display.DisplayableItem
import stick.geometry.Point
import stick.lifecycle.RecyclableObject
import kotlin.math.absoluteValue
import kotlin.math.pow

class MovingStick(
        override var originCm: Point<Float>,
        override val radiusCm: Float,
        override var angleRad: Float,
        private val massKg: Float,
        private val movingSpeedMPerS: Float,
        private val timeIntervalInS: Float,
        scope: CoroutineScope
) : MovableStick, DisplayableItem, RecyclableObject {

    private var isRecycling = false
    private var remainingMovingDistanceM = 0f
    private var movingDirection = 0f

    init {
        scope.launch {
            val timeIntervalInMs = (timeIntervalInS * 1000f).toLong()
            while (!isRecycling) {
                computeNextValues()
                delay(timeIntervalInMs)
            }
        }
    }

    private fun computeNextValues() {
        setNewXOrigin()
        computeNewMovingOrder()
    }

    private fun setNewXOrigin() {
        val newX = originCm.x + movingDirection * movingSpeedMPerS
        originCm = Point(newX, originCm.y)
    }

    private fun computeNewMovingOrder() {
        if (remainingMovingDistanceM > 0) {
            remainingMovingDistanceM -= timeIntervalInS * movingSpeedMPerS
        } else {
            remainingMovingDistanceM = 0f
            movingDirection = 0f
        }
    }

    override fun moveByXCm(dx: Float) {
        remainingMovingDistanceM += dx.absoluteValue * 10f.pow(-2f)
        movingDirection = if (dx > 0) 1f else if (dx < 0) -1f else 0f
    }

    override fun recycle() {
        isRecycling = true
    }
}