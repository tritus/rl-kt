package stick.environment

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import stick.geometry.Point
import stick.runInScope
import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertTrue

class MovingStickTest {

    private val toleranceIntervalRad = PI / 10
    private val toleranceIntervalCm = 2f

    @Test
    fun testMoveCartActuallyMovesTheCart() {
        runInScope {
            val stick = newMovingStick(this)
            val xOrigin = stick.originCm.x
            stick.moveByXCm(5f)
            delay(100)
            assertTrue { xOrigin + 5 >= stick.originCm.x - toleranceIntervalCm }
            stick.recycle()
        }
    }

    @Test
    fun testStickEventuallyEndsUpBeingVertical() {
        runInScope {
            val stick = newMovingStick(this)
            stick.moveByXCm(5f)
            delay(3000)
            val verticalAngle = - PI / 2
            assertTrue { stick.angleRad < verticalAngle + toleranceIntervalRad && stick.angleRad > verticalAngle - toleranceIntervalRad }
            stick.recycle()
        }
    }

    @Test
    fun testStickMassHasInertiaWhenMoving() {
        runInScope {
            val stick = newMovingStick(this)
            stick.moveByXCm(5f)
            delay(100)
            val verticalAngle = - PI / 2
            assertTrue { stick.angleRad < verticalAngle - toleranceIntervalRad }
            stick.recycle()
        }
    }

    @Test
    fun testStickAngleEvolvesInTheExpectedDirectionWhenNegativeMovementImposed() {
        runInScope {
            val stick = newMovingStick(this)
            stick.moveByXCm(-5f)
            delay(100)
            val verticalAngle = - PI / 2
            assertTrue { stick.angleRad > verticalAngle + toleranceIntervalRad }
            stick.recycle()
        }
    }

    private fun newMovingStick(scope: CoroutineScope): MovingStick {
        return MovingStick(Point(15f, 10f), 4.8f, -PI.toFloat() / 2f, 0.5f, 1f, 0.02f, 0.001f, scope)
    }
}