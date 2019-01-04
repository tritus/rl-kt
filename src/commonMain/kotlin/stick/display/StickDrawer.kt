package stick.display

import stick.geometry.Point

class StickDrawer : ItemDrawer {
    override fun draw(item: DisplayableItem, display: Display) {
        drawStick(display, item.originCm, item.radiusCm, item.angleRad)
    }

    private fun drawStick(display: Display, originCm: Point<Float>, lengthCm: Float, angleRad: Float) {
        var image = (1 until display.height).map { (1 until display.width).map { 0 } }
        val widthCm = 100f
        val heightCm = widthCm * display.height.toFloat() / display.width.toFloat()

        display.draw(image)
    }
}

