package stick.brain

interface MovableStick {
    val xOrigin: Float
    val angle: Float

    fun moveByXCm(dx: Float)
}