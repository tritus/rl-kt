package stick.display

import stick.geometry.Point
import kotlin.math.cos
import kotlin.math.sin

class StickDrawer : ItemDrawer {
    override fun draw(item: DisplayableItem, display: Display) {
        drawStick(display, item.originCm, item.radiusCm, item.angleRad)
    }

    private fun drawStick(display: Display, originCm: Point<Float>, lengthCm: Float, angleRad: Float) {
        val image = (0 until display.width).map { (0 until display.height).map { 0 }.toMutableList() }
        val maxXCm = 30f
        val maxYCm = 20f
        val cmToCoordinateOnX = display.width.toFloat() / maxXCm
        val cmToCoordinateOnY = display.height.toFloat() / maxYCm
        val startingX = originCm.x * cmToCoordinateOnX
        val startingYFromTop = (maxYCm - originCm.y) * cmToCoordinateOnY
        image[startingX.toInt()][startingYFromTop.toInt()] = 1
        val endingX = (originCm.x + lengthCm * cos(angleRad)) * cmToCoordinateOnX
        val endingYFromTop = (maxYCm - (originCm.y + lengthCm * sin(angleRad))) * cmToCoordinateOnY
        image[endingX.toInt()][endingYFromTop.toInt()] = 1
        display.draw(image)
    }
}

