package stick.environment

import stick.brain.MovableStick
import stick.display.DisplayableItem
import stick.geometry.Point
import kotlin.math.PI

class MovingStick: MovableStick, DisplayableItem {
    override var originCm = Point(15f, 10f)
    override val radiusCm: Float = 4.8f
    override var angleRad: Float = -PI.toFloat()/2f
}