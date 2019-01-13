package stick.math

class SecondOrderEulerSolver : ODESolver {
    private val stepsCount = 10

    override fun nextValuesFrom(x0: Float, derivativeX0: Float, doubleDerivativeFormula: (Float, Float) -> Float, expectedT: Float): List<Float> {
        val dt = expectedT / stepsCount.toFloat()
        return (0 until stepsCount).fold(listOf(x0, derivativeX0)) { currentPoint, _ ->
            listOf(
                    currentPoint[0] + dt * currentPoint[1],
                    currentPoint[1] + dt * doubleDerivativeFormula(currentPoint[0], currentPoint[1])
            )
        }
    }
}
