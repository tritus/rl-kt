package stick.display

import kotlinx.coroutines.delay
import stick.environment.MovingStick
import stick.geometry.Point
import stick.runInScope
import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MovingStickTest {

    private val toleranceIntervalRad = PI / 10

    @Test
    fun testMoveCartActuallyMovesTheCart() {
        runInScope {
            val stick = newMovingStick()
            val xOrigin = stick.originCm.x
            stick.moveByXCm(5f)
            delay(2000)
            assertEquals(xOrigin + 5, stick.originCm.x)
        }
    }

    @Test
    fun testStickEventuallyEndsUpBeingVertical() {
        runInScope {
            val stick = newMovingStick()
            stick.moveByXCm(5f)
            delay(2000)
            val verticalAngle = - PI / 2
            assertTrue { stick.angleRad < verticalAngle + toleranceIntervalRad || stick.angleRad > verticalAngle - toleranceIntervalRad }
        }
    }

    @Test
    fun testStickMassHasInertiaWhenMoving() {
        runInScope {
            val stick = newMovingStick()
            stick.moveByXCm(5f)
            delay(100)
            val verticalAngle = - PI / 2
            assertTrue { stick.angleRad < verticalAngle + toleranceIntervalRad}
        }
    }

    private fun newMovingStick(): MovingStick {
        return MovingStick(Point(15f, 10f),4.8f,-PI.toFloat()/2f, 0.5f)
    }
}