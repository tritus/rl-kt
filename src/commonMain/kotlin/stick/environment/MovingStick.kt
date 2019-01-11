package stick.environment

import stick.brain.MovableStick
import stick.display.DisplayableItem
import stick.geometry.Point

class MovingStick(override var originCm: Point<Float>, override val radiusCm: Float, override var angleRad: Float, private val massKg: Float) : MovableStick, DisplayableItem {
    override fun moveByXCm(dx: Float) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}