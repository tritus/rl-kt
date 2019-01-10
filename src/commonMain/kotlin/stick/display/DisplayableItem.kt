package stick.display

import stick.geometry.Point

interface DisplayableItem {
    val originCm: Point<Float>
    val radiusCm: Float
    val angleRad: Float
}