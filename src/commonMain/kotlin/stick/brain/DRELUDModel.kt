package stick.brain

import stick.brain.model.BrainModel

class DRELUDModel : BrainModel {

    val kernelLayer1 = listOf(
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

    private val kernelLayer2 = listOf(-12f, -25f, 9f, -8f, -4f, 0f, 10f, -3f, 5f, 27f, -13f, -7f, 7f, -7f, -22f, 2f)

    override fun bestActionFrom(xOrigin: Float, angle: Float): Float {
        return listOf(xOrigin, angle)
                .let { input -> kernelLayer1.map { input[0] * it[0] + input[1] * it[1] } }
                .let { input -> input.map { activateWithReLU(it) } }
                .let { input -> kernelLayer2.zip(input).fold(0f) { output, pair -> output + pair.first * pair.second } }
    }

    private fun activateWithReLU(input: Float): Float {
        return if (input < 0) 0f else input
    }
}