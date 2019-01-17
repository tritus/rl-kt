package stick.brain

import stick.brain.model.BrainModel

class DRELUDModel : BrainModel {

    val kernelLayer1 = listOf(
            listOf(1f, 2f),
            listOf(1f, 2f),
            listOf(1f, 2f),
            listOf(1f, 2f),
            listOf(1f, 2f),
            listOf(1f, 2f),
            listOf(1f, 2f),
            listOf(1f, 2f),
            listOf(1f, 2f),
            listOf(1f, 2f),
            listOf(1f, 2f),
            listOf(1f, 2f),
            listOf(1f, 2f),
            listOf(1f, 2f),
            listOf(1f, 2f),
            listOf(1f, 2f)
    )

    private val kernelLayer2 = listOf(1f, 2f, 1f, 2f, 1f, 2f, 1f, 2f, 1f, 2f, 1f, 2f, 1f, 2f, 1f, 2f)

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