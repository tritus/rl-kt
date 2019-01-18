package stick.brain

import stick.brain.model.BrainModel
import stick.math.Matrix

class DRELUDModel : BrainModel {

    val layer1 = Matrix(
            listOf(0f, 1f),
            listOf(-13f, 21f),
            listOf(4f, 22f),
            listOf(-3f, 6f),
            listOf(5f, 11f),
            listOf(2f, 39f),
            listOf(19f, -26f),
            listOf(7f, -44f),
            listOf(19f, -5f),
            listOf(5f, -12f),
            listOf(0f, 31f),
            listOf(-1f, -5f),
            listOf(37f, -30f),
            listOf(-11f, -22f),
            listOf(1f, -2f),
            listOf(26f, -9f)
    )

    private val layer2 = Matrix(
            listOf(-1f, 2f, 2f, -5f, -18f, -35f, 3f, -2f, 12f, -12f, -18f, -11f, 10f, 0f, -14f, -35f),
            listOf(30f, -2f, -6f, -8f, -31f, 12f, 5f, 3f, 2f, 0f, 0f, -14f, -13f, 0f, -42f, -35f),
            listOf(14f, 10f, -8f, -7f, 0f, -2f, -14f, -6f, -8f, 6f, -18f, 6f, 30f, -9f, -1f, 2f)
    )

    override fun bestActionFrom(xOrigin: Float, angle: Float): List<Float> {
        return listOf(xOrigin, angle)
                .let { input -> layer1.times(input) }
                .let { input -> input.map { activateWithReLU(it) } }
                .let { input -> layer2.times(input) }
    }

    private fun activateWithReLU(input: Float): Float {
        return if (input < 0) 0f else input
    }
}