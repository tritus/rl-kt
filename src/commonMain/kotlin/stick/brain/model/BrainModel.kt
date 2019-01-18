package stick.brain.model

interface BrainModel {
    fun bestActionFrom(xOrigin: Float, angle: Float): List<Float>
}
