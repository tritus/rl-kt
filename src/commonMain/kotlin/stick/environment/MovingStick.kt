package stick.environment

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import stick.brain.MovableStick
import stick.display.DisplayableItem
import stick.geometry.Point
import stick.lifecycle.RecyclableObject
import stick.math.ODESolver
import stick.math.SecondOrderEulerSolver
import kotlin.math.absoluteValue
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

class MovingStick(
    override var originCm: Point<Float>,
    override val radiusCm: Float,
    override var angleRad: Float,
    private val massKg: Float,
    private val movingSpeedMPerS: Float,
    private val timeIntervalInS: Float,
    private val pivotFrictionCoefInNMS: Float,
    scope: CoroutineScope
) : MovableStick, DisplayableItem, RecyclableObject {

    // equality of moments : J*d²(alpha)/dt² + r*d(alpha)/dt + m*g*l*cos(alpha) - l*m*acc*sin(alpha) = 0
    // J -> inertiaMoment
    // r -> resistance in the pivot link
    // alpha -> angleRad
    // l -> radiusCm
    // m -> massKg
    // acc -> computed for each time interval
    // g -> gravitational constant 9.81

    private var angleSpeed = 0f
    private var originAcceleration = 0.0f
    private var isRecycling = false
    private var remainingMovingDistanceM = 0f
    private var movingDirection = 0f
    private val inertiaMoment = massKg * radiusCm.pow(2) * 10f.pow(-4)
    private val gravitationalConstant = 9.81f

    private val solver: ODESolver = SecondOrderEulerSolver()

    init {
        scope.launch {
            val timeIntervalInMs = (timeIntervalInS * 1000f).toLong()
            while (!isRecycling) {
                computeNextValues()
                delay(timeIntervalInMs)
            }
        }
    }

    override val xOrigin: Float get() { return originCm.x }
    override val angle: Float get() { return angleRad }

    private fun computeNextValues() {
        setNewXOrigin()
        computeNextAngle()
        computeNewMovingOrder()
    }

    private fun computeNextAngle() {
        val newAngleData = solver.nextValuesFrom(angleRad, angleSpeed, angleAccelerationFormula(originAcceleration), timeIntervalInS)
        angleRad = newAngleData[0]
        angleSpeed = newAngleData[1]
    }

    private fun angleAccelerationFormula(originAcc: Float): (Float, Float) -> Float {
        val radiusMeter = radiusCm * 10f.pow(-2)
        return { angle: Float, angleSpeed: Float ->
            (-pivotFrictionCoefInNMS * angleSpeed - massKg * gravitationalConstant * radiusMeter * cos(angle) + radiusMeter * massKg * originAcc * sin(angle)) / inertiaMoment
        }
    }

    private fun setNewXOrigin() {
        val newX = originCm.x + movingDirection * movingSpeedMPerS
        originCm = Point(newX, originCm.y)
    }

    private fun computeNewMovingOrder() {
        if (remainingMovingDistanceM > 0) {
            remainingMovingDistanceM -= timeIntervalInS * movingSpeedMPerS
            originAcceleration = 0f
        } else {
            originAcceleration = if (remainingMovingDistanceM == 0f) 0f else - movingDirection * movingSpeedMPerS / timeIntervalInS
            remainingMovingDistanceM = 0f
            movingDirection = 0f
        }
    }

    override fun moveByXCm(dx: Float) {
        remainingMovingDistanceM = dx.absoluteValue * 10f.pow(-2f)
        val newMovingDirection = if (dx > 0) 1f else if (dx < 0) -1f else 0f
        if (dx != 0f) {
            originAcceleration = if (movingDirection * dx > 0) {
                0f
            } else if (movingDirection * dx < 0) {
                newMovingDirection * 2f * movingSpeedMPerS / timeIntervalInS
            } else {
                newMovingDirection * movingSpeedMPerS / timeIntervalInS
            }
        }
        movingDirection = newMovingDirection
    }

    override fun recycle() {
        isRecycling = true
    }
}