package stick.display

import kotlinx.coroutines.delay
import stick.environment.MovingStick
import stick.geometry.Point
import stick.geometry.Size
import stick.runInScope
import kotlin.math.PI

class StandardOutputDisplayTest {
    fun testEndToEndOutput() {
        runInScope {
            val stick = MovingStick(Point(15f, 10f), 4.8f, -PI.toFloat() / 2f, 0.5f, 1f, 0.02f, 0.001f, this)
            val displayManager = DisplayManager(this, StandardOutputDisplay(Size(30, 20)), StickDrawer(), 40)
            displayManager.display(stick)
            delay(2000)
            stick.moveByXCm(5f)
            delay(2000)
            stick.moveByXCm(-10f)
            delay(1000)
            stick.moveByXCm(5f)
            delay(3000)
            stick.moveByXCm(20f)
            delay(15000)
            stick.moveByXCm(-2f)
        }
    }
}